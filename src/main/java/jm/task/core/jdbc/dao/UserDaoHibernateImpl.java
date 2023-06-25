package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() throws HibernateException {

        var sql = "CREATE TABLE IF NOT EXISTS user" +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100), last_name VARCHAR(100), age TINYINT)";

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e)  {
            throw e;
        }
    }

    @Override
    public void dropUsersTable() throws HibernateException {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            var sql = "DROP TABLE IF EXISTS user";

            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e)  {
            throw e;
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws HibernateException {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


    @Override
    public void removeUserById(long id) throws HibernateException {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            Query query = session.createQuery("delete User where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> entityList = Collections.<User>emptyList();

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            criteria.from(User.class);
            entityList = session.createQuery(criteria).getResultList();
            }
        return entityList;
    }

    @Override
    public void cleanUsersTable() throws HibernateException {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
