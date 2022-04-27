package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User's login can not be null");
        }
        if (user.getLogin().length() < 1) {
            throw new RuntimeException("User's login can not be blank");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User's password can not be null");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User's password must be at least 6 symbols");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User's age can not be null");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User's age must be at least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
