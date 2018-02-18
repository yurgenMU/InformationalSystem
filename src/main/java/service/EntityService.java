package service;

import model.AbstractEntity;

import java.util.Set;

public interface EntityService {

    void add(AbstractEntity entity);

    AbstractEntity get(int id);

    AbstractEntity getByName(String name);

    void edit(AbstractEntity entity);

    Set<? extends AbstractEntity> getAll();

    void remove(int id);

}