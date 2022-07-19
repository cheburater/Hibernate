package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        UserDaoHibernateImpl userdao = new UserDaoHibernateImpl();

        userdao.createUsersTable();
        userdao.saveUser("name-1", "lastName-1", (byte) 100);
        userdao.saveUser("name-2", "lastName-2", (byte) 101);
        userdao.saveUser("name-3", "lastName-3", (byte) 102);
        userdao.saveUser("name-4", "lastName-4", (byte) 103);

        for (User user: userdao.getAllUsers()) {
            System.out.println(user.toString());
        }
        userdao.cleanUsersTable();
        userdao.dropUsersTable();
    }
}
