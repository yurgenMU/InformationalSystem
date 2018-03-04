package controllers;

import model.Option;
import model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.TariffService;
import validator.TariffValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @Autowired
    private TariffValidator tariffValidator;

    public TariffService getTariffService() {
        return tariffService;
    }

    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    public TariffValidator getTariffValidator() {
        return tariffValidator;
    }

    public void setTariffValidator(TariffValidator tariffValidator) {
        this.tariffValidator = tariffValidator;
    }


    @RequestMapping(value = "tariffs/registerNew", method = RequestMethod.GET)
    public String getAddPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> actual = new HashSet<>();
        session.setAttribute("actual", actual);
        session.setAttribute("other", tariffService.getAllOptions());
        model.addAttribute("newTariff", new Tariff());
        return "newTariff";
    }

    @RequestMapping(value = "tariffs/registerNew", method = RequestMethod.POST)
    public String addNew(@ModelAttribute Tariff tariff,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {
        tariffValidator.validate(tariff, bindingResult);
        if (bindingResult.hasErrors()) {
            Set<Option> options = tariffService.getAllOptions();
            model.addAttribute("newTariff", new Tariff());
            model.addAttribute("options", options);
            return "newTariff";
        }
        HttpSession session = request.getSession();
        Set<Option> act = (Set<Option>) session.getAttribute("actual");
        tariff.setOptions(act);
        tariffService.add(tariff);
        return "redirect: /";

    }

    @RequestMapping(value = "tariffs/management", method = RequestMethod.GET)
    public String listOptions(Model model) {
        Set<Tariff> tariffs = tariffService.getAll();
        model.addAttribute("tariffs", tariffs);
        return "allTariffs";
    }


    @GetMapping(value = "tariffs/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        tariffService.setEditPageParams(model, id, session);
        return "editTariff";
    }


    @PostMapping(value = "tariffs/edit/{id}")
    public String editPage(Model model, @PathVariable int id, HttpServletRequest request,
                           @ModelAttribute("tariff") Tariff tariff, BindingResult bindingResult) {
        tariffValidator.validate(tariff, bindingResult);
        HttpSession session = request.getSession();
        if (bindingResult.hasErrors()) {
            tariffService.setEditPageParams(model, id, session);
            return "redirect: /tariffs/edit/" + id;
        }
        Set<Option> act = (Set<Option>) session.getAttribute("actual");
        tariffService.editTariffParameters(tariff, act);
        return "redirect: /tariffs/management";
    }


    @GetMapping(value = "tariffs/remove/{id}")
    public String deleteTariff(@PathVariable("id") int id) {
        tariffService.remove(id);
        return "redirect: /options/showAll";
    }

}
