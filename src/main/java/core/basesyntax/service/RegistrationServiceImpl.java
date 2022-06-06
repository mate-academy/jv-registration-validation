package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (passwordIsValid(user.getPassword()) && ageIsValid(user.getAge())
                && storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        }
        return user;
    }

    @Override
    public boolean passwordIsValid(String password) {
        if (password == null) {
            throw new RuntimeException();
        }
        if (password.length() >= 6) {
            return true;
        }
        return false;
    }

    @Override
    public boolean ageIsValid(Integer age) {
        if (age == null) {
            throw new RuntimeException();
        }
        if (age >= 18) {
            return true;
        }
        return false;
    }
}
