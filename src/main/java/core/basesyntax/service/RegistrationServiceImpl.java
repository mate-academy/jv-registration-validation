package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.concurrent.atomic.AtomicLong;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }

        final String login = user.getLogin();
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "Login must be at least " + MIN_LOGIN_LENGTH + " characters");
        }

        final String password = user.getPassword();
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
        }

        final Integer age = user.getAge();
        if (age == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (age < 0) {
            throw new RegistrationException("Age can't be negative");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException(
                    "User must be at least " + MIN_AGE + " years old");
        }

        boolean exists = Storage.people.stream()
                .anyMatch(u -> login.equals(u.getLogin()));
        if (exists) {
            throw new RegistrationException("User with this login already exists");
        }

        final Long newId = ID_GENERATOR.getAndIncrement();
        user.setId(newId);
        Storage.people.add(user);
        return user;
    }
}
