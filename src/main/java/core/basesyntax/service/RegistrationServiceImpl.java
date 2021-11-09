package core.basesyntax.service;

import static core.basesyntax.db.Storage.people;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (people.contains(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("Can`t add user, login already exists");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Can`t add user, age should have more 18 years");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Can`t add user, password should have more 6 symbols");
        }
        storageDao.add(user);
        return null;
    }
}
