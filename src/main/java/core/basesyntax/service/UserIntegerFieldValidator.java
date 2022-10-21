package core.basesyntax.service;

import java.util.Objects;

public class UserIntegerFieldValidator implements FieldValidator<Integer> {
    private static final int MINIMUM_AGE_REQUIRED = 18;
    private static final String TOO_YOUNG_MESSAGE = "User age should be greater then "
            + MINIMUM_AGE_REQUIRED;

    @Override
    public void validate(Integer field, UserFields fieldName) {
        if (!Objects.nonNull(field)) {
            throw new RuntimeException(fieldName + IS_NULL_MESSAGE);
        }
        if (fieldName.equals(UserFields.AGE)) {
            if (field < MINIMUM_AGE_REQUIRED) {
                throw new RuntimeException(TOO_YOUNG_MESSAGE);
            }
        }
    }
}
