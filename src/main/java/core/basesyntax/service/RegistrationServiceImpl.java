package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_CHECK = 18;
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User information is empty");
        }

        if (user.getLogin() == null || user.getLogin().length() < LOGIN_LENGTH) {
            throw new RegistrationException("User`s login is less then 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RegistrationException("User`s password is less then 6 characters");
        }

        if (user.getAge() == null || user.getAge() < AGE_CHECK) {
            throw new RegistrationException("User is too young - min age is 18!");
        }

        if (user.getAge() >= 100) {
            throw new RegistrationException("User`s age is not real");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with that login already exists");
        }

        if (user.getPassword().contains(" ")) {
            throw new RegistrationException("Password should not contain WhiteSpaces");
        }

        if (user.getLogin().contains(" ")) {
            throw new RegistrationException("Login should not contain WhiteSpaces");
        }

        storageDao.add(user);

        return user;
    }
}
