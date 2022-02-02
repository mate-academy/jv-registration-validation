package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
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
        if (Objects.isNull(user)) {
            throw new RuntimeException("User can't be null");
        }
        if (Objects.isNull(user.getLogin())) {
            throw new RuntimeException("User login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("User login can't be empty");
        }
        if (user.getLogin().length() <= MIN_LENGTH) {
            throw new RuntimeException("User login can't be shorter than 6");
        }
        if (PATTERN_WORD_IS_NUMERIC.matcher(user.getLogin()).matches()) {
            throw new RuntimeException("User login can't be only numeric");
        }
        if (Objects.isNull(user.getPassword())) {
            throw new RuntimeException("User password can't be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new RuntimeException("User password can't be empty");
        }
        if (PATTERN_WORD_IS_NUMERIC.matcher(user.getPassword()).matches()) {
            throw new RuntimeException("User password can't be only numeric");
        }
        if (user.getPassword().length() <= MIN_LENGTH) {
            throw new RuntimeException("User password can't be shorter than 6");
        }
        if (Objects.isNull(user.getAge())) {
            throw new RuntimeException("User age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be at least 18 years old");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("User can't be older than 100 years old");
        }
        for (User person : Storage.people) {
            if (Objects.nonNull(person) && person.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("User login not available");
            }
        }
        return storageDao.add(user);
    }
}
