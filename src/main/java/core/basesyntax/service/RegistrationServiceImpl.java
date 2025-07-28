package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isBlank()
                || user.getLogin().length() < 6) {
            throw new InvalidDataException("Your login is blank or has less than 6 characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login is already exists!");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()
                || user.getPassword().length() < 6) {
            throw new InvalidDataException("Your password is blank or has less than 6 characters!");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("Your age is incorrect or you are too young!");
        }
        storageDao.add(user);
        return user;
    }
}
