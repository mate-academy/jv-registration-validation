package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int AGE_MIN_VALUE = 18;
    public static final int PASSWORD_MIN_LENGTH = 6;
    private static final StorageDaoImpl storageDaoImpl = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Fill user information with valid data.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Enter your login." + user.getLogin());
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Enter your age." + user.getAge());
        }
        if (user.getAge() > 0 && user.getAge() < AGE_MIN_VALUE) {
            throw new RuntimeException("You must be 18 years of age to register, but was :"
                    + user.getAge());
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Enter your password" + user.getPassword());
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password length must be at least 6 characters, but was: "
                    + user.getPassword().length());
        }
        if (user.getLogin().equals(storageDaoImpl.get(user.getLogin()))) {
            throw new RuntimeException();
        }
        return storageDaoImpl.add(user);
    }
}
