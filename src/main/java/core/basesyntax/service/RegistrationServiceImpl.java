package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isCorrectUser(user)) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private boolean isCorrectUser(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null!");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new NullPointerException("User's field cannot be null!");
        }
        if (user.getLogin().length() < 6) {
            throw new ValidationException("User's login should be at least 6 characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("There is user with such login in the Storage!");
        }
        if (user.getPassword().length() < 6) {
            throw new ValidationException("User's password should be at least 6 characters!");
        }
        if (!(user.getAge() >= 18)) {
            throw new ValidationException("User's age should be at least 18 years old!");
        }
        return true;
    }
}
