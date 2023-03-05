package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkAgeNull(user);
        checkAgeLess18(user);
        checkAgeBigger100(user);
        checkAgeZero(user);
        checkLoginNull(user);
        checkLoginIncorrect(user);
        checkPasswordNull(user);
        checkPasswordLess8(user);
        checkIdNull(user);
        checkIdNegative(user);
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

    private void checkAgeZero(User user) {
        if (user.getAge() < 0) {
            throw new IncorrectDataExeption("Age can not be less than 0");
        }
    }

    private void checkAgeNull(User user) {
        if (user.getAge() == null) {
            throw new IncorrectDataExeption("Age can not be null");
        }
    }

    private void checkAgeLess18(User user) {
        if (user.getAge() < 18) {
            throw new IncorrectDataExeption("Age can not be less than 18");
        }
    }

    private void checkAgeBigger100(User user) {
        if (user.getAge() > 100) {
            throw new IncorrectDataExeption("Age can not be bigger then 100");
        }
    }

    private void checkPasswordNull(User user) {
        if (user.getPassword() == null) {
            throw new IncorrectDataExeption("Password can not be null");
        }
    }

    private void checkPasswordLess8(User user) {
        if (user.getPassword().length() < 8) {
            throw new IncorrectDataExeption("Password can not be less then 8");
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

    private void checkIdNull(User user) {
        if (user.getId() == null) {
            throw new IncorrectDataExeption("Id can not be null");
        }
    }

    private void checkIdNegative(User user) {
        if (user.getId() < 0) {
            throw new IncorrectDataExeption("Id can not be less then 0");
        }
    }
}
