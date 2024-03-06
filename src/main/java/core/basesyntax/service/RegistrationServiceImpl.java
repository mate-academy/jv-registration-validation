package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        verify(user);
        storageDao.add(user);
        return user;
    }

    public void checkUsersInfo(User user) throws CustomException {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
    }

    public void checkAge(User user) throws CustomException {
        if (user.getAge() == null) {
            throw new CustomException("Age can't be null.");
        }
        if (user.getAge() < 18) {
            throw new CustomException("Age must be at least 18 y.o.");
        }

    }

    private void checkLogin(User user) throws CustomException {
        if (user.getLogin() == null) {
            throw new CustomException("Login can't be null.");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new CustomException("Short login length. "
                    + "It must be at least 6 characters length!");
        }
    }

    private void checkPassword(User user) throws CustomException {
        if (user.getPassword() == null) {
            throw new CustomException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new CustomException("Short password length. "
                    + "It must be at least 6 characters length!");
        }
    }

    private void verify(User user) throws CustomException {
        if (user != null) {
            checkUsersInfo(user);
        }
    }
}
