package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error! User is already exist");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Error! Your login is empty");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Error! Your password too small, actual: "
                    + (user.getPassword() == null
                    ? "password is null"
                    : user.getPassword().length()));
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Error! Checked your age, is empty");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Error! Age must be less then 18, actual: "
                    + user.getAge());
        }
        storageDao.add(user);
        return user;
    }
}
