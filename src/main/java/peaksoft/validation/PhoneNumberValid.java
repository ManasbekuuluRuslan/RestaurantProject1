package peaksoft.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PhoneNumberValid {
    String message() default "{The phone number must start with '+996' and the phone number must be 13 characters long.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}