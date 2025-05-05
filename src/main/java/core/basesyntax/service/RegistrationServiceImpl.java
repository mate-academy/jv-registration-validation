package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Incoming user is null!");
        }
        if (Objects.equals(user.getLogin(), null)) {
            throw new RegistrationException("You can't use null value as login!");
        }
        if (Objects.equals(user.getPassword(), null)) {
            throw new RegistrationException("You can't use null value as password!");
        }
        if (Objects.equals(user.getAge(), null)) {
            throw new RegistrationException("You can't use null value as age!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You must be at least "
                    + MIN_AGE + " years old!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Your password must contain at least "
                    + MIN_PASSWORD_LENGTH + " characters!");
        }
        for (User existingUser : Storage.people) {
            if (user.getLogin().equals(existingUser.getLogin())) {
                throw new RegistrationException("User with such login already exist!");
            }
        }
        return storageDao.add(user);
    }
}
