package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserValidation(user);
        return storageDao.add(user);
    }

    private void checkUserValidation(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null! Maybe you made a mistake");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().isEmpty()) {
            throw new RegistrationException("Password can't be empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Minimal allowed age is " + MINIMAL_AGE);
        }
        if (user.getPassword().length() < MINIMAL_LENGTH) {
            throw new RegistrationException("Password can't be less than "
                    + MINIMAL_LENGTH + " symbols");
        }
        if (user.getLogin().length() < MINIMAL_LENGTH) {
            throw new RegistrationException("Login can't be less than "
                    + MINIMAL_LENGTH + " symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("You can't register with this login. "
                    + "This login is already occupied");
        }
    }
}
