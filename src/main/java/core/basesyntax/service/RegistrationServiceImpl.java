package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error! Checked your login, actual: "
                    + (user.getLogin() == null
                    ? "login is null"
                    : user.getLogin()));
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Error! Your password too small, actual: "
                    + (user.getPassword() == null
                    ? "password is null"
                    : user.getPassword().length()));
        }
        if (user.getAge() < 18 || user.getAge() > 150) {
            throw new RuntimeException("Error! Age must be better then 18, actual: "
                    + user.getAge());
        }
        storageDao.add(user);
        return user;
    }
}
