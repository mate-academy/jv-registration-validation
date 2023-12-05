package core.basesyntax.service.validators;

public interface Validator<T> {
    void validate(T entity);
}
