package service;

import dao.OptionDAO;
import model.AbstractEntity;
import model.Option;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OptionService implements EntityService {
    private OptionDAO optionDAO;

    public OptionDAO getOptionDAO() {
        return optionDAO;
    }

    public void setOptionDAO(OptionDAO optionDAO) {
        this.optionDAO = optionDAO;
    }

    @Override
    @Transactional
    public void add(AbstractEntity entity) {
        optionDAO.addEntity(entity);
    }

    @Override
    @Transactional
    public Option get(int id) {
        return optionDAO.getEntity(id);
    }

    @Override
    @Transactional
    public Option getByName(String name) {
        return optionDAO.getEntityByName(name);
    }

    @Override
    @Transactional
    public void edit(AbstractEntity entity) {

    }

    @Override
    @Transactional
    public Set<Option> getAll() {
        return optionDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {

    }


    public Set<Option> getCompatibleOptions(Option option) {
        return optionDAO.getCompatibleOptions(option);
    }


    public Set<Option> getRestOptions(int id, Set<Option> options) {
        Set<Option> ans = optionDAO.getTheRest(options);
        ans = ans.stream().filter(x -> x.getId() != id).collect(Collectors.toSet());
        return ans;
    }


    public Model createAddPageModel(Model model) {
        Set<Option> options = getAll();
        model.addAttribute("newOption", new Option());
        model.addAttribute("options", options);
        model.addAttribute("optionId", new ArrayList<Integer>());
        return model;
    }

    public void setupNewOptionParams(Option option, ArrayList<Integer> selectedOptions) {
        Set<Option> options = new HashSet<>();
        selectedOptions.stream().forEach(x -> options.add(get(x)));
        add(option);
        Option proxyOption = getByName(option.getName());
        options.stream().forEach(x -> optionDAO.addCompatibleOption(option, x));
//        optionDAO.setCompatibleOptions(option, options);
//        project.setUsers(options);
    }


}
