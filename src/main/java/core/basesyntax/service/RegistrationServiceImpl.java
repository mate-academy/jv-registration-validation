package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegisterException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AMOUNT_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin().length() < MIN_AMOUNT_CHARACTERS) {
            throw new RegisterException("User's login length = " + user.getLogin().length()
                    + " but min length = " + MIN_AMOUNT_CHARACTERS);
        } else if (user.getPassword().length() < MIN_AMOUNT_CHARACTERS) {
            throw new RegisterException("User's password length = " + user.getPassword().length()
                    + " but min length = " + MIN_AMOUNT_CHARACTERS);
        } else if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RegisterException("User's age " + user.getPassword().length()
                    + " but allowed" + MIN_AGE);
        }
        for (User users:Storage.people) {
            if (users.equals(user)) {
                throw new RegisterException("User already registered!");
            }
        }
        return storageDao.add(user);
    }
}
