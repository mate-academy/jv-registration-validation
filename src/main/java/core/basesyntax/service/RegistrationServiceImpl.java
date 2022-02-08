package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkValidateData(checkNull(user));
        return storageDao.add(user);
    }

    private User checkNull(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid data type, please input correct date type");
        }
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RuntimeException("There is empty line,"
                    + " please fill all information about user");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such login already exist, please create another login");
        }
        return user;
    }

    private void checkValidateData(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Incorrect age, user age must be at least 18 years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Incorrect password length, "
                    + "password length must be at least 6 characters");
        }
    }
}
