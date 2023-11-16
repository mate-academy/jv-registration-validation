package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!isUserPassCriterias(user)) {
            throw new InvalidDataException("Incorrect data, try again!");
        }

        storageDao.add(user);
        return user;
    }

    private boolean isUserPassCriterias(User user) {
        return !isLoginExist(user.getLogin())
                && user.getLogin().length() >= 6
                && user.getPassword().length() >= 6
                && user.getAge() >= 18;
    }

    private boolean isLoginExist(String login) {
        for (User user : Storage.people) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
