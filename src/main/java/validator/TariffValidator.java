package validator;

import model.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.TariffService;

public class TariffValidator implements Validator{

    @Autowired
    private TariffService optionService;

    @Override
    public void validate(Object o, Errors errors) {
        Tariff tariff = (Tariff) o;
        Tariff proxyTariff = (Tariff) optionService.getByName(tariff.getName().trim());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Required");
        if ((proxyTariff != null) &&
                (proxyTariff.getId() != tariff.getId())) {
            errors.rejectValue("name", "Duplicate.tariff");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Tariff.class.equals(aClass);
    }
}
