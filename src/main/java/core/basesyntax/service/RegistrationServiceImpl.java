package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputException("User can not be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputException("User login can not be null");
        }
        if (user.getAge() == null) {
            throw new InvalidInputException("User age can not be null");
        }
        if (user.getId() == null) {
            throw new InvalidInputException("User ID can not be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputException("User password can not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException(
                    "User password length can not be less than "
                            + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputException("User age can not be less than " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputException("User is already exists!");
        }
        storageDao.add(user);
        return user;
    }

    public User getUser(String login) {
        User user = storageDao.get(login);
        if (user == null) {
            return null;
        }
        return user;
    }
}
