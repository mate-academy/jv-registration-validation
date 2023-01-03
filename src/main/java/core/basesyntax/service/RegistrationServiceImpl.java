package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 118;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() == 0
                || user.getLogin().indexOf(' ') != -1) {
            throw new UserNotFoundException("This login is incorrect");
        }
        if (user.getPassword() == null || user.getPassword().length() == 0
                || user.getPassword().indexOf(' ') != -1) {
            throw new UserNotFoundException("This password is incorrect");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserNotFoundException("This user is already registered");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserNotFoundException("User age is less than allowed");
        }
        if (user.getAge() > MAX_AGE) {
            throw new UserNotFoundException("User age is more than allowed");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new UserNotFoundException("User password is less than expected");
        }
        return storageDao.add(user);
    }
}
