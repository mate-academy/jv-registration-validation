package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_CHARACTERS = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        try {
            if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null
                    || user.getId() == null) {
                throw new InvalidDataException("Ooops, your input data is not valid");
            }
        } catch (NullPointerException e) {
            throw new InvalidDataException("Oops, your input data is not valid");
        }
        if (user.getPassword().length() >= MINIMUM_CHARACTERS
                && user.getLogin().length() >= MINIMUM_CHARACTERS
                && user.getAge() >= MINIMUM_AGE) {
            storageDao.add(user);
            return user;
        } else {
            throw new InvalidDataException("Oops, your input data is not valid");
        }
    }
}
