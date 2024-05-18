package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.IncorrectInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LEN_FOR_CREDS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getLogin() == null
                || user.getLogin().isEmpty() || user.getId() == null
                || user.getId() < 0 || user.getPassword() == null) {
            throw new IncorrectInputDataException("Some of the field is empty");
        }
        if (user.getLogin().length() < MIN_LEN_FOR_CREDS
                || user.getPassword().length() < MIN_LEN_FOR_CREDS) {
            throw new IncorrectInputDataException("Credentials size must be greater than 6");
        }
        if (user.getAge() < 18) {
            throw new IncorrectInputDataException(" age must be at least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new IncorrectInputDataException("User with this login already exists");
        }
        storageDao.add(user);
        return user;
    }
}
