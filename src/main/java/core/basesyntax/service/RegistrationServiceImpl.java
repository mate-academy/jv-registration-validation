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

        if (user.getLogin() == null) {
            throw new InvalidDataException("Error, login can't be null.");
        }

        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Error, login too short.");
        }

        for (User u : Storage.people) {
            if (u.getLogin().equals(user.getLogin())) {
                throw new InvalidDataException("Error, user already exists.");
            }
        }

        if (user.getAge() == null) {
            throw new InvalidDataException("Error, age can't be null.");
        }

        if (user.getAge() < 18 && user.getAge() >= 0) {
            throw new InvalidDataException("Error, user below 18 years old.");
        }

        if (user.getAge() > 122 || user.getAge() < 0) {
            throw new InvalidDataException("Error, not real age.");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("Error, password can't be null.");
        }

        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Error, password too short.");
        }

        return storageDao.add(user);
    }
}
