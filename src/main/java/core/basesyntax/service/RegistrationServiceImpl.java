package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullUserOrField(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException("User already exists");
        }
        checkNonValidParameter(user);
        storageDao.add(user);
        return user;
    }

    private void checkNullUserOrField(User user) {
        if (user == null) {
            throw new AuthenticationException(
                    "User should be initialized");
        }
        if (Objects.isNull(user.getLogin())
                || Objects.isNull(user.getPassword())
                || Objects.isNull(user.getAge())) {
            throw new AuthenticationException(
                    "User's fields should be initialized");
        }
    }

    private void checkNonValidParameter(User user) {
        if (user.getPassword().isEmpty() || user.getLogin().isEmpty()) {
            throw new AuthenticationException(
                    "User's login or password can't be empty");
        }
        if (user.getLogin().length() < MIN_LENGTH || user.getPassword().length() < MIN_LENGTH) {
            throw new AuthenticationException(
                    "User's login or password can't be shorter than 6 characters");
        }
        if (user.getAge() < MIN_AGE || user.getAge() >= MAX_AGE) {
            throw new AuthenticationException(
                    "User's age must be between 18 and 100 years inclusively");
        }
        if (!user.getLogin().matches("^[a-zA-Z0-9!@#$%^&*()_+-]+$")
                || !user.getPassword().matches("^[a-zA-Z0-9!@#$%^&*()_+-]+$")) {
            throw new AuthenticationException(
                    "User's login or password must be written in English");
        }
    }
}
