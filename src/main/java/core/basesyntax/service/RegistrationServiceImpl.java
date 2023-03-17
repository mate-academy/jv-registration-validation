package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService  {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        User newUser = storageDao.get(user.getLogin());
        if (newUser != null) {
            throw new InvalidDataException("The user already is in storage");
        }

        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Your password is short. Length of password should be not shorter then 6 symbols");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Your very young. Your age should be not younger then 18 years old");
        }

        return storageDao.add(user);
    }
}
