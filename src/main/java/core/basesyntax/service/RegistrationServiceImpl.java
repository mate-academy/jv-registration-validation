package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.InvalidInputException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    static final int[] VALID_AGE_NUMBERS_BETWEEN = {0, 100};
    static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getAge() <= VALID_AGE_NUMBERS_BETWEEN[0] || user.getAge() > VALID_AGE_NUMBERS_BETWEEN[1]) {
            throw new InvalidInputException("Invalid age input");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputException("You must be older or equal 18");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidInputException("Empty login");
        }
        for (User userFromStorage : Storage.people) {
            if (userFromStorage.getLogin().equals(user.getLogin())) {
                throw new InvalidInputException("User with such login already exist");
            }
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidInputException("Password must contain at least six characters");
        }
        storageDao.add(user);
        return user;
    }
}
