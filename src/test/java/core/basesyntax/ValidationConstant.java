package core.basesyntax;

public enum ValidationConstant {
    VALID_PASSWORD("VALID_PASSWORD"),
    INVALID_SHORT_PASSWORD("ISP"),
    VALID_AGE(30),
    MIN_VALID_AGE(18),
    MIN_INVALID_AGE(16),
    NEGATIVE_INVALID_AGE(-10),
    INVALID_LARGE_AGE(130),
    VALID_LOGIN("VALID_LOGIN");

    private final Object value;

    ValidationConstant(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
