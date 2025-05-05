package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 130;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfNull(user);
        checkUserAge(user);
        checkPasswordLength(user);
        checkLoginExistance(user);
        storageDao.add(user);
        return user;
    }

    public void checkIfNull(User user) {
        if (user == null) {
            throw new InvalidDataException("Can not add null to user storage");
        }
    }

    public void checkUserAge(User user) {
        if ((user.getAge() == null) || (user.getAge() < MIN_AGE) || (user.getAge() > MAX_AGE)) {
            throw new InvalidDataException("Invalid age of user");
        }
    }

    public void checkPasswordLength(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Password length has to be greater than 6");
        }
    }

    public void checkLoginExistance(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidDataException("Invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User exists");
        }
    }

}
