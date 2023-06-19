package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.DriverManager;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        var dao = new UserServiceImpl();
        dao.createUsersTable();
        dao.saveUser("Vasya", "P", (byte) 13);
        dao.saveUser("Alex", "N", (byte) 14);
        dao.saveUser("Petya", "V", (byte) 15);
        dao.saveUser("Misha", "G", (byte) 99);

        System.out.println(dao.getAllUsers());

        dao.cleanUsersTable();
        dao.dropUsersTable();

    }
}

