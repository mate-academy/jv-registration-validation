package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age can't be less than " + MIN_AGE);
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("The user login is missing, the login can't null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login \""
                    + user.getLogin() + "\" already exists.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Attention, password entry error!\n" +
                                       "The password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Attention, password entry error!\n" +
                    "The password must be "+ MIN_PASSWORD_LENGTH + " or longer than characters.");
        }
        return storageDao.add(user);
    }
}
