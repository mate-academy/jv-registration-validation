package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private final int minAge = 18;
    private final int minLength = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        somethingEqualsNull(user);
        ageIsCorrect(user);
        correctLogin(user);
        shortPassword(user);
        duplicate(user);
        passwordWithoutSymbols(user);
        passwordWithoutUpperCaseLetters(user);
        storageDao.add(user);
        return user;
    }

    private void duplicate(User user) throws InvalidDataException {
        if (Objects.equals(storageDao.get(user.getLogin()), user)) {
            exception("User already exists");
        }
    }

    private void correctLogin(User user) throws InvalidDataException {
        if (user.getLogin().length() < minLength) {
            exception("Your login is too short");
        }
        if (!user.getLogin().contains("@")) {
            exception("There is no \"@\" in login");
        }
        if (user.getLogin().contains(" ")) {
            exception("Login shouldn't contain spaces");
        }
        if (!Character.isLetter(user.getLogin().charAt(0))) {
            exception("Login shouldn't starts with symbol");
        }
    }

    private void somethingEqualsNull(User user) throws InvalidDataException {
        if (user.getLogin() == null) {
            exception("Login can't be null");
        }
        if (user.getAge() == null) {
            exception("Age can't be null");
        }
        if (user.getPassword() == null) {
            exception("Password can't be null");
        }
    }

    private void ageIsCorrect(User user) throws InvalidDataException {
        if (user.getAge() < minAge) {
            exception("User too young for our service");
        } else if (user.getAge() < 0) {
            exception("User cannot be younger than 0");
        }
    }

    private void shortPassword(User user) throws InvalidDataException {
        if (user.getPassword().length() < minLength) {
            exception("Your password is too short");
        }
    }

    private void passwordWithoutSymbols(User user) throws InvalidDataException {
        for (int i = 0; i < user.getPassword().length(); i++) {
            if (!Character.isLetter(user.getPassword().charAt(i))) {
                return;
            } else if (i == user.getPassword().length() - 1) {
                exception("Password should contain some symbols");
            }
        }
    }

    private void passwordWithoutUpperCaseLetters(User user) throws InvalidDataException {
        for (int i = 0; i < user.getPassword().length(); i++) {
            if (Character.isUpperCase(user.getPassword().charAt(i))) {
                break;
            } else if (i == user.getPassword().length() - 1) {
                exception("Password should contain upper case letter");
            }
        }
    }

    private void exception(String message) throws InvalidDataException {
        throw new InvalidDataException(message);
    }
}
