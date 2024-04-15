package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private static final int MAXIMUM_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getLogin().length() < DEFAULT_LENGTH) {
            throw new RegistrationException("Login must be longer then " + DEFAULT_LENGTH
                    + " characters");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("You must be adult");
        }
        if (user.getAge() > MAXIMUM_AGE) {
            throw new RegistrationException("You must be younger");
        }
        if (user.getPassword().length() < DEFAULT_LENGTH) {
            throw new RegistrationException("You password must be longer then "
                    + DEFAULT_LENGTH + " characters");
        }
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new RegistrationException("User with this login exists");
        }
        return storageDao.add(user);
    }
}
