package controllers;

import model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.OptionService;
import validator.OptionValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping(value = "restOption")
    @ResponseBody
    private Set<Option> showAll(){
        Set<Option> options = optionService.getAll();
        return options;
    }


//    public String editOptionPage(){
//
//    }
//
//    @RequestMapping(value = "OfficeProject/projects/edit", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String updateExisting(@ModelAttribute("mproject") Project mproject) {
//        projectService.add(mproject);
//        return "projects";
//    }
//
//
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @RequestMapping(value = "OfficeProject/projects/edit", method = RequestMethod.GET)
//    public String getEditPage(Model model, @RequestParam("projectId") int id) {
//        projectService.createEditPageModel(id, model);
//        return "editProject";
//    }
//
//    @RequestMapping(value = "OfficeProject/projects/removeFrom", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String deleteUsersFromProject(@RequestParam("projectId") int id,
//                                         Model model,
//                                         @RequestParam(value = "auserId", required = false) ArrayList<Integer> selectedUsers
//    ) {
//        if (selectedUsers != null) {
//            projectService.deleteUsersOperation(id, selectedUsers);
//        }
//        getEditPage(model, id);
//        return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
//    }
//
//
//    @RequestMapping(value = "OfficeProject/projects/addInto", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String addUsersIntoProject(@RequestParam("projectId") int id,
//                                      Model model,
//                                      @RequestParam(value = "nuserId", required = false) ArrayList<Integer> selectedUsers
//    ) {
//        if (selectedUsers != null) {
//            projectService.addToExisting(id, selectedUsers);
//        }
//        getEditPage(model, id);
//        return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
//    }
//
//
//    @RequestMapping(value = "OfficeProject/projects/changeName", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String changeProjectName(@RequestParam("projectId") int id,
//                                    @ModelAttribute("mproject") Project project,
//                                    BindingResult bindingResult,
//                                    Model model
//    ) {
//        projectValidator.validate(project, bindingResult);
//        if (bindingResult.hasErrors()) {
//            projectService.createEditPageModel(id, model);
//            return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
//        }
//        projectService.changeName(id, project.getName());
//        getEditPage(model, id);
//        return "redirect: /OfficeProject/OfficeProject/projects/edit?projectId=" + id;
//
//    }


}
