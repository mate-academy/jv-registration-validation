package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 18;
    private static final String REGEX_PASSWORD =
            "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!nullChecker(user).equals("Ok")) {
            throw new RuntimeException(user + "have null parameters");
        }
        userAgeCheck(user.getAge());
        userLoginCheck(user.getLogin());
        userPasswordCheck(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private String nullChecker(User user) {
        return user.getPassword() == null ? "Password"
                : user.getLogin() == null ? "Login"
                : user.getLogin().isBlank() ? "Login"
                : user.getAge() == null ? "Age"
                : "Ok";

    }

    private void userPasswordCheck(String password) {
        if (password.matches(REGEX_PASSWORD)) {
            return;
        }
        throw new RuntimeException("The password " + password + " is incorrect");
    }

    private void userLoginCheck(String login) {
        if (storageDao.get(login) == null && !login.contains(" ")) {
            return;
        }
        throw new RuntimeException("Login " + login + " is wrong");
    }

    private void userAgeCheck(int age) {
        if (age >= MAX_AGE) {
            return;
        }
        throw new RuntimeException("Age " + age + " is lass than 18");
    }
}
