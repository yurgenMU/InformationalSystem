package controllers;

import model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.ClientService;
import service.SecurityService;
import validator.ClientValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class ClientController {
    private ClientService clientService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ClientValidator clientValidator;

    public ClientService getClientService() {
        return clientService;
    }

    @Autowired
    @Qualifier(value = "clientService")
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public ClientValidator getClientValidator() {
        return clientValidator;
    }

    public void setClientValidator(ClientValidator clientValidator) {
        this.clientValidator = clientValidator;
    }

    @RequestMapping(value = "clients/new", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("client", new Client());

        return "newClient";
    }


    /**
     *
     * @param userForm
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value = "clients/new", method = RequestMethod.POST)
    public String registration(@ModelAttribute("client") Client userForm, BindingResult bindingResult, Model model) {
        clientValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "newClient";
        }

        clientService.add(userForm);

//        securityService.autoLogin(userForm.getLogin(), userForm.getConfirmPassword());

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/mobileOperator"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = AuthorityUtils
                .authorityListToSet(authentication.getAuthorities());
        org.springframework.security.core.userdetails.User modelUser =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String name = modelUser.getUsername();
        Client client = clientService.getByName(name);
        model.addAttribute("user", client);
        if (roles.contains("ROLE_ADMIN")) {
            return "welcomeAdmin";
        }
        return "welcomeUser";
    }


    @GetMapping(value = "clients/management")
    public String getManagementPage(Model model){
        model.addAttribute("clients", clientService.getAll());
        return "allClients";
    }

    @GetMapping(value = "clients/searchClientByName")
    @ResponseBody
    public Set<Client> searchByName(@RequestParam("name") String name){
        return clientService.getByInitials(name);
    }


//    @GetMapping(value = "options/edit/{id}")
    public String getEditPage(Model model, @PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
//        clientService.setEditPageParams(model, id, session);
        return "editOption";
    }


}
