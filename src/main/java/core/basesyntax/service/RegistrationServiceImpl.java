package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private static final String MESSAGE_LENGTH_ERROR = "Your message length is less then 6";
    private static final String USER_CANT_BE_NULL = "User cant be null";
    private static final String CANNOT_BE_NULL_MESSAGE = "length can not be null";
    private static final String YOU_ARE_NOT_OLD_ENOUGH = "Your age length is less then 18";
    private static final String USER_ALREADY_EXIST = "This user already exist";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userCheck(user);
        return storageDao.add(user);
    }

    public void userCheck(User user) {
        if (user == null) {
            throw new RegistrationException(USER_CANT_BE_NULL);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Your login " + CANNOT_BE_NULL_MESSAGE);
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Your age " + CANNOT_BE_NULL_MESSAGE);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Your password " + CANNOT_BE_NULL_MESSAGE);
        }
        if (user.getLogin().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException(MESSAGE_LENGTH_ERROR);
        }
        if (user.getPassword().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException(MESSAGE_LENGTH_ERROR);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(YOU_ARE_NOT_OLD_ENOUGH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(USER_ALREADY_EXIST);
        }
    }
}
