package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sessionFactory.openSession();
        Transaction tx = null;
        try (session) {
            tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        this.tx(session -> session.createSQLQuery("""
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255),
                lastname VARCHAR(255),
                age SMALLINT
            );
        """).addEntity(User.class).executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        this.tx(session -> session.createSQLQuery("""
            DROP TABLE IF EXISTS users
        """).addEntity(User.class).executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        this.tx(session -> session.save(new User(name, lastName, age)));
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        this.tx(session -> {
            User user = session.find(User.class, id);
            session.delete(user);
            return null;
        });
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = this.tx(session -> session.createQuery("from User").list());
        result.forEach(user -> System.out.println(user.toString()));
        return result;
    }


    @Override
    public void cleanUsersTable() {
        this.tx(session -> {
            List<User> users = session.createQuery("from User").list();
            for (User user : users) {
                session.delete(user);
            }
            return null;
        });
    }
}
