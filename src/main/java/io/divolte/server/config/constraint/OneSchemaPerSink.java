/*
 * Copyright 2018 GoDataDriven B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.divolte.server.config.constraint;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import io.divolte.server.config.DivolteConfiguration;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy=OneSchemaPerSink.Validator.class)
@Documented
public @interface OneSchemaPerSink {
    String message() default "Any sink can only use one schema. The following sinks have multiple mappings with different schema's linked to them: ${validatedValue.sinksWithMultipleSchemas()}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<OneSchemaPerSink, DivolteConfiguration> {
        @Override
        public void initialize(final OneSchemaPerSink constraintAnnotation) {
            // Nothing needed here.
        }

        @Override
        public boolean isValid(final DivolteConfiguration value, final ConstraintValidatorContext context) {
            return value.sinksWithMultipleSchemas().isEmpty();
        }
    }
}
