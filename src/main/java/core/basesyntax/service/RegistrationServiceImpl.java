package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String AGE_MESSAGE = "User's age must be at least 18!";
    private static final String LOGIN_LENGTH_MESSAGE = "User's login length"
            + " must be at least 6 symbols!";
    private static final String PASSWORD_LENGTH_MESSAGE = "User's password length "
            + "must be at least 6 symbols!";
    private static final String LOGIN_EXISTS_MESSAGE = "User with such login already exists!";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        try {
            validate(user);
            storageDao.add(user);
            return user;
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    public void validate(User user) throws InvalidDataException {
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException(AGE_MESSAGE);
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException(LOGIN_LENGTH_MESSAGE);
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException(PASSWORD_LENGTH_MESSAGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException(LOGIN_EXISTS_MESSAGE);
        }
    }
}
