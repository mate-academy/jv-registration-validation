package core.basesyntax.service;

import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);

    void removeAllUsers();

    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isAdult(int age);

    boolean userExists(User user);
}
