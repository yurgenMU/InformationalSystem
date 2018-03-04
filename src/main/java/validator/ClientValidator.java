package validator;

import model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import service.ClientService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidator implements Validator {
    @Autowired
    private ClientService clientService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateValue", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passportData", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
//        if (client.getLogin().length() < 8 || client.getLogin().length() > 32) {
//            errors.rejectValue("login", "Size.client.login");
//        }
        if (client.getFirstName().trim().length() == 0) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");
        }
        if (client.getLastName().trim().length() == 0) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required");
        }
        if ((clientService.getByName(client.getEmail()) != null) &&
                ((clientService.getByName(client.getEmail()).getId() != client.getId()))) {
            errors.rejectValue("login", "Duplicate.client.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (client.getPassword().trim().length() < 8 || client.getPassword().trim().length() > 32) {
            errors.rejectValue("password", "Size.client.password");
        }

        if (!client.getConfirmPassword().equals(client.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.client.password");
        }


        if (!validateEmail(client.getEmail())) {
            errors.rejectValue("email", "Email.invalid");
        }

        if (!validateDate(client.getDateValue())){
            errors.rejectValue("dateValue", "Date.invalid");
        }
    }


    public boolean validateEmail(String emailStr) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(emailStr);
        return matcher.find();
    }


    private boolean validateDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        Date parsed = null;
        try {
            date = date.replaceAll("\\.", "");
            parsed = format.parse(date);
            if (parsed != null) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
