package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == 0
                || user.getPassword() == null) {
            return null;
        }
        if (user.getAge() < 18
                || user.getPassword().length() < 6
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
