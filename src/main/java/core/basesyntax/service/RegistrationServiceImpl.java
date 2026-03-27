package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationDataInvalidException;
import core.basesyntax.model.User;


public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationDataInvalidException("User does not exist");
        }

        if (user.getLogin() == null) {
            throw new RegistrationDataInvalidException("Login does not exist");
        }

        if (user.getPassword() == null) {
            throw new RegistrationDataInvalidException("Password does not exist");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationDataInvalidException("Login must be at least 6 characters long");
        }

        if (user.getPassword().length() < 6) {
            throw new RegistrationDataInvalidException("Password must be at least 6 characters long");
        }

        if (user.getAge() == null) {
            throw new RegistrationDataInvalidException("Age does not exist");
        }

        if (user.getAge() < 18) {
            throw new RegistrationDataInvalidException("Age must be at least 18");
        }

        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }

        throw new RegistrationDataInvalidException("Login already exists");

    }
}
