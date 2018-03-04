package dao;

import model.AbstractEntity;
import model.Option;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class OptionDAO implements EntityDAO {
    private SessionFactory sessionFactory;

    public OptionDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }


    /**
     * Method adding new Option entity into the database
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void addEntity(T entity) {
        Option option = (Option) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(option);
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
     * Method allowing to get Option entity by identifier
     *
     * @param id Identifier value
     * @return Option instance with existing properties
     */
    @Override
    public Option getEntity(int id) {
        Session session = sessionFactory.openSession();
        Option option = session.get(Option.class, id);
        session.close();
        return option;
    }


    /**
     * Method allowing to get Option Entity by unique name
     *
     * @param name String identifier
     * @return Option instance with existing properties
     */
    @Override
    public Option getEntityByName(String name) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Option where name =:name");
        query.setParameter("name", name);
        return (Option) query.uniqueResult();
    }


    /**
     * Method allowing to edit given Option entity
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    @Override
    public <T extends AbstractEntity> void editEntity(T entity) {
        Option option = (Option) entity;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(option);
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
     * Method allowing to remove Option entity using identifier
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
            Option removedOption = session.get(Option.class, Id);
            session.delete(removedOption);
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
     * @return all from "options" table
     */
    @Override
    public Set<Option> getAllEntities() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Option> options = session.createQuery("FROM Option ").list();
        transaction.commit();
        session.close();
        return new HashSet<>(options);
    }


    public Set<Option> getCompatibleOptions(Set<Option> options) {
        Session session = null;
        Transaction transaction = null;
        Set<Option> ans = Collections.emptySet();
        if (options.size() == 0) {
            return getAllEntities();
        } else {
            try {
                session = sessionFactory.openSession();
                transaction = session.beginTransaction();
                List<Integer> values = options.stream().map(Option::getId).collect(Collectors.toList());
                Query query = session.createNativeQuery("SELECT " +
                        "b.second_option_id " +
                        "FROM (SELECT `options_compatibility`.`first_option_id` " +
                        "AS `first_option_id`,`options_compatibility`.`second_option_id` " +
                        "AS `second_option_id` FROM `options_compatibility` UNION SELECT `options_compatibility`.`second_option_id` " +
                        "AS `second_option_id`,`options_compatibility`.`first_option_id` " +
                        "AS `first_option_id` FROM `options_compatibility`) AS a " +
                        "INNER JOIN (SELECT `options_compatibility`.`first_option_id` " +
                        "AS `first_option_id`,`options_compatibility`.`second_option_id` " +
                        "AS `second_option_id` FROM `options_compatibility` UNION SELECT `options_compatibility`.`second_option_id` " +
                        "AS `second_option_id`,`options_compatibility`.`first_option_id` " +
                        "AS `first_option_id` FROM `options_compatibility`) AS b ON a.second_option_id = b.second_option_id " +
                        "WHERE a.first_option_id IN :arr AND b.first_option_id IN :arr " +
                        "GROUP BY second_option_id " +
                        "HAVING COUNT(*) = :par");
                query.setParameterList("arr", values);
                query.setParameter("par", values.size() * values.size());
                List<Integer> idList = query.getResultList();
                ans = idList.stream().map(x -> getEntity(x)).collect(Collectors.toSet());
                ans = ans.stream().filter(y -> !values.contains(y.getId())).collect(Collectors.toSet());
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
        return ans;
    }

    public Set<Option> getCompatibleOptions(Option option) {
        Set<Option> options = new HashSet<>();
        options.add(option);
        Set<Option> ans = getCompatibleOptions(options);
        ans = ans.stream().filter(x-> x.getId()!=option.getId()).collect(Collectors.toSet());
        return ans;
    }

    public void addCompatibleOption(Option targetOption, Option addedOption) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createNativeQuery("INSERT INTO options_compatibility VALUES (?,?)");
            query.setParameter(1, targetOption.getId());
            query.setParameter(2, addedOption.getId());
            query.executeUpdate();
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

    public void deleteCompatibleOption(Option targetOption, Option removedOption) {
        executeRemoving(removedOption, targetOption);
        executeRemoving(targetOption, removedOption);
    }

    private void executeRemoving(Option targetOption, Option removedOption) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createNativeQuery("DELETE FROM options_compatibility " +
                    "WHERE (first_option_id = ? AND second_option_id = ?)");
            query.setParameter(1, targetOption.getId());
            query.setParameter(2, removedOption.getId());
            query.executeUpdate();
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


    public Set<Option> getTheRest(Set<Option> compatibleOptions) {
        Session session = null;
        Transaction transaction = null;
        Set<Option> ans = Collections.emptySet();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            List<Integer> values = compatibleOptions.stream().map(Option::getId).collect(Collectors.toList());
            Query query;
            if (values.size() == 0) {
                query = session.createNativeQuery("SELECT id FROM options");
            } else {
                query = session.createNativeQuery("SELECT first_option_id FROM options_compatibility" +
                        " WHERE first_option_id NOT IN :arr UNION (SELECT second_option_id FROM options_compatibility" +
                        " WHERE second_option_id NOT IN :arr)");
                query.setParameterList("arr", values);
            }
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

    @Transactional
    public void setCompatibleOptions(Option targetOption, Set<Option> compatibleOptions) {
//        Session session = null;
//        Transaction transaction = null;
//        try {
//            session = sessionFactory.openSession();
//            transaction = session.beginTransaction();
//            Query query = session.createNativeQuery("INSERT INTO options_compatibility VALUES (?,?)");
//            for (Option option : compatibleOptions) {
//                query.setParameter(1, targetOption.getId());
//                query.setParameter(2, option.getId());
//                query.executeUpdate();
//                transaction.commit();
//            }
//        } catch (HibernateException he) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            he.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
    }

}
