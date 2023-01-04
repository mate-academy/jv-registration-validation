package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new registerUserException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new registerUserException("Login can`t be null");
        }
        if (user.getAge() == null) {
            throw new registerUserException("Age can`t be null");
        }
        if (user.getPassword() == null) {
            throw new registerUserException("Password can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new registerUserException("User is exist");
        }
        if (user.getAge() < MIN_AGE) {
            throw new registerUserException("User age can`t be less than 18");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new registerUserException("Password need to be greater than 6");
        }
        if (user.getLogin().isEmpty()) {
            throw new registerUserException("Login can`t be empty");
        }
        if (!Character.isLetter((user.getLogin().charAt(0)))) {
            throw new registerUserException("Login need to start from Letter");
        }
        return storageDao.add(user);
    }
}
