package service;

import dao.ContractDAO;
import model.AbstractEntity;
import model.Contract;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.Set;

public class ContractService implements EntityService {

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TariffService tariffService;


    public ContractDAO getContractDAO() {
        return contractDAO;
    }

    public void setContractDAO(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }


    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public TariffService getTariffService() {
        return tariffService;
    }

    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @Override
    public void add(AbstractEntity entity) {
        contractDAO.addEntity(entity);
    }

    @Override
    public Contract get(int id) {
        return contractDAO.getEntity(id);
    }

    @Override
    public Contract getByName(String name) {
        return contractDAO.getEntityByName(name);
    }

    @Override
    public void edit(AbstractEntity entity) {
        contractDAO.editEntity(entity);
    }

    @Override
    public Set<Contract> getAll() {
        return contractDAO.getAllEntities();
    }

    @Override
    public void remove(int id) {
        contractDAO.removeEntity(id);
    }

    private String generateNumber() {
        int num1, num2, num3; //3 numbers in area code
        int set2, set3; //sequence 2 and 3 of the phone number

        Random generator = new Random();

        //Area code number; Will not print 8 or 9
        num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin
        num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
        num3 = generator.nextInt(8);

        // Sequence two of phone number
        // the plus 100 is so there will always be a 3 digit number
        // randomize to 643 because 0 starts the first placement so if i randomized up to 642 it would only go up yo 641 plus 100
        // and i used 643 so when it adds 100 it will not succeed 742
        set2 = generator.nextInt(643) + 100;

        //Sequence 3 of numebr
        // add 1000 so there will always be 4 numbers
        //8999 so it wont succed 9999 when the 1000 is added
        set3 = generator.nextInt(8999) + 1000;

//        return("(" + num1 + "" + num2 + "" + num3 + ")" + "-" + set2 + "-" + set3);
        return ("" + num1 + num2 + num3 + set2 + set3);

    }
}
