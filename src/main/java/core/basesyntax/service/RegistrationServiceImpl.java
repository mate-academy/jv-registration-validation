package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNullException("User is null");
        }
        if (user.getLogin().length() < 6
                || user.getPassword().length() < 6
                || user.getAge() < 18) {
            throw new UserWrongFieldsException("Wrong field");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRepeatingException("User with this login exists");
        }
        return storageDao.add(user);
    }
}
