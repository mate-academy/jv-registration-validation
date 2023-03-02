package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.IncorrectUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IncorrectUserDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new IncorrectUserDataException("User login can't be null");
        }
        if (user.getPassword() == null) {
            throw new IncorrectUserDataException("User password can't be null");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new IncorrectUserDataException("User password must be has at least 6 symbols");
        }
        if (user.getAge() < MIN_AGE) {
            throw new IncorrectUserDataException("User age must be at least 18");
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

}
