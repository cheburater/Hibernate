package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = null;

        try {
            session = Util.open().openSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User(id SERIAL PRIMARY KEY, name TEXT, lastName TEXT, age SMALLINT)").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            logger.log(Level.INFO, "Таблица создана");
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
                logger.log(Level.WARNING, "Изменения не внесены");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;

        try {
            session = Util.open().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            logger.log(Level.INFO, "Таблица удалена");
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
                logger.log(Level.WARNING, "Изменения не внесены");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;

        try {
            session = Util.open().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            logger.log(Level.INFO, "User сохранен");
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
                logger.log(Level.WARNING, "Изменения не внесены");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        User user = new User();
        user.setId(id);

        try {
            session = Util.open().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
            logger.log(Level.INFO, "User удален");
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
                logger.log(Level.WARNING, "Изменения не внесены");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> users = null;

        try {
            session = Util.open().openSession();
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
            logger.log(Level.INFO, "User список получен");
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
                logger.log(Level.WARNING, "Изменения не внесены");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;

        try {
            session = Util.open().openSession();
            session.beginTransaction();
            for (Object user: session.createQuery("from User").list()) {
                session.delete(user);
            }
            session.getTransaction().commit();
            logger.log(Level.INFO, "Таблица очищена");
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
                logger.log(Level.WARNING, "Изменения не внесены");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
