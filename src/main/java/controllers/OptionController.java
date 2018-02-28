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

    @PostMapping(value = "restOption/{id}")
    @ResponseBody
    public Set<Option> getCompatible(@PathVariable int id){
        Set<Option> options = optionService.getOptionDAO().getCompatibleOptions(optionService.get(id));
        return options;
    }


//    @GetMapping(value = "options/edit/addActualOptions/{id}")
//    @ResponseBody
//    public Set<Option> getCompatibleOptions(@PathVariable int id){
//        Option option = optionService.get(id);
//        return optionService.getCompatibleOptions(option);
//    }
//
//    @GetMapping(value = "options/edit/addOtherOption")
//    @ResponseBody
//    public Set<Option> getRestOptions(@PathVariable int id, HttpServletRequest request){
//        Set<Option> options = getCompatibleOptions(id);
//        options = options.stream().filter(x -> x.getId() != id).collect(Collectors.toSet());
//        request.getSession().setAttribute("actualOptions", options);
//        return optionService.getRestOptions(id, options);
//    }

    @GetMapping(value = "options/edit/addOtherOption")
    @ResponseStatus(value = HttpStatus.OK)
    public void addOtherOption(@RequestParam("parentId") int parentId,
                                      @RequestParam("childId") int childId,
                                      HttpServletRequest request){
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        Set<Option> other = (Set<Option>) session.getAttribute("other");
        System.out.println(actual);
        System.out.println(other);
        actual.add(optionService.get(childId));
        other = other.stream().filter(x-> x.getId() != childId).collect(Collectors.toSet());
        session.setAttribute("actual", actual);
        session.setAttribute("other", other);
    }

    @GetMapping(value = "options/edit/removeActualOption")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeActualOptions(@RequestParam("parentId") int parentId,
                                        @RequestParam("childId") int childId,
                                        HttpServletRequest request){
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        Set<Option> other = (Set<Option>) session.getAttribute("other");
        actual = actual.stream().filter(x-> x.getId() != childId).collect(Collectors.toSet());
        other.add(optionService.get(childId));
        session.setAttribute("actual", actual);
        session.setAttribute("other", other);
    }

    @GetMapping(value = "options/edit/getActualSessionOptions")
    @ResponseBody
    public Set<Option> getSessionActualOptions(HttpServletRequest request){
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("actual");
        System.out.println(actual);
        return actual;
    }


    @GetMapping(value = "options/edit/getOtherSessionOptions")
    @ResponseBody
    public Set<Option> getSessionOtherOptions(HttpServletRequest request){
        HttpSession session = request.getSession();
        Set<Option> actual = (Set<Option>) session.getAttribute("other");
        System.out.println(actual);
        return actual;
    }


    @GetMapping(value = "options/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id, HttpServletRequest request){
        Option option = optionService.get(id);
        Set<Option> actualOptions = optionService.getCompatibleOptions(option);
        Set<Option> otherOptions = optionService.getRestOptions(id, actualOptions);
        model.addAttribute("option", option);
        model.addAttribute("actual", actualOptions);
        model.addAttribute("other", otherOptions);
        request.getSession().setAttribute("option", option);
        request.getSession().setAttribute("actual", actualOptions);
        request.getSession().setAttribute("other", otherOptions);
        return "editOption";
    }




}
