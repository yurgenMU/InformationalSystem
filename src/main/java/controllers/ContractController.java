package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ContractService;


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
    public String getCreatePage(@RequestParam("clientId") int clientId, Model model) {
        model.addAttribute("client", contractService.getClientService().get(clientId));
        model.addAttribute("tariffs", contractService.getTariffService().getAll());
        return "newContract";

    }
}
