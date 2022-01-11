package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Please, insert your password");
        }
        User userFromDb = storageDao.get(user.getLogin());
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Your password is too short");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("You are to young for registration");
        }

        if (userFromDb != null) {
            throw new RuntimeException("User login not unique");
        }

        boolean isPresent = Storage.people.contains(user);
        if (isPresent) {
            throw new RuntimeException();
        }
        Storage.people.add(user);
        return new User();
    }
}
