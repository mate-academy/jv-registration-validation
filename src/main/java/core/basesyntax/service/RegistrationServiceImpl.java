package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws ValidationException {
        if (user.getLogin() != null && user.getLogin().length() < 6) {
            throw new ValidationException("Login shorter than 6 symbols." + user.getLogin());
        }
        if (user.getPassword() != null && user.getPassword().length() < 6) {
            throw new ValidationException("Password shorter than 6 symbols." + user.getPassword());
        }
        if (user.getAge() != null && user.getAge() < 18) {
            throw new ValidationException("Age is incorrect" + user.getAge());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("We already have such user." + user.getLogin());
        }
        return user;
    }
}
