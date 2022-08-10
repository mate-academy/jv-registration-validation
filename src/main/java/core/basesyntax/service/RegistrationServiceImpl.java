package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SIZE_OF_PASSWORD = 6;
    private static final int MAX_AGE = 123;
    private final StorageDao storageDao = new StorageDaoImpl();

    public static int getMinAge() {
        return MIN_AGE;
    }

    public static int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null!");
        }
        if (user.getLogin() != null && !user.getLogin().isEmpty()) {
            User findUser = storageDao.get(user.getLogin());
            if (findUser != null) {
                throw new RuntimeException("The user with such login already exists. "
                        + "Please, enter another login.");
            }
            if (user.getAge() < MIN_AGE) {
                throw new RuntimeException("The user should be at least "
                        + MIN_AGE + " years old.");
            }

            if (user.getAge() > MAX_AGE) {
                throw new RuntimeException("The user should be less "
                        + MAX_AGE + " years old.");
            }

            if (user.getPassword().length() < MIN_SIZE_OF_PASSWORD) {
                throw new RuntimeException("Password should be at least "
                        + MIN_SIZE_OF_PASSWORD + " characters.");
            }
            storageDao.add(user);
            return user;
        } else {
            throw new RuntimeException("Login can't be null or empty!");
        }
    }
}
