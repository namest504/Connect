package xyz.connect.post.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileListValidator.class})
public @interface CheckContentType {

    String message() default "지원하지 않는 Content-Type 입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}