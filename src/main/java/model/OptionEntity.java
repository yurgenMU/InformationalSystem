package model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "options")
public class OptionEntity extends AbstractEntity{
    private int id;
    private String name;
    private int value;
    private Set<ContractEntity> contracts;
    private Set<TariffEntity> tariffs;



    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Column(name = "value", nullable = false)
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "options")
    public Set<ContractEntity> getContracts() {
        return contracts;
    }

    public void setContracts(Set<ContractEntity> contracts) {
        this.contracts = contracts;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "options")
    public Set<TariffEntity> getTariffs() {
        return tariffs;
    }

    public void setTariffs(Set<TariffEntity> tariffs) {
        this.tariffs = tariffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionEntity that = (OptionEntity) o;

        if (id != that.id) return false;
        if (value != that.value) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }
}
