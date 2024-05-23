package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.NotEnoughAgeForUsingStorageException;
import core.basesyntax.exceptions.UserDoesNotExistException;
import core.basesyntax.exceptions.WrongLoginException;
import core.basesyntax.exceptions.WrongPasswordException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin().length() < 6) {
            throw new WrongLoginException("u have to pass at least 6 characters");
        }

        if (user.getPassword().length() < 6) {
            throw new WrongPasswordException("u have to pass at least 6 characters");
        }

        if (user.getAge() < 18) {
            throw new NotEnoughAgeForUsingStorageException("Expected at least 18 y.o");
        }

        User existUser = storageDao.get(user.getLogin());

        if (existUser == null) {
            throw new UserDoesNotExistException("User was not founded");
        }

        return storageDao.add(user);
    }
}
