package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int minAge = 18;
    private final int minLength = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        if (user.getAge() < minAge) {
            throw new InvalidUserDataException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + minAge);
        }
        if (user.getPassword().length() < minLength) {
            throw new InvalidUserDataException("Password cant be less than 6");
        }
        if (user.getLogin().length() < minLength) {
            throw new InvalidUserDataException("Login cant be less than 6");
        }
        return storageDao.add(user);
    }
}
