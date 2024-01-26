package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_CHARACTEEERS = 6;
    private static final int MIN_PASSWORD_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }
        isNullElements(user);
        if (user.getLogin().length() < MIN_LOGIN_CHARACTEEERS) {
            throw new InvalidDataRegistrationExeption("The login must be at least "
                    + MIN_LOGIN_CHARACTEEERS + " characters " + user.getLogin());
        }
        if (user.getPassword().length() < MIN_PASSWORD_CHARACTERS) {
            throw new InvalidDataRegistrationExeption("The password must be at least "
                    + MIN_PASSWORD_CHARACTERS + " characters " + user.getPassword());
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataRegistrationExeption("Invalid age: " + user.getAge()
                    + "Min allowed age is" + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataRegistrationExeption("Found user with the same login "
                    + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }

    private boolean isNullElements(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataRegistrationExeption("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataRegistrationExeption("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataRegistrationExeption("Age can't be null");
        }
        return true;
    }
}
