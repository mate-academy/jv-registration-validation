package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User must not be null!");
        }

        StringBuilder errorMessage = new StringBuilder();

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            errorMessage.append("Login must be at least 6 characters. ");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            errorMessage.append("Password must be at least 6 characters. ");
        }

        if (user.getAge() < 18) {
            errorMessage.append("User must be at least 18 years old.");
        }

        if (errorMessage.length() > 0) {
            throw new InvalidUserDataException(errorMessage.toString());
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("Such user has already registered!");
        }

        storageDao.add(user);

        return user;
    }
}
