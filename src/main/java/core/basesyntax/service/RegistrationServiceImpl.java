package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("The information are not valid, can't register user.");
        }
        if (people.contains(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User with that login already exists.");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("You need to be more than 18 years of age.");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Your password need to be at least 6 symbols long.");
        }
        return storageDao.add(user);
    }
}
