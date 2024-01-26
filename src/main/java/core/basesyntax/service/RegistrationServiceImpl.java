package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int minAge = 18;
    private final int minLoginCharacteeers = 6;
    private final int minPasswordCharacters = 6;

    @Override
    public User register(User user) {
        isNullElements(user);
        if (user.getLogin().length() < minLoginCharacteeers) {
            throw new InvalidDataRegistrationExeption("The login must be at least 6 characters "
                    + user.getLogin());
        }
        if (user.getPassword().length() < minPasswordCharacters) {
            throw new InvalidDataRegistrationExeption("The password must be at least 6 characters "
                    + user.getPassword());
        }
        if (user.getAge() < minAge) {
            throw new InvalidDataRegistrationExeption("Invalid age: " + user.getAge()
                    + "Min allowed age is" + minAge);
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
