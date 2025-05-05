package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.IncorrectUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 140;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IncorrectUserDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new IncorrectUserDataException("User login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new IncorrectUserDataException("User login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new IncorrectUserDataException("Storage already has this user");
        }
        if (user.getPassword() == null) {
            throw new IncorrectUserDataException("User password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IncorrectUserDataException("User password must be has at least 6 symbols");
        }
        if (user.getAge() == null) {
            throw new IncorrectUserDataException("User age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new IncorrectUserDataException("User age must be at least 18");
        }
        if (user.getAge() > MAX_AGE) {
            throw new IncorrectUserDataException("User age must be lower than 140");
        }
        storageDao.add(user);
        return user;
    }

}
