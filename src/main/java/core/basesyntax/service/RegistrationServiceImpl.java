package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 122;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("The user can't be null.");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("The age can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("The password can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("The login can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getAge() > MAX_AGE) {
            throw new InvalidDataException("Not valid age: " + user.getAge()
                    + ". Max allowed age is " + MAX_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Not valid password: " + user.getPassword()
                    + ". The password should have at least " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("The login can't be empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("The login " + user.getLogin() + " is already taken");
        }
        storageDao.add(user);
        return user;
    }
}
