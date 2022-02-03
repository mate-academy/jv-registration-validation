package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHAR_FOR_PASS = 6;
    private static final int MIN_AGE_FOR_REG = 18;

    @Override
    public User register(User user) {
        if (userNullCheck(user)
                && loginUserCheck(user)
                && userAgeCheck(user)
                && userPasswordCheck(user)) {
            return user;
        }
        return null;
    }

    public boolean loginUserCheck(User user) {
        for (User u: Storage.people) {
            if (!u.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    public boolean userNullCheck(User user) {
        return user == null;
    }

    public boolean userAgeCheck(User user) {
        return user.getAge() >= MIN_AGE_FOR_REG;
    }

    public boolean userPasswordCheck(User user) {
        return user.getPassword().length() >= MIN_CHAR_FOR_PASS;
    }
}

