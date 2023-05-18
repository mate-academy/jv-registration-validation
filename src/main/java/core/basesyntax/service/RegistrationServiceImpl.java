package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final Integer MIN_AGE = 18;
    private static final Integer MAX_AGE = 110;
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyParametersNotNull(user);
        verifyLogin(user);
        verifyAge(user);
        verifyPassword(user);
        verifyIfUserExist(user);
        return storageDao.add(user);
    }

    private void verifyParametersNotNull(User user) {
        if (user == null) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User is NULL");
        }
        if (user.getLogin() == null) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User login is NULL");
        }
        if (user.getAge() == null) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User age is NULL");
        }
    }

    private void verifyLogin(User user) {
        if (user.getLogin().equals("")) {
            throw new UserDataException(UserDataException.class.getName()
                    + " Login is empty");
        }
    }

    private void verifyAge(User user) {
        if (user.getAge() > MAX_AGE) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User age is not valid");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User age is less than 18");
        }
    }

    private void verifyPassword(User user) {
        verifyPasswordNotNull(user);
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User password is less than 6 symbols");
        }
    }

    private void verifyPasswordNotNull(User user) {
        if (user.getPassword() == null) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User password is NULL ");
        }
    }

    private void verifyIfUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserDataException(UserDataException.class.getName()
                    + " User already exist");
        }
    }
}
