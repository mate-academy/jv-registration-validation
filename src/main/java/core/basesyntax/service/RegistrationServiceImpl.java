package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User object can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password can't be null");
        }
        StorageDaoImpl storageDao = new StorageDaoImpl();
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getAge() >= 18 && user.getPassword().length() >= 6) {
                storageDao.add(user);
                return user;
            }
        }
        return null;
    }
}
