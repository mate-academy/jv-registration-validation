package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORDS_LENGTH = 6;
    private static final int MIN_USERS_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Pls create your account. "
                    + "Enter your Login, Password and Age");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Pls enter your login");
        }
        if (user.getAge() < MIN_USERS_AGE || user.getAge() == null) {
            throw new RuntimeException("We so sorry, but your age should be than "
                    + MIN_USERS_AGE + " y.o");
        }
        if (user.getPassword().length() < MIN_PASSWORDS_LENGTH || user.getPassword() == null) {
            throw new RuntimeException("Password should be more than "
                    + MIN_PASSWORDS_LENGTH + " characters");
        }
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new RuntimeException("User with such login already exist. "
                        + "Pls choose other login");
            }
        }
        storageDao.add(user);
        return user;
    }
}
