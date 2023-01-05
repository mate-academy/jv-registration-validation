package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_QUANTITY_OF_CHARACTER = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User is null");
        }

        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new RegistrationException("User with such login "
                        + user.getLogin() + " contains in this storage");
            }
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age " + user.getAge()
                    + " is less then need");
        }

        if (user.getPassword().length() < MIN_QUANTITY_OF_CHARACTER) {
            throw new RegistrationException("The password must contains min "
                    + MIN_QUANTITY_OF_CHARACTER + " characters");
        }
        return storageDao.add(user);
    }
}
