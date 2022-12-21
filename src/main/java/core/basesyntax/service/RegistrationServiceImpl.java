package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_AGE = 18;
    static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao people = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age. Age should be more than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is too short! "
                    + "Password length should be minimum " + MIN_PASSWORD_LENGTH);
        }
        if (people.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        return people.add(user);
    }
}
