package core.basesyntax.service;

public interface FieldValidator<T> {
    String IS_NULL_MESSAGE = " can't be null";

    void validate(T field, UserFields fieldName);
}
