package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("The user cannot have a null name");
        }
        if (user.getLogin().equals("")) {
            throw new RuntimeException("Name cannot be empty");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user is already in the database");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("The user is under 18 years of age");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("The password cannot by a null");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("The user has a password less than 6 characters");
        }
        return storageDao.add(user);
    }
}
