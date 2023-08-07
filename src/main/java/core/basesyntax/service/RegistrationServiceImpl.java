package core.basesyntax.service;

import core.basesyntax.model.User;
import core.basesyntax.utils.UserValidator;
import java.util.ArrayList;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private final List<User> registeredUsers = new ArrayList<>();
    private final UserValidator userValidator = new UserValidator();

    @Override
    public User register(User user) {
        userValidator.validate(user);
        if (duplicateUser(user)) {
            throw new RegistrationException("User already exists");
        }
        registeredUsers.add(user);
        return user;
    }

    private boolean duplicateUser(User newUser) {
        return registeredUsers.stream()
                .anyMatch(user -> user.getLogin().equals(newUser.getLogin()));
    }
}
