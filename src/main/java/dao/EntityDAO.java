package dao;

import model.AbstractEntity;

import java.util.Set;

/**
 * Interface defines basic operations for putting in and extraction
 * entities from the database.
 */
public interface EntityDAO {

    /**
     * Method adding entity into the database
     *
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    <T extends AbstractEntity> void addEntity(T entity);


    /**
     * Method allowing to get entity by identifier
     * @param id  Identifier value
     * @param <T> Type parameter
     * @return
     */
    <T extends AbstractEntity> T getEntity(int id);


    /**
     * Method allowing to get Entity by unique name
     * @param name String identifier
     * @param <T>  Type parameter
     * @return
     */
    <T extends AbstractEntity> T getEntityByName(String name);


    /**
     * Method allowing to edit given entity
     * @param entity Given entity
     * @param <T>    Type parameter
     */
    <T extends AbstractEntity> void editEntity(T entity);


    /**
     * Method allowing to remove entity using identifier
     * @param Id Identifier value
     */
    void removeEntity(int Id);

    /**
     * Method that returns all entities
     * @return
     */
    Set<? extends AbstractEntity> getAllEntities();
}
