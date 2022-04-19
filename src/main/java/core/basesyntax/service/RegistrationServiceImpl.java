package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao data = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isLoginCorrect(user.getLogin())
                && isPasswordCorrect(user.getPassword())
                && isAgeCorrect(user.getAge())
                && !Objects.equals(data.get(user.getLogin()), user)) {
            data.add(user);
            return user;
        }
        throw new RuntimeException("Can't register user " + user);
    }

    private boolean isAgeCorrect(Integer age) {
        return age != null && age >= MIN_AGE;
    }

    private boolean isPasswordCorrect(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isLoginCorrect(String login) {
        return login != null;
    }
}
