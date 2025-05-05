package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.model.exception.RegistrationException;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MINIMAL_AGE_AVAILABLE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("user can't be null");
        } else if (user.getLogin() == null) {
            throw new RegistrationException("login can't be null");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User exists: " + user.getLogin());
        } else if (user.getPassword() == null) {
            throw new RegistrationException("password can't be null");
        } else if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("password can't be shorter than 6 symbols");
        } else if (user.getAge() == null) {
            throw new RegistrationException("age can't be null");
        } else if (user.getAge() < MINIMAL_AGE_AVAILABLE) {
            throw new RegistrationException("user is to young: " + user.getAge());
        }

        return storageDao.add(user);
    }
}
