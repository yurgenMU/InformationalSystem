package service;

import dao.OptionDAO;
import model.AbstractEntity;
import model.Option;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        Option option = (Option) entity;
        Option proxyOption = optionDAO.getEntityByName(option.getName());
        optionDAO.addCompatibleOption(proxyOption, proxyOption);

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
        optionDAO.editEntity(entity);
    }

    @Override
    @Transactional
    public Set<Option> getAll() {
        return optionDAO.getAllEntities();
    }

    @Override
    @Transactional
    public void remove(int id) {
        Option option = optionDAO.getEntity(id);
        optionDAO.getCompatibleOptions(option).stream().forEach(x -> optionDAO.deleteCompatibleOption(option, x));
        optionDAO.removeEntity(id);
    }


    public Set<Option> getCompatibleOptions(Option option) {
        return optionDAO.getCompatibleOptions(option);
    }

    public Set<Option> getCompatibleOptions(Set<Option> options){
        return optionDAO.getCompatibleOptions(options);
    }


    public Set<Option> getRestOptions(Set<Option> options) {
        Set<Option> ans = optionDAO.getTheRest(options);

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

    public Model setEditPageParams(Model model, int id, HttpSession session) {
        Option option = get(id);
        Set<Option> actualOptions = getCompatibleOptions(option);
        actualOptions = actualOptions.stream().filter(x-> x.getId()!= id).collect(Collectors.toSet());
        Set<Option> otherOptions = getRestOptions(actualOptions);
        otherOptions = otherOptions.stream().filter(x -> x.getId() != id).collect(Collectors.toSet());
        model.addAttribute("option", option);
        model.addAttribute("actual", actualOptions);
        model.addAttribute("other", otherOptions);
        session.setAttribute("option", option);
        session.setAttribute("actual", actualOptions);
        session.setAttribute("other", otherOptions);
        return model;
    }


    public void editOptionParameters(Option option, Set<Option> comp) {
        Set<Option> compNow = optionDAO.getCompatibleOptions(option);
        compNow.stream().forEach(x -> {
            if (!comp.contains(x)) {
                optionDAO.deleteCompatibleOption(option, x);
            }
        });
        comp.stream().forEach(x -> {
            if (!compNow.contains(x)) {
                optionDAO.addCompatibleOption(option, x);
            }
        });
        edit(option);
    }


}
