package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_AND_PASSWORD_LENGTH = 6;
    private final StorageDaoImpl storageDao;

    public RegistrationServiceImpl(StorageDaoImpl storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidUserDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LOGIN_AND_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Not valid login length: "
                    + user.getLogin().length() + ". " + "Min allowed login length is "
                    + MIN_LOGIN_AND_PASSWORD_LENGTH);
        }
        if (user.getPassword().length() < MIN_LOGIN_AND_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Not valid password length: "
                    + user.getPassword().length() + ". " + "Min allowed password length is "
                    + MIN_LOGIN_AND_PASSWORD_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("There is user with such login in the Storage yet");
        }
        return storageDao.add(user);
    }
}
