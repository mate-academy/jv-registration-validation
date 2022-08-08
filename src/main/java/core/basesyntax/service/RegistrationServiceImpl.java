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
        if (user.getAge() == null && user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age error. " +
                                       "User age can't be null or less than \"18\".");
        } else if (user.getLogin() == null) {
            throw new RuntimeException("The user login is missing, the login can't null.");
        }else if (!checkUserPresence(user.getLogin())) {
            throw new RuntimeException("User with this login is already registered.");
        } else if (user.getPassword() == null
                   && user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Attention, password entry error!\n" +
                    "The password must be longer than 6 characters.");
        } else {
            storageDao.add(user);
        }
        return user;
    }

    private boolean checkUserPresence (String login) {
        return storageDao.get(login) != null;
    }
}
