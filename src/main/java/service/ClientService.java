package service;

import dao.ClientDAO;
import model.AbstractEntity;
import model.Client;
import model.Option;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
public class ClientService implements EntityService {
    private ClientDAO clientDAO;
    private TariffService tariffService;


    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        Client client = (Client) entity;
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date parsed = null;
        try {
            String date = client.getDateValue();
            date = date.replaceAll("\\.", "");
            parsed = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        client.setBirth(sql);
        client.setRole("ROLE_USER");
        clientDAO.addEntity(client);
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

    public Model setEditPageParams(Model model, int id) {
        model.addAttribute("client", get(id));
        return model;
    }

    public Set<Client> getByInitials(String name) {
        String initials = name.trim().replaceAll("\\s+", "");
        Set<Client> ans = clientDAO.getByInitials(initials);
        ans.forEach(x -> {
            x.setDateValue(x.getBirth().toString());
            x.setPassword(null);
        });
        return ans;
    }
}
