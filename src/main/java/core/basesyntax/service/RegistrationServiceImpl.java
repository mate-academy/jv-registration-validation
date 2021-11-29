package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        } else if (user.getAge() < 18) {
            throw new RuntimeException("User must be at least 18 years old");
        } else if (user.getPassword().length() < 6) {
            throw new RuntimeException("User's password is too short");
        }
        storageDao.add(user);
        return user;
    }
}
