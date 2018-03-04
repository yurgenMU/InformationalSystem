package dao;

import model.Client;
import model.Option;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        OptionDAO optionDAO = new OptionDAO();
        ClientDAO clientDAO = new ClientDAO();
        Set<Client> clients = clientDAO.getByInitials("adminadmin");
        System.out.println(clients);
//        optionDAO.addCompatibleOption(optionDAO.getEntity(2),optionDAO.getEntity(5));

    }
}
