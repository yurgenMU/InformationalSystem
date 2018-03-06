package controllers;

import model.Contract;
import model.Option;
import model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ContractService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;


@Controller
public class ContractController {

    @Autowired
    private ContractService contractService;

    public ContractService getContractService() {
        return contractService;
    }

    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(value = "contracts/new")
    public String getCreatePage(@RequestParam("clientId") int clientId, Model model, HttpServletRequest req) {
        model.addAttribute("client", contractService.getClientService().get(clientId));
        model.addAttribute("tariffs", contractService.getTariffService().getAll());
        HttpSession session = req.getSession();
        return "newContract";

    }

    @GetMapping(value = "contracts/saveNew")
    public String createNewContract(@RequestParam("clientId") int clientId, Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        contractService.addNewContractModel(session, clientId);
        return "redirect:/";

    }



    @GetMapping(value = "contracts/generatePhoneNumber")
    @ResponseBody
    public String generateNumber(HttpServletRequest req){
        String number = contractService.generateNumber();
        HttpSession session = req.getSession();

        session.setAttribute("phone", number);
        return number;
    }


    @GetMapping(value = "contracts/addTariffToBasket")
//    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addTariffToBasket(@RequestParam("tariff") String tariff, HttpServletRequest req){
//        contractService.setContractTariff(tariff, req.getSession());
        HttpSession session = req.getSession();
        Tariff tariff1 = contractService.getTariffService().getByName(tariff);
        Set<Option> actual = tariff1.getOptions();
        session.setAttribute("tariff", tariff1);
        session.setAttribute("actual", actual);
        session.setAttribute("other", contractService.getOptionService().getCompatibleOptions(actual));
        return "OK";
    }



    @GetMapping(value = "contracts/addPhoneToBasket")
    @ResponseStatus(value = HttpStatus.OK)
    public void addOptionToBasket(@ModelAttribute("phone") String phone, HttpServletRequest req){
        HttpSession session = req.getSession();

        session.setAttribute("phone", phone);
    }




}
