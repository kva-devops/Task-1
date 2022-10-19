package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 19);
        userService.saveUser("Sergey", "Petrov", (byte) 25);
        userService.saveUser("Marya", "Semenova", (byte) 24);
        userService.saveUser("Ksenia", "Gorbunova", (byte) 21);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
