package com.diandong.configuration;

import com.diandong.utils.BizRegexUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

@Slf4j
public class PhoneConstraintValidator implements ConstraintValidator<PhoneConstraint, String> {

    @Override
    public void initialize(PhoneConstraint constraintAnnotation) {
        log.info("初始化手机号码校验格式");
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(BizRegexUtil.phoneRegex, phone);
    }
}
