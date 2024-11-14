package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user.getLogin() == null) {
            throw new InvalidUserDataException("User login can not be null!");
        }

        if (user.getPassword() == null) {
            throw new InvalidUserDataException("User password can not be null!");
        }

        if (user.getAge() == null) {
            throw new InvalidUserDataException("User age can not be null!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("This username already exist!");
        }

        if (user.getLogin().length() < 6) {
            throw new InvalidUserDataException("Username must have at least 6 characters!");
        }

        if (user.getPassword().length() < 6) {
            throw new InvalidUserDataException("Password must have at least 6 characters!");
        }

        if (user.getAge() < 18) {
            throw new InvalidUserDataException("User must have at least 18 yo!");
        }

        if (user.getAge() < 0) {
            throw new InvalidUserDataException("User age must be over 0!");
        }

        if (user.getLogin().contains(" ")) {
            throw new InvalidUserDataException("User login can not have white spaces!");
        }

        if (user.getPassword().contains(" ")) {
            throw new InvalidUserDataException("User password can not have white spaces!");
        }

        return storageDao.add(user);
    }
}
