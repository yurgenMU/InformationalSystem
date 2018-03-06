package dao;

import model.AbstractEntity;
import model.Tariff;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class TariffDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public TariffDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    /**
     * Method adding new Tariff entity into the database
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Tariff tariff = (Tariff) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(tariff);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null)
                transaction.rollback();
            he.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    /**
     * Method allowing to get Tariff entity by identifier
     *
     * @param id Identifier value
     * @return Tariff instance with existing properties
     */
    @Override
    public Tariff getEntity(int id) {
        Session session = sessionFactory.openSession();
        Tariff tariff = session.get(Tariff.class, id);
        session.close();
        return tariff;

    }


    /**
     * Method allowing to get Tariff Entity by unique name
     *
     * @param name String identifier
     * @return Tariff instance with existing properties
     */
    @Override
    public Tariff getEntityByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Tariff where name =:name");
        query.setParameter("name", name);
        Tariff ans = (Tariff) query.uniqueResult();
        session.close();
        return ans;
    }


    /**
     * Method allowing to edit given Tariff entity
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Tariff tariff = (Tariff) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(tariff);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null)
                transaction.rollback();
            he.printStackTrace();
        } finally {
            if (session != null)
                session.close();

        }
    }


    /**
     * Method allowing to remove Tariff entity using identifier
     *
     * @param Id Identifier value
     */
    @Override
    public void removeEntity(int Id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Tariff removedTariff = session.get(Tariff.class, Id);
            session.delete(removedTariff);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null)
                transaction.rollback();
            he.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }


    /**
     * Method that returns all Client entities
     *
     * @return all from "tariffs" table
     */
    @Override
    public Set<Tariff> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Tariff> tariffs = session.createQuery("FROM Tariff ").list();
        transaction.commit();
        session.close();
        return new HashSet<>(tariffs);
    }
}
