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
    public User register(User user) throws AuthenticationException {
        if (nullUserOrField(user)) {
            throw new AuthenticationException();
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new AuthenticationException();
        }
        if (nonValidParameter(user)) {
            throw new AuthenticationException();
        }
        storageDao.add(user);
        return user;
    }

    private boolean nullUserOrField(User user) {
        if (user == null) {
            return true;
        }
        if (Objects.isNull(user.getLogin())
                || Objects.isNull(user.getPassword())
                || Objects.isNull(user.getAge())) {
            return true;
        }
        return false;
    }

    private boolean nonValidParameter(User user) {
        if (user.getPassword().isEmpty() || user.getLogin().isEmpty()) {
            return true;
        }
        if (user.getLogin().length() < MIN_LENGTH || user.getPassword().length() < MIN_LENGTH) {
            return true;
        }
        if (user.getAge() < MIN_AGE || user.getAge() >= MAX_AGE) {
            return true;
        }
        if (!user.getLogin().matches("^[a-zA-Z0-9!@#$%^&*()_+-]+$")
                || !user.getPassword().matches("^[a-zA-Z0-9!@#$%^&*()_+-]+$")) {
            return true;
        }
        return false;
    }
}
