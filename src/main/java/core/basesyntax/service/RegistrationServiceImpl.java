package core.basesyntax.service;

import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        if (user.getAge() >= 18 && user.getPassword().length() >= 6 && user.getId() == null
                && user.getLogin() != null && user.getPassword() != null) {
            return user;
        }
        throw new RuntimeException("Invalid data");
    }
}
