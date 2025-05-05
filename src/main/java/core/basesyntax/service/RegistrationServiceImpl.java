package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_NUMBERS_OF_CHARACTER = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDaoImpl = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null || user.getId() == null) {
            throw new RegistrationException("User cannot have a null parameter");
        }
        for (User users : Storage.people) {
            if (users.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User has same login");
            }
        }
        if (user.getLogin().length() < MIN_NUMBERS_OF_CHARACTER || user.getLogin() == null) {
            throw new RegistrationException("User has login less than " + MIN_NUMBERS_OF_CHARACTER
                    + " characters");
        }
        if (user.getPassword().length() < MIN_NUMBERS_OF_CHARACTER || user.getPassword() == null) {
            throw new RegistrationException("User has password less than "
                    + MIN_NUMBERS_OF_CHARACTER + " characters");
        }
        if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RegistrationException("User age ss less than " + MIN_AGE);
        }
        storageDaoImpl.add(user);
        return user;
    }
}
