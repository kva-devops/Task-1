package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:postgresql://127.0.0.1:5432/task1";
    private static final String username = "postgres";
    private static final String password = "password";
    private static final String driver = "org.postgresql.Driver";

    private static final String dialect = "org.hibernate.dialect.PostgreSQL10Dialect";

    private static final String update = "update";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.connection.url", url);
        properties.setProperty("hibernate.connection.username", username);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.connection.driver_class", driver);
        properties.setProperty("hibernate.hbm2ddl.auto", update);
        return new Configuration().addProperties(properties).addAnnotatedClass(User.class).buildSessionFactory();
    }
}
