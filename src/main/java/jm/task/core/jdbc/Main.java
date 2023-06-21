package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        var userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Vasya", "P", (byte) 13);
        userService.saveUser("Alex", "N", (byte) 14);
        userService.saveUser("Petya", "V", (byte) 15);
        userService.saveUser("Misha", "G", (byte) 99);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}

