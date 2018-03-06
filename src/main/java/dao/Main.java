package dao;

import model.Client;
import model.Contract;
import model.Option;
import model.Tariff;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        OptionDAO optionDAO = new OptionDAO();
        ClientDAO clientDAO = new ClientDAO();
        TariffDAO tariffDAO = new TariffDAO();
        ContractDAO contractDAO = new ContractDAO();
        Option option = optionDAO.getEntity(1);
        Set<Option> options = new HashSet<>();
        options.forEach(x-> x.setTariffs(Collections.emptySet()));
        options.add(option);
        Tariff tariff = tariffDAO.getEntity(1);
        Client client = clientDAO.getEntity(1);
        Contract contract = new Contract();
        contract.setNumber("911");
        Set<Contract> contracts = tariff.getContracts();
        contracts.add(contract);
        tariff.setContracts(contracts);
        contract.setOptions(options);
//        contract.setTariff(tariff);
        contract.setClient(client);
        contractDAO.addEntity(contract);

//        optionDAO.addCompatibleOption(optionDAO.getEntity(2),optionDAO.getEntity(5));

    }
}
