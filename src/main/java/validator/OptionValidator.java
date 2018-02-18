package validator;

import model.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.OptionService;

public class OptionValidator implements Validator {

    @Autowired
    private OptionService optionService;

    @Override
    public void validate(Object o, Errors errors) {
        Option option = (Option) o;
        Option proxyOption = (Option) optionService.getByName(option.getName().trim());
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "connectionCost", "Required");
        if ((proxyOption != null) &&
                (proxyOption.getId() != option.getId())) {
            errors.rejectValue("name", "Duplicate.option");
        }

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Option.class.equals(aClass);
    }


    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}

