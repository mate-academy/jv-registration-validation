package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl(); // add, get

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login is null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("User login is too short");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password is null");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("User password is too short");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age is null");
        }

        if (user.getAge() <= 0) {
            throw new RegistrationException("User can't be younger than 0");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("User is too young");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist");
        }
        storageDao.add(user);
        return user;
    }
}
