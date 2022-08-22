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
            exeptionTrows("nullChecker", nullChecker(user));
        }
        userAgeCheck(user.getAge());
        userLoginCheck(user.getLogin());
        userPasswordLengthCheck(user.getPassword());
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

    private boolean exeptionTrows(String method, String argument) {
        switch (method) {
            case "userPasswordLengthCheck" :
                throw new RuntimeException("The password " + argument + " is incorrect");
            case "userLoginCheck" :
                throw new RuntimeException("Login " + argument + " is wrong");
            case "userAgeCheck" :
                throw new RuntimeException("Age " + argument + " is lass than 18");
            case "nullChecker" :
                throw new RuntimeException(argument + " is null");
            default:
                return false;
        }
    }

    private boolean userPasswordLengthCheck(String password) {
        return password.matches(REGEX_PASSWORD) ? true :
                exeptionTrows("userPasswordLengthCheck", password);
    }

    private boolean userLoginCheck(String login) {
        return storageDao.get(login) == null && !login.contains(" ") ? true
                : exeptionTrows("userLoginCheck", login);
    }

    private boolean userAgeCheck(int age) {
        return age >= MAX_AGE ? true :
                exeptionTrows("userAgeCheck", String.valueOf(age));
    }
}
