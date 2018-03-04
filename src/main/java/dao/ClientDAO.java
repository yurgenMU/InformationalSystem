package dao;

import model.AbstractEntity;
import model.Client;
import model.Option;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ClientDAO implements EntityDAO {

    private SessionFactory sessionFactory;

    public ClientDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    /**
     * Method adding new Client entity into the database
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Client client = (Client) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }


    /**
     * Method allowing to get Client entity by identifier
     *
     * @param id Identifier value
     * @return Client instance with existing properties
     */
    @Override
    public Client getEntity(int id) {
        Session session = sessionFactory.openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }


    /**
     * Method allowing to get Client Entity by unique email
     *
     * @param name String identifier
     * @return Client instance with existing properties
     */
    @Override
    public Client getEntityByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Client where email =:email");
        query.setParameter("email", name);
        return (Client) query.uniqueResult();
    }


    /**
     * Method allowing to edit given Client entity
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Client client = (Client) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
        } catch (HibernateException he) {
            if (transaction != null) {
                transaction.rollback();
            }
            he.printStackTrace();
        } finally {
            if (session != null)
                session.close();

        }
    }


    /**
     * Method allowing to remove Client entity using identifier
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
            Client removedClient = session.get(Client.class, Id);
            session.delete(removedClient);
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
     * @return all from "clients" table
     */
    @Override
    public Set<Client> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Client> usersData = session.createQuery("FROM Client ").list();
        transaction.commit();
        session.close();
        return new HashSet<>(usersData);
    }

    public Set<Client> getByInitials(String initials) {
        Session session = null;
        Transaction transaction = null;
        Set<Client> ans = Collections.emptySet();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createNativeQuery("SELECT id FROM " +
                    "CLIENTS WHERE " +
                    "replace(CONCAT(first_name, last_name),' ', '')" +
                    " LIKE ?");
            query.setParameter(1, initials);
            List<Integer> idList = query.getResultList();
            ans = idList.stream().map(x -> getEntity(x)).collect(Collectors.toSet());
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

        return ans;
    }
}
