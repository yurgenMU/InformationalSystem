package service;


import dao.TariffDAO;
import model.AbstractEntity;
import model.Option;
import model.Tariff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TariffService implements EntityService{
    private TariffDAO tariffDAO;
    private  OptionService optionService;

    public TariffDAO getTariffDAO() {
        return tariffDAO;
    }

    public void setTariffDAO(TariffDAO tariffDAO) {
        this.tariffDAO = tariffDAO;
    }


    public OptionService getOptionService() {
        return optionService;
    }

    public void setOptionService(OptionService optionService) {
        this.optionService = optionService;
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
//        Set<Option> options = optionService.getAll();
//        model.addAttribute("newTariff", new Tariff());
//        model.addAttribute("options", options);
//        return model;
    }

    public void setupNewTariffParams(Tariff tariff, ArrayList<Integer> selectedOptions) {

    }

    public Set<Option> getAllOptions(){
        return optionService.getAll();
    }

    public Model setEditPageParams(Model model, int id, HttpSession session) {
        Tariff tariff = get(id);
        Set<Option> actualOptions = tariff.getOptions();
//        actualOptions = actualOptions.stream().filter(x-> x.getId()!= id).collect(Collectors.toSet());
        Set<Option> otherOptions = optionService.getRestOptions(actualOptions);
        model.addAttribute("tariff", tariff);
        model.addAttribute("actual", actualOptions);
        model.addAttribute("other", otherOptions);
        session.setAttribute("tariff", tariff);
        session.setAttribute("actual", actualOptions);
        session.setAttribute("other", otherOptions);
        return model;
    }

    public void editTariffParameters(Tariff tariff, Set<Option> act) {
        tariff.setOptions(act);
        edit(tariff);
        Tariff tariff1 = get(tariff.getId());
    }
}
