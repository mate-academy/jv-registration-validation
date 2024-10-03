package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getLogin().length() >= 6 && user.getPassword().length() >= 6
                    && user.getAge() >= 18) {
                storageDao.add(user);
                return user;
            } else {
                throw new InvalidDataException("The data isn't correct, please check it");
            }
        }
        return null;
    }
}
