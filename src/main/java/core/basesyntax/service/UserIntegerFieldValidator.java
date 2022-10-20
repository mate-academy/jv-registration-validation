package core.basesyntax.service;

public class UserIntegerFieldValidator implements FieldValidator<Integer> {
    private static final String AGE_FIELD_STRING = "age";
    private static final String IS_NULL_MESSAGE = " can't be null";
    private static final String TOO_YOUNG_MESSAGE = "User age should be greater then ";
    private static final int MINIMUM_AGE_REQUIRED = 18;

    @Override
    public void validate(Integer field, String fieldName) {
        if (fieldIsNull(field)) {
            throw new RuntimeException(fieldName + IS_NULL_MESSAGE);
        }
        if (fieldName.equals(AGE_FIELD_STRING)) {
            if (field < MINIMUM_AGE_REQUIRED) {
                throw new RuntimeException(TOO_YOUNG_MESSAGE
                        + MINIMUM_AGE_REQUIRED);
            }
        }
    }
}
