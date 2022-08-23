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
        checkNull(user);
        checkUserAge(user.getAge());
        checkUserLogin(user.getLogin());
        userPasswordCheck(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void checkNull(User user) {
        if (user == null
                || user.getLogin().isBlank()
                || user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException(user + "have null parameters");
        }
    }

    private void userPasswordCheck(String password) {
        if (!password.matches(REGEX_PASSWORD)) {
            throw new RuntimeException("The password " + password + " is incorrect");
        }
    }

    private void checkUserLogin(String login) {
        if (storageDao.get(login) != null || login.contains(" ")) {
            throw new RuntimeException("Login " + login + " is wrong");
        }
    }

    private void checkUserAge(int age) {
        if (age < MAX_AGE) {
            throw new RuntimeException("Age " + age + " is lass than 18");
        }
    }
}
