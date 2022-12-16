package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getPassword() == null) {
            throw new NullPointerException("Incorrect password or user age");
        }
        if (user.getAge() < 18 || user.getPassword().length() < 6) {
            throw new InvalidDataException("Incorrect password or user age");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Incorrect password or user age");
        }
        for (User person : Storage.people) {
            if (user.getLogin().equals(person.getLogin())) {
                throw new InvalidDataException("This login is not available");
            }
        }
        return storageDao.add(user);
    }
}
