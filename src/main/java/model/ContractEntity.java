package model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "contracts")
public class ContractEntity extends AbstractEntity{
    private int id;
    private String number;
    private TariffEntity tariff;
    private boolean isActive;
    private ClientEntity client;
    private Set<OptionEntity> options;



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
    @Column(name = "number", nullable = false, length = 100)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Basic
    @Column(name = "is_active", columnDefinition = "TINYINT(1)", nullable = false)
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="client_id", nullable=false)
    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "contract_option",
            joinColumns = {@JoinColumn(name = "contract_id")},
            inverseJoinColumns = {@JoinColumn(name = "option_id")}
    )
    public Set<OptionEntity> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionEntity> options) {
        this.options = options;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="tariff_id", nullable=false)
    public TariffEntity getTariff() {
        return tariff;
    }

    public void setTariff(TariffEntity tariff) {
        this.tariff = tariff;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractEntity that = (ContractEntity) o;

        if (id != that.id) return false;
        if (isActive != that.isActive) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (tariff != null ? !tariff.equals(that.tariff) : that.tariff != null) return false;
        if (client != null ? !client.equals(that.client) : that.client != null) return false;
        return options != null ? options.equals(that.options) : that.options == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (tariff != null ? tariff.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }
}
