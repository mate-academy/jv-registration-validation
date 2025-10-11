package core.basesyntax.service;

import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);

    boolean userExistCheck(String login);

    boolean loginSizeCheck(String login);

    boolean passwordSizeCheck(String password);

    boolean ageCheck(int age);
}
