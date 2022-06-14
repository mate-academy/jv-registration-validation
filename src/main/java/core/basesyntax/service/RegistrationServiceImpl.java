package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("Invalid data");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("Invalid data");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidDataException("Invalid data");
        }
        return user;
    }
}
