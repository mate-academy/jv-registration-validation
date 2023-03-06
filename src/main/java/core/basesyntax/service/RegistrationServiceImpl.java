package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static final int MIN_ID_VALUE = 1;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is empty");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getId() == null) {
            throw new RegistrationException("ID can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getId() < MIN_ID_VALUE) {
            throw new RegistrationException("ID can't be less than 1");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age");
        }

        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password should has more 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }
        return storageDao.add(user);
    }
}

