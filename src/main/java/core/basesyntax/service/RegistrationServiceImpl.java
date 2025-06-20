package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegisterExtension {
        if (user == null) {
            throw new RegisterExtension("User cannot be null");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegisterExtension("Password is not valid, check the correctness of the "
                   + "data entered");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegisterExtension("Age is not valid, check the correctness of "
                   + "the data entered");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegisterExtension("Login is not valid, check the correctness of the "
                   + "data entered");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterExtension("User already exists");
        }

        return storageDao.add(user);
    }
}
