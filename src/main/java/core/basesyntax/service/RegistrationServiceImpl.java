package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 18 && user.getPassword().length() < 6) {
            throw new ExpectedException("Incorrect password or user age!");
        }
        if (user.getLogin() == null || user.getPassword() == null) {
            throw new ExpectedException("Incorrect user login!");
        }
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new ExpectedException("This login is not available!");
            }
        }
        return storageDao.add(user);

    }

}
