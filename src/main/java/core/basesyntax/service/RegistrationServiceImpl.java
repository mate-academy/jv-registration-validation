package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;
    private static final int AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User`" + user.getLogin() + "` already exist");
        }
        if (user.getLogin().length() < LOGIN_LENGTH || user.getLogin() == null) {
            throw new RegistrationException("Login must be 6 characters");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH || user.getPassword() == null) {
            throw new RegistrationException("Password must be 6 characters");
        }
        if (user.getAge() < AGE || user.getAge() == null) {
            throw new RegistrationException("User must be 18 years old");
        }
        return storageDao.add(user);
    }
}
