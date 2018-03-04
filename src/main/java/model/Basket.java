package model;

import java.util.Set;

public class Basket {
    private String number;
    private Client client;
    private Tariff tariff;
    private Set<Option> chosenOptions;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Set<Option> getChosenOptions() {
        return chosenOptions;
    }

    public void setChosenOptions(Set<Option> chosenOptions) {
        this.chosenOptions = chosenOptions;
    }
}
