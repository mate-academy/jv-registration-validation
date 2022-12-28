package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static int MIN_AGE = 18;
    private static int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User equals null");
        }
        if (loginIsValid(user.getLogin()) && ageIsValid(user.getAge())
                && passwordIsValid(user.getPassword())) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private boolean loginIsValid(String login) {
        if (!login.contains("@")) {
            throw new RuntimeException("Login should contain @");
        }
        if (!Character.isLetter(login.charAt(0))) {
            throw new RuntimeException("No numerals at the start of the email");
        }
        for (int i = 0; i < login.length(); i++) {
            if (Character.isUpperCase(login.charAt(i))) {
                throw new RuntimeException("No uppercase letters in email");
            }
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("There is already such login in the system");
        }
        return true;
    }

    private boolean passwordIsValid(String pass) {
        if (pass.length() < MIN_LENGTH) {
            throw new RuntimeException("The password is too short");
        }
        return true;
    }

    private boolean ageIsValid(Integer age) {
        if (age < MIN_AGE) {
            throw new RuntimeException("Invalid age");
        }
        return true;
    }
}
