package com.projetointegrado.MeuBolso.globalConstraints.NotBlankIfPresent;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "O campo deve conter pelo menos um caractere não-espaço.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}