package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException();
        }

        if (!isUserPassCriterias(user)) {
            if (user.getLogin().length() < 6) {
                throw new InvalidDataException("Login too short. Please enter login with"
                        + " 6+ symbols.");
            } else if (user.getPassword().length() < 6) {
                throw new InvalidDataException("Password too short. Please enter password "
                        + "with 6+ symbols.");
            } else if (user.getAge() < 18) {
                throw new InvalidDataException("Your age is lower than acceptable. If it's mistake,"
                        + " enter the new one, higher or equal than 18.");
            } else if (isLoginExist(user.getLogin())) {
                throw new InvalidDataException("This login already exist. Try the new one.");
            }
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
