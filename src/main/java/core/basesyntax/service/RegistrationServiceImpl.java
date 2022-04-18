package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT_AGE = 18;
    private static final int PASSWORD_VALID_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        nullCheck(user);
        passwordLengthCheck(user);
        ageCheck(user);
        sameLoginCheck(user);
        return storageDao.add(user);
    }

    private User passwordLengthCheck(User user) {
        if (user.getPassword().length() < PASSWORD_VALID_LENGTH) {
            throw new RuntimeException("Password length is too short, please provide password "
                    + "which is longer than " + PASSWORD_VALID_LENGTH);
        }
        return user;
    }

    private User nullCheck(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("Please provide valid registration data");
        }
        return user;
    }

    private User ageCheck(User user) {
        if (user.getAge() == null || user.getAge() < 0 || user.getAge() < ADULT_AGE) {
            throw new RuntimeException("Invalid age for registration");
        }
        return user;
    }

    private User sameLoginCheck(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists");
        }
        return user;
    }

}
