package core.basesyntax.service;

import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);

    boolean userExistCheck(User user);

    boolean loginSizeCheck(User user);

    boolean passwordSizeCheck(User user);

    boolean ageCheck(User user);
}
