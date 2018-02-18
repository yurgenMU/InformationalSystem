package model;

/**
 * Class representing the abstract Entity model. All model classes
 * are the ancessors of this class. This fact allows to use abstract
 * entity at DAO, service and controller layers.
 */
public abstract class AbstractEntity {
    protected int id;
}
