package io.bootify.my_app.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import io.bootify.my_app.service.ScanSerialService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the serialItem value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = ScanSerialSerialItemUnique.ScanSerialSerialItemUniqueValidator.class
)
public @interface ScanSerialSerialItemUnique {

    String message() default "{Exists.scanSerial.serialItem}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ScanSerialSerialItemUniqueValidator implements ConstraintValidator<ScanSerialSerialItemUnique, String> {

        private final ScanSerialService scanSerialService;
        private final HttpServletRequest request;

        public ScanSerialSerialItemUniqueValidator(final ScanSerialService scanSerialService,
                final HttpServletRequest request) {
            this.scanSerialService = scanSerialService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("serialId");
            if (currentId != null && value.equalsIgnoreCase(scanSerialService.get(Long.parseLong(currentId)).getSerialItem())) {
                // value hasn't changed
                return true;
            }
            return !scanSerialService.serialItemExists(value);
        }

    }

}
