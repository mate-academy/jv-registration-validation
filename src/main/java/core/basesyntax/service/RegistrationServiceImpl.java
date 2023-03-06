package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkAgeNull(user);
        checkAgeLess18(user);
        checkAgeBigger120(user);
        checkLoginNull(user);
        checkLoginIncorrect(user);
        checkPasswordNull(user);
        checkPasswordLess6(user);
        checkStorageContainsUserAlready(user);
        storageDao.add(user);
        return user;
    }

    private void checkStorageContainsUserAlready(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new IncorrectDataExeption("User already existed in storage");
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new IncorrectDataExeption("User can not be null");
        }
    }

    private void checkAgeNull(User user) {
        if (user.getAge() == null) {
            throw new IncorrectDataExeption("Age can not be null");
        }
    }

    private void checkAgeLess18(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new IncorrectDataExeption("Age can not be less than 18");
        }
    }

    private void checkAgeBigger120(User user) {
        if (user.getAge() > MAX_AGE) {
            throw new IncorrectDataExeption("Age can not be bigger then 120");
        }
    }

    private void checkPasswordNull(User user) {
        if (user.getPassword() == null) {
            throw new IncorrectDataExeption("Password can not be null");
        }
    }

    private void checkPasswordLess6(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IncorrectDataExeption("Password can not be less then 6");
        }
    }

    private void checkLoginNull(User user) {
        if (user.getLogin() == null) {
            throw new IncorrectDataExeption("Login can not be null");
        }
    }

    private void checkLoginIncorrect(User user) {
        if (!user.getLogin().contains("@gmail.com")) {
            throw new IncorrectDataExeption("Login must contains @gmail.com");
        }
    }
}
