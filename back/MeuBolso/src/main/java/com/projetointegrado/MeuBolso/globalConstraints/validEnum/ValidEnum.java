package com.projetointegrado.MeuBolso.globalConstraints.validEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnumValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    Class<? extends Enum<?>> value();
    String message() default "Invalid value for enum";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
