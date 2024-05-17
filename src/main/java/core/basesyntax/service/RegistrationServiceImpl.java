package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("There already is user with this login!");
        } else if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Login is less than 6 characters, try longer!");
        } else if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Password is less than 6 characters, try longer!");
        } else if (user.getAge() < 18) {
            throw new InvalidDataException("User is under 18!");
        }
        return storageDao.add(user);
    }
}
