package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGTH = 6;
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 100;
    public static final Pattern PATTERN_WORD_IS_NUMERIC = Pattern.compile("-?\\d+(\\.\\d+)?");
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (Objects.isNull(user)) {
            throw new RuntimeException("User can't be null");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
    }

    private void validateLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User login not available");
        }
        if (Objects.isNull(user.getLogin()) || user.getLogin().isEmpty()) {
            throw new RuntimeException("User login can't be null or empty");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RuntimeException("User login can't be shorter than 6");
        }
        if (PATTERN_WORD_IS_NUMERIC.matcher(user.getLogin()).matches()) {
            throw new RuntimeException("User login can't be only numeric");
        }
    }

    private void validatePassword(User user) {
        if (Objects.isNull(user.getPassword()) || user.getPassword().isEmpty()) {
            throw new RuntimeException("User password can't be null or empty");
        }
        if (PATTERN_WORD_IS_NUMERIC.matcher(user.getPassword()).matches()) {
            throw new RuntimeException("User password can't be only numeric");
        }
        if (user.getPassword().length() <= MIN_LENGTH) {
            throw new RuntimeException("User password can't be shorter than 6");
        }
    }

    private void validateAge(User user) {
        if (Objects.isNull(user.getAge())) {
            throw new RuntimeException("User age can't be null");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new RuntimeException("User must be between 18 and 100 years old");
        }
    }
}
