package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (
                (user == null)
                || (user.getPassword() == null)
                || (user.getLogin() == null)
                || (user.getAge() == null)
        ) {
            throw new RuntimeException("You can use not null value");
        }

        if ((user.getAge() < USER_MIN_AGE) || (user.getPassword().length() < PASSWORD_MIN_LENGTH)) {
            throw new RuntimeException("User age should be more then 17 or password should");
        }

        for (User person : Storage.people) {
            if ((person.getLogin().equals(user.getLogin()))) {
                throw new RuntimeException("Use another login");
            }
        }

        for (int i = 0; i < user.getLogin().length(); i++) {
            if (user.getLogin().charAt(i) == ' ') {
                throw new RuntimeException("Login can not have white space");
            }
        }

        for (int i = 0; i < user.getPassword().length(); i++) {
            if (user.getPassword().charAt(i) == ' ') {
                throw new RuntimeException("Password can not have white space");
            }
        }

        storageDao.add(user);
        return user;
    }
}
