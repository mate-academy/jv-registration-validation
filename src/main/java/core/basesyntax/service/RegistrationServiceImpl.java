package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int AGE_OF_EIGHTEEN = 18;
    private static final int AGE_ZERO = 0;
    private static final int MINIMUM_LENGTH_OF_PASSWORD_OR_LOGIN = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login is null");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH_OF_PASSWORD_OR_LOGIN) {
            throw new RegistrationException("User login is too short");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password is null");
        }
        if (user.getPassword().length() < MINIMUM_LENGTH_OF_PASSWORD_OR_LOGIN) {
            throw new RegistrationException("User password is too short");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age is null");
        }

        if (user.getAge() <= AGE_ZERO) {
            throw new RegistrationException("User can't be younger than 0");
        }

        if (user.getAge() < AGE_OF_EIGHTEEN) {
            throw new RegistrationException("User is too young");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist");
        }
        storageDao.add(user);
        return user;
    }
}
