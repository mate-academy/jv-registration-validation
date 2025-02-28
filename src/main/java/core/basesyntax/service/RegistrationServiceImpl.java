package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Age must be at least 18");
        }

        for (int i = 0; i < Storage.people.size(); i++) {
            if (user.getLogin().equals(Storage.people.get(i).getLogin())) {
                throw new RegistrationException("User already exists");
            }
        }
        return storageDao.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationServiceImpl that = (RegistrationServiceImpl) o;
        return Objects.equals(storageDao, that.storageDao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(storageDao);
    }
}
