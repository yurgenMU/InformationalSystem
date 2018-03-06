package controllers;

import model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.OptionService;
import validator.OptionValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class OptionController {
    @Autowired
    private OptionService optionService;


    @Autowired
    private OptionValidator optionValidator;

    @RequestMapping(value = "options/registerNew", method = RequestMethod.GET)
    public String getAddPage(Model model) {
        optionService.createAddPageModel(model);
        return "newOption";
    }

    @RequestMapping(value = "options/registerNew", method = RequestMethod.POST)
    public String addNew(
            @ModelAttribute("newOption") Option option,
            BindingResult bindingResult,
            @RequestParam(value = "optionId", required = false) ArrayList<Integer> selectedOptions,
            Model model) {
        optionValidator.validate(option, bindingResult);
        if (bindingResult.hasErrors()) {
            Set<Option> options = optionService.getAll();
            model.addAttribute("newOption", new Option());
            model.addAttribute("options", options);
            return "newOption";
        }
        if (selectedOptions != null) {
            optionService.setupNewOptionParams(option, selectedOptions);
        } else {
            optionService.add(option);
        }
        return "redirect: /";

    }


    @RequestMapping(value = "options/showAll", method = RequestMethod.GET)
    public String listOptions(Model model) {
        Set<Option> options = optionService.getAll();
        model.addAttribute("options", options);
        return "allOptions";
    }


    @GetMapping(value = {"options/edit/addOtherOption", "contracts/addToContract"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addOtherOption(
            @RequestParam("childId") int childId,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        Set<Option> other = (Set<Option>) session.getAttribute("other");
        actual.add(optionService.get(childId));
        other = other.stream().filter(x -> x.getId() != childId).collect(Collectors.toSet());
        session.setAttribute("actual", actual);
        session.setAttribute("other", other);
    }

    @GetMapping(value = {"options/edit/removeActualOption", "contracts/removeFromContract"})
    @ResponseStatus(value = HttpStatus.OK)
    public void removeActualOptions(
            @RequestParam("childId") int childId,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        Set<Option> other = (Set<Option>) session.getAttribute("other");
        actual = actual.stream().filter(x -> x.getId() != childId).collect(Collectors.toSet());
        other.add(optionService.get(childId));
        session.setAttribute("actual", actual);
        session.setAttribute("other", other);
    }

    @GetMapping(value = {"options/edit/getActualSessionOptions", "tariffs/getChosenOptions"
            , "tariffs/edit/getChosenOptions", "contracts/getChosenOption"})
    @ResponseBody
    public Set<Option> getSessionActualOptions(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        return actual;
    }


    @GetMapping(value = {"options/edit/getOtherSessionOptions", "tariffs/getAvailableOptions",
            "tariffs/edit/getAvailableOptions", "contracts/getAvailableOptions"})
    @ResponseBody
    public Set<Option> getSessionOtherOptions(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> other = (Set<Option>) session.getAttribute("other");
        return other;
    }


    @GetMapping(value = {"tariffs/addToTariff", "tariffs/edit/addToTariff"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addOptionToTariff(
            @RequestParam("optionId") int optionId,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        actual.add(optionService.get(optionId));
        Set<Option> other = optionService.getCompatibleOptions(actual)
                .stream()
                .filter(x -> x.getId() != optionId)
                .collect(Collectors.toSet());
        session.setAttribute("actual", actual);
        session.setAttribute("other", other);
    }

    @GetMapping(value = {"tariffs/removeFromTariff", "tariffs/edit/removeFromTariff"})
    @ResponseStatus(value = HttpStatus.OK)
    public void removeOptionfromTariff(
            @RequestParam("optionId") int optionId,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        Set<Option> proxySet = actual.stream().filter(x -> x.getId() != optionId).collect(Collectors.toSet());
        Set<Option> other = optionService.getCompatibleOptions(proxySet);
//        other = other.stream().filter(x-> x.getId()!= optionId).collect(Collectors.toSet());
        session.setAttribute("actual", proxySet);
        session.setAttribute("other", other);
    }

    @GetMapping(value = "options/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        optionService.setEditPageParams(model, id, session);
        return "editOption";
    }


    @PostMapping(value = "options/edit/{id}")
    public String editPage(Model model, @PathVariable int id, HttpServletRequest request,
                           @ModelAttribute("option") Option option, BindingResult bindingResult) {
        optionValidator.validate(option, bindingResult);
        HttpSession session = request.getSession();
        if (bindingResult.hasErrors()) {
            optionService.setEditPageParams(model, id, session);
            return "redirect: /options/edit/" + id;
        }
        Set<Option> act = (Set<Option>) session.getAttribute("actual");
        Set<Option> oth = (Set<Option>) session.getAttribute("other");
        optionService.editOptionParameters(option, act);
        optionService.edit(option);
        return "redirect: /options/showAll";
    }






    @GetMapping(value = "options/remove/{id}")
    public String deleteOption(@PathVariable("id") int id) {
        optionService.remove(id);
        return "redirect: /options/showAll";
    }





}
