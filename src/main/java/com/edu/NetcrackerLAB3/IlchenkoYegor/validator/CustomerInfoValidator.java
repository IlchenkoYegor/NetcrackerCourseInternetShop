package com.edu.NetcrackerLAB3.IlchenkoYegor.validator;

import com.edu.NetcrackerLAB3.IlchenkoYegor.entitiy.Account;
import com.edu.NetcrackerLAB3.IlchenkoYegor.model.CustomerInfo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Errors;

@Component
public class CustomerInfoValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();
    // This Validator support CustomerInfo class.
/*    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == CustomerInfo.class;
    } */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == Account.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account custInfo = (Account) target;

        // Check the fields of CustomerInfo class.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "NotEmpty.customerForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerEmail", "NotEmpty.customerForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerAdress", "NotEmpty.customerForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPhone", "NotEmpty.customerForm.phone");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.customerForm.password");

        String passw = custInfo.getPassword();
        if(passw.length()<3){
            errors.rejectValue("password", "Minsize.customerForm.password" );
        }

        if (!emailValidator.isValid(custInfo.getCustomerEmail())) {
            errors.rejectValue("customerEmail", "Pattern.customerForm.email");
        }

    }

}
