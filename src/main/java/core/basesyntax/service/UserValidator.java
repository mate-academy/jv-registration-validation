package core.basesyntax.service;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public interface UserValidator {
    void validate(User user) throws ValidationException;
    void validateLogin(String login) throws ValidationException;
    void validatePassword(String password) throws ValidationException;
    void validateAge(Integer age) throws ValidationException;
}
