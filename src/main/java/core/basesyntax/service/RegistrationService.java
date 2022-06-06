package core.basesyntax.service;

import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);

    boolean passwordIsValid(String password);

    boolean ageIsValid(Integer age);

}
