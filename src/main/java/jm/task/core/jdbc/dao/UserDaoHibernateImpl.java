package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}


    @Override
    public void createUsersTable() {

        Session session = Util.open().openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS User(id SERIAL PRIMARY KEY, name TEXT, lastName TEXT, age SMALLINT)").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {

        Session session = Util.open().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name, lastName, age);
        Session session = Util.open().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {

        Session session = Util.open().openSession();
        session.beginTransaction();
        try {
            User user = session.load(User.class, id);
            session.delete(user);
        }
        catch (EntityNotFoundException e) {System.out.println("такого пользователя нет");}
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.open().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {

        Session session = Util.open().openSession();
        session.beginTransaction();
        for (Object user: session.createQuery("from User").list()) {session.delete(user);}
        session.getTransaction().commit();
        session.close();
    }
}
