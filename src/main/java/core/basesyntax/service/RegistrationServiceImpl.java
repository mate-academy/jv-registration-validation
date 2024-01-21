package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.FailedToAddUser;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUMBERS_OF_CHARACTER = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDaoImpl = new StorageDaoImpl();

    @Override
    public User register(User user) throws FailedToAddUser {
        if (user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null || user.getId() == null) {
            throw new FailedToAddUser("User cannot have a null parameter");
        }
        for (User users : Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new FailedToAddUser("User has same login");
            }
        }
        if (user.getLogin().length() < MIN_NUMBERS_OF_CHARACTER || user.getLogin() == null) {
            throw new FailedToAddUser("User has login less than 6 characters");
        }
        if (user.getPassword().length() < MIN_NUMBERS_OF_CHARACTER || user.getPassword() == null) {
            throw new FailedToAddUser("User has password less than 6 characters");
        }
        if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new FailedToAddUser("User age ss less than 18");
        }
        storageDaoImpl.add(user);
        return user;
    }
}


