package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such login is already existed");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User should be 18 and older");
        }
        if (user.getPassword().toCharArray().length < 6) {
            throw new RegistrationException("Password should be 6 and more symbols");
        }
        return storageDao.add(user);
    }
}
