package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can not be null!");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Your age can not be null!");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("Your age is under 18!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Your password can not be null!");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Your password is less than 6 characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with such a login already exists!");
        }

        storageDao.add(user);
        return user;
    }
}
