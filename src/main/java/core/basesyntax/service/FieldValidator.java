package core.basesyntax.service;

public interface FieldValidator<T> {
    void validate(T field, String fieldName);

    default boolean fieldIsNull(T field) {
        return field == null;
    }
}
