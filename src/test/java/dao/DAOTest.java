//package dao;
//
//
//import model.Client;
//import model.Contract;
//import model.Option;
//import model.Tariff;
//import org.junit.Test;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//
//public class DAOTest {
//    ClientDAO clientDAO = new ClientDAO();
//    ContractDAO contractDAO = new ContractDAO();
//    OptionDAO optionDAO = new OptionDAO();
//    TariffDAO tariffDAO = new TariffDAO();
//
//    @Test
//    public void testConnection() throws ParseException {
//        Tariff tariff = new Tariff();
//        tariff.setName("first tariff");
//        tariff.setPrice(300);
//        Option firstOption = new Option();
//        firstOption.setName("First option");
//        firstOption.setPrice(150);
//        firstOption.setConnectionCost(50);
//        Option secondOption = new Option();
//        secondOption.setName("Second option");
//        secondOption.setPrice(100);
//        secondOption.setConnectionCost(60);
//        Client firstClient = new Client();
//        firstClient.setFirstName("Igor");
//        firstClient.setLastName("Nikolaev");
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        Date parsed = format.parse("19900210");
//        java.sql.Date sql = new java.sql.Date(parsed.getTime());
//        firstClient.setBirth(sql);
//        firstClient.setPassportData("Выдан кем-то там");
//        firstClient.setAddress("Адрес");
//        firstClient.setEmail("admin@admin.ru");
//        firstClient.setPassword("1111111111");
//        firstClient.setRole("ROLE_USER");
//        Contract contract = new Contract();
//        contract.setClient(firstClient);
//        contract.setTariff(tariff);
//        contract.setNumber("911");
//        contract.setActive(false);
//        Set<Option> options = new HashSet<>();
//        options.add(firstOption);
//        options.add(secondOption);
//        Set<Contract> contracts = new HashSet<>();
//        contracts.add(contract);
//        tariff.setOptions(options);
////        tariffDAO.addEntity(tariff);
//        firstClient.setContracts(contracts);
//        Contract contract1 = new Contract();
//        contract1.setClient(firstClient);
//        contract1.setTariff(tariff);
//        contract1.setNumber("921");
//        contract1.setActive(true);
////        contractDAO.addEntity(contract1);
//        Client client2 = clientDAO.getEntityByName("ЖОПА");
//        contracts.add(contract1);
//        client2.setContracts(contracts);
//        clientDAO.editEntity(client2);
////        clientDAO.addEntity(firstClient);
//    }
//
//}
