package core.basesyntax.service;

import java.util.Objects;

public class UserStringFieldsValidator implements FieldValidator<String> {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final String IS_BLANK_MESSAGE = " can't be blank";
    private static final String WITH_SPACES_MESSAGE = " can't contain spaces";
    private static final String SHORT_PASSWORD_MESSAGE = "Password length should be "
            + "greater than or equal "
            + MINIMUM_PASSWORD_LENGTH;
    private static final String SPACE_SYMBOL = " ";

    @Override
    public void validate(String field, UserFields fieldName) {
        if (!Objects.nonNull(field)) {
            throw new RuntimeException(fieldName + IS_NULL_MESSAGE);
        }
        if (field.isBlank()) {
            throw new RuntimeException(fieldName + IS_BLANK_MESSAGE);
        }
        if (field.contains(SPACE_SYMBOL)) {
            throw new RuntimeException(fieldName + WITH_SPACES_MESSAGE);
        }
        if (fieldName.equals(UserFields.PASSWORD)) {
            if (field.length() < MINIMUM_PASSWORD_LENGTH) {
                throw new RuntimeException(SHORT_PASSWORD_MESSAGE);
            }
        }
    }
}
