/* LicenseInfo : Copyright © 2023 Piramal */ 
package com.iemr.mmu.annotation.sqlInjectionSafe;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SQLInjectionSafeConstraintValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInjectionSafe {

    String message() default "{SQLInjectionSafe}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
