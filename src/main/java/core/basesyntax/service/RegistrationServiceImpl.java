package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidUserException {
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null
                || user.getLogin().length() < 6 || user.getPassword().length() < 6
                || user.getAge() < 18 || storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("Wrong user data");
        }
        storageDao.add(user);
        return user;
    }
}
