package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) == null) {
            throw new RegistrationException("Login is exist");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Your login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Your password can't be null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Your login should be bigger than 6");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Your password should be bigger than 6");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Your age is incorrect");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Your age should be 18 or older");
        }
        return storageDao.add(user);
    }
}
