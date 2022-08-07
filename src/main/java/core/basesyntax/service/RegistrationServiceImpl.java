package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("User without LOGIN can not be registered!");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User without AGE can not be registered!");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User without PASSWORD can not be registered!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists!");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User is to be at least 18 years!");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User`s password is to be at least 6 characters!");
        }
        storageDao.add(user);
        return user;
    }
}
