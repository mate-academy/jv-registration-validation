package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User's data is not valid");
        }
        /*if (equals(user.getLogin())) {
            throw new InvalidDataException("Login already exist");
        }*/
        if (user.getLogin().length() < MIN_LENGTH
                || user.getLogin() == null) {
            throw new InvalidDataException("such login is too short");
        }
        if (user.getPassword().length() < MIN_LENGTH
                || user.getPassword() == null) {
            throw new InvalidDataException("User password can not be null " +
                    "or shorter than 6");
        }
        if (user.getAge() < MIN_AGE
                || user.getAge() == null) {
            throw new InvalidDataException("User can not be younger than 18");
        }
        return storageDao.add(user);
    }
}
