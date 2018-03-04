package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


/**
 * Class that represents the Option entity mapping into "Options" table in DB
 */
@Entity
@Table(name = "options")
public class Option extends AbstractEntity {
    private int id;
    private String name;
    private int price;
    private int connectionCost;
    private Set<Contract> contracts;
    @JsonIgnore
    private Set<Tariff> tariffs;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "connection_cost", nullable = false)
    public int getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(int connectionCost) {
        this.connectionCost = connectionCost;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "options")
    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "options")
    public Set<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (id != option.id) return false;
        if (price != option.price) return false;
        if (connectionCost != option.connectionCost) return false;
        if (name != null ? !name.equals(option.name) : option.name != null) return false;
        if (contracts != null ? !contracts.equals(option.contracts) : option.contracts != null) return false;
        return tariffs != null ? tariffs.equals(option.tariffs) : option.tariffs == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + connectionCost;
        result = 31 * result + (contracts != null ? contracts.hashCode() : 0);
        result = 31 * result + (tariffs != null ? tariffs.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", connectionCost=" + connectionCost +
                ", contracts=" + contracts +
                ", tariffs=" + tariffs +
                "} " + super.toString();
    }
//    @ManyToMany(fetch = FetchType.EAGER)
//    public Set<Option> getCompatibleOptions() {
//        return compatibleOptions;
//    }
//
//    public void setCompatibleOptions(Set<Option> compatibleOptions) {
//        this.compatibleOptions = compatibleOptions;
//    }
}
