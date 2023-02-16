package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.ValidationExeption;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationExeption {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationExeption("The user with this login is already exist!");
        }

        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new ValidationExeption("Please fill in the login field!");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new ValidationExeption("Users age must be 18 or greater!");
        }

        if (user.getPassword() == null
                || user.getPassword().equals("")
                || user.getPassword().length() < 6) {
            throw new ValidationExeption("The password must contain minimum 6 characters!");
        }

        storageDao.add(user);
        return user;
    }
}
