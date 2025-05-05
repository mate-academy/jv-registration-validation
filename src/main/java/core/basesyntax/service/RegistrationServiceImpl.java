package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("invalid data");
        } else if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidDataException("problem with login");
        } else if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidDataException("problem with password");
        } else if (user.getAge() < 18) {
            throw new InvalidDataException("problem with age");
        } else if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new InvalidDataException("user already exists");
    }
}
