package service;


import dao.TariffDAO;
import model.AbstractEntity;
import model.Tariff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Set;


@Service
public class TariffService implements EntityService{
    private TariffDAO tariffDAO;

    public TariffDAO getTariffDAO() {
        return tariffDAO;
    }

    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }

    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        tariffDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public Tariff get(int id) {
        return tariffDAO.getEntity(id);
    }

    @Override
    @Transactional
    public Tariff getByName(String name) {
        return tariffDAO.getEntityByName(name);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        tariffDAO.editEntity(entity);
    }

    @Override
    @Transactional
    public Set<Tariff> getAll() {
        return tariffDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        tariffDAO.removeEntity(id);
    }

    public void createAddPageModel(Model model) {
    }

    public void setupNewTariffParams(Tariff tariff, ArrayList<Integer> selectedOptions) {
    }
}
