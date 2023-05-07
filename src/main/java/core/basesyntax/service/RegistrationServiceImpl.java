package core.basesyntax.service;

import core.basesyntax.MyRegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int REQUIRED_RANGE_OF_PASSWORD = 6;
    private static final int MAX_AGE = 105;
    private final StorageDao storageDao = new StorageDaoImpl();

    private User userLogin;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new MyRegistrationException("User cannot be null!!!");
        }
        if (user.getLogin() == null) {
            throw new MyRegistrationException("Login cannot be null!!");
        }
        if (user.getAge() == null) {
            throw new MyRegistrationException("Age cannot be null!!!");
        }
        if (user.getPassword() == null || user.getPassword().equals(" ")) {
            throw new MyRegistrationException("Password cannot be null or empty line!!!");
        }
        if (storageDao.get(user.getLogin()) != null && !user.getLogin().equals(" ")) {
            throw new MyRegistrationException("there is " + user.getLogin()
                    + " user already exist!");
        }
        if (user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new MyRegistrationException("The range of age must be from "
                    + MIN_AGE + " years old through " + MAX_AGE + " years old!!!");
        }
        if (user.getPassword().length() < REQUIRED_RANGE_OF_PASSWORD) {
            throw new MyRegistrationException("Your password must contain at least "
                    + REQUIRED_RANGE_OF_PASSWORD + " characters!");
        }

        return storageDao.add(user);
    }
}
