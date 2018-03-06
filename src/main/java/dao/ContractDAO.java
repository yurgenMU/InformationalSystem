package dao;

import model.AbstractEntity;
import model.Contract;
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
public class ContractDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public ContractDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    /**
     * Method adding new Contract entity into the database
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Contract contract = (Contract) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(contract);
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
     * Method allowing to get Contract entity by identifier
     *
     * @param id Identifier value
     * @return Contract instance with existing properties
     */
    @Override
    public Contract getEntity(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Contract contract = session.get(Contract.class, id);
        session.getTransaction().commit();
        session.close();
        return contract;
    }

    /**
     * Method allowing to get Contract Entity by unique phone number
     *
     * @param number String identifier
     * @return Contract instance with existing properties
     */
    @Override
    public Contract getEntityByName(String number) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Contract where number =:number");
        query.setParameter("number", number);
        Contract ans = (Contract) query.uniqueResult();
        session.close();
        return ans;
    }


    /**
     * Method allowing to edit given Contract entity
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Contract contract = (Contract) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(contract);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    /**
     * Method allowing to remove Contract entity using identifier
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
            Contract removedContract = session.get(Contract.class, Id);
            session.delete(removedContract);
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
     * @return all from "contracts" table
     */
    @Override
    public Set<Contract> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Contract> contracts = session.createQuery("FROM Contract ").list();
        transaction.commit();
        session.close();
        return new HashSet<>(contracts);
    }


}
