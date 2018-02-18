package service;

import dao.ClientDAO;
import model.AbstractEntity;
import model.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ClientService implements EntityService{
    private ClientDAO clientDAO;
    private TariffService tariffService;



    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        clientDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public Client get(int id) {
        return clientDAO.getEntity(id);
    }

    @Override
    @Transactional
    public Client getByName(String email) {
        return clientDAO.getEntityByName(email);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {
        clientDAO.editEntity(entity);
    }

    @Override
    @Transactional
    public Set<Client> getAll() {
        return clientDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        clientDAO.removeEntity(id);
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
}
