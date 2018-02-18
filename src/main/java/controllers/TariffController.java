package controllers;

import model.Tariff;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.TariffService;
import validator.TariffValidator;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class TariffController {
    private TariffService tariffService;

    private TariffValidator tariffValidator;


    @RequestMapping(value = "tariffs/registerNew", method = RequestMethod.GET)
    public String getAddPage(Model model) {
        tariffService.createAddPageModel(model);
        return "newTariff";
    }

    @RequestMapping(value = "tariffs/registerNew", method = RequestMethod.POST)
    public String addNew(
            @ModelAttribute("newTariff") Tariff tariff,
            BindingResult bindingResult,
            @RequestParam(value = "optionId", required = false) ArrayList<Integer> selectedOptions,
            Model model) {
        tariffValidator.validate(tariff, bindingResult);
        if (bindingResult.hasErrors()) {
            Set<Tariff> tariffs = tariffService.getAll();
            model.addAttribute("newTariff", new Tariff());
            model.addAttribute("tariffs", tariffs);
            return "newOption";
        }
        if (selectedOptions != null) {
            tariffService.setupNewTariffParams(tariff, selectedOptions);
        } else {
            tariffService.add(tariff);
        }
        return "redirect: /";

    }


    @RequestMapping(value = "tariffs/showAll", method = RequestMethod.GET)
    public String listOptions(Model model) {
        Set<Tariff> tariffs = tariffService.getAll();
        model.addAttribute("tariffs", tariffs);
        return "allOptions";
    }


}
