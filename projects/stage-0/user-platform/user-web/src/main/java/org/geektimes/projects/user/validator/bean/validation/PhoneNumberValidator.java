package org.geektimes.projects.user.validator.bean.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号校验器
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    /**
     * 中国大陆正则表达式Pattern  From Internet
     */
    private static final Pattern CHINA_PATTERN =
            Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

    @Override
    public void initialize(PhoneNumber annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && value.length() == 11) {
            Matcher matcher = CHINA_PATTERN.matcher(value);
            return matcher.matches();
        }

        return false;
    }
}
