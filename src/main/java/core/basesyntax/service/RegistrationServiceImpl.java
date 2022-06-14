package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("No one to register");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("You are too young");
        }
        if (user.getAge() > 122) {
            throw new RuntimeException("Don't pass fake age");
        }
        if (user.getLogin() != null && storageDao.get(user.getLogin()) == null) {
            if (user.getPassword() != null && user.getPassword().length() >= 6) {
                return storageDao.add(user);
            } else {
                throw new RuntimeException("password is too bad");
            }
        } else {
            throw new RuntimeException("Incorrect login");
        }
    }
}
