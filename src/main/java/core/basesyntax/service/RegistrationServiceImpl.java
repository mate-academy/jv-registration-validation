package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int REQ_PASS_LENGTH = 6;
    private static final int REQ_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be empty");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be empty");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidDataException("Minimum age is 18 years old. User too young: "
                    + user.getAge());
        }
        if (user.getLogin().length() < REQ_LOGIN_LENGTH) {
            throw new InvalidDataException("Login too short. Minimum length: "
                    + REQ_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < REQ_LOGIN_LENGTH) {
            throw new InvalidDataException("Password too short. Minimum length: "
                    + REQ_PASS_LENGTH);
        }
        if (loginAlreadyExist(user.getLogin())) {
            throw new InvalidDataException("User: " + user.getLogin() + " already exist in DB");
        }
    }

    private boolean loginAlreadyExist(String login) {
        return storageDao.get(login) != null;
    }
}
