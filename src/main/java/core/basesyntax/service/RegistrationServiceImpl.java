package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserDataInvalidException("The user provided is null");
        }

        if (checkDataValidity(user)) {
            return storageDao.add(user);
        }
        throw new UserDataInvalidException("The user data is not valid");
    }

    private boolean checkDataValidity(User user) {
        return user.getLogin().length() >= 6
                && user.getPassword().length() >= 6
                && user.getAge() >= 18
                && storageDao.get(user.getLogin()) == null;
    }
}
