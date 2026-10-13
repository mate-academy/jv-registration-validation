package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("The login can't be null");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("The login can't be shorter than 6 symbols");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exists");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("The password can't be null");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("The password can't be shorter than 6 symbols");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("The age can't be null");
        }
        if (user.getAge() < 0) {
            throw new InvalidDataException("The age can't be negative");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("The user must be older than 18 y. o.");
        }
        storageDao.add(user);
        return user;
    }
}
