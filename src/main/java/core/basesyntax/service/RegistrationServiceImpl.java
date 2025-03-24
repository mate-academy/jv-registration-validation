package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Error, login too short.");
        }

        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                throw new InvalidDataException("Error, user already exists.");
            }
        }

        if (user.getAge() < 18) {
            throw new InvalidDataException("Error, user below 18 years old.");
        }

        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Error, password too short.");
        }

        return storageDao.add(user);
    }
}
