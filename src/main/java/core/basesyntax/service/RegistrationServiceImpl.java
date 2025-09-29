package core.basesyntax.service;

import core.basesyntax.Invalid;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    static final int MIN_LOGIN_LENGTH = 6;
    static final int MIN_PASSWORD_LENGTH = 6;

    public RegistrationServiceImpl(StorageDaoImpl dao) {

    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new Invalid("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new Invalid("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new Invalid("Password cannot be null");
        }
        if (user.getAge() == null) {
            throw new Invalid("Age cannot be null");
        }
        if (Storage.people.contains(user.getLogin())) {
            throw new Invalid("This login is already used by another user");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new Invalid("Login should be at least " + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new Invalid("Password should be at least " + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < 18) {
            throw new Invalid("The allowed age is 18+");
        }
        Storage.people.add(user);
        return user;
    }

}
