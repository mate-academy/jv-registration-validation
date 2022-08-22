package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user);
        return user;
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("User login can not be Null!");
        } else if (user.getLogin().isEmpty()) {
            throw new RuntimeException("User login can not be empty!");
        } else if (user.getLogin().contains(" ")) {
            throw new RuntimeException("User login can not contain white spaces!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such user has already exist!!!");
        }
    }
}
