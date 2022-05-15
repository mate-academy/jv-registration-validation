package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_RESTRICTIONS_ON_REGISTRATION = 18;
    private static final int MIN_NUMBER_OF_SYMBOLS_IN_THE_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == 0
                || user.getPassword() == null) {
            return null;
        }
        if (user.getAge() < AGE_RESTRICTIONS_ON_REGISTRATION
                || user.getPassword().length()
                < MIN_NUMBER_OF_SYMBOLS_IN_THE_PASSWORD
                || existLoginInStorage(user.getLogin())) {
            throw new RuntimeException("The user does not meet the "
                    + "following criteria:"
                    + System.lineSeparator()
                    + " - there is no user with such login in the "
                    + "Storage yet" + System.lineSeparator()
                    + " - the user is at least 18 years old"
                    + System.lineSeparator()
                    + " - user password is at least 6 characters"
                    + System.lineSeparator()
                    + "Check it out and try again!");
        } else {
            storageDao.add(user);
            return user;
        }
    }

    private boolean existLoginInStorage(String login) {
        for (User person : Storage.people) {
            if (person.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
