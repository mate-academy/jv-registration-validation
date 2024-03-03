package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int minLength = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkPassword(User user) {
        shortPassword(user);
        passwordWithoutSymbols(user);
        passwordWithoutUpperCaseLetters(user);
    }

    private void checkUser(User user) {
        somethingEqualsNull(user);
        checkDuplicate(user);
    }

    private void checkDuplicate(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            return;
        }
        if (storageDao.get(user.getLogin()).equals(user)) {
            throw new InvalidDataException("User already exists");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin().length() < minLength) {
            throw new InvalidDataException("Your login is too short");
        }
        if (!user.getLogin().contains("@")) {
            throw new InvalidDataException("There is no \"@\" in login");
        }
        if (user.getLogin().contains(" ")) {
            throw new InvalidDataException("Login shouldn't contain spaces");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            throw new InvalidDataException("Login shouldn't starts with symbol");
        }
    }

    private void somethingEqualsNull(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
    }

    private void checkAge(User user) {
        final int minAge = 18;
        if (user.getAge() < minAge) {
            throw new InvalidDataException("User too young for our service");
        } else if (user.getAge() < 0) {
            throw new InvalidDataException("User cannot be younger than 0");
        }
    }

    private void shortPassword(User user) {
        if (user.getPassword().length() < minLength) {
            throw new InvalidDataException("Your password is too short");
        }
    }

    private void passwordWithoutSymbols(User user) {
        for (int i = 0; i < user.getPassword().length(); i++) {
            if (!Character.isLetter(user.getPassword().charAt(i))) {
                return;
            } else if (i == user.getPassword().length() - 1) {
                throw new InvalidDataException("Password should contain some symbols");
            }
        }
    }

    private void passwordWithoutUpperCaseLetters(User user) {
        for (int i = 0; i < user.getPassword().length(); i++) {
            if (Character.isUpperCase(user.getPassword().charAt(i))) {
                break;
            } else if (i == user.getPassword().length() - 1) {
                throw new InvalidDataException("Password should contain upper case letter");
            }
        }
    }
}
