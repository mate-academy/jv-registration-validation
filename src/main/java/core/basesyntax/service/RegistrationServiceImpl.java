package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.*;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        //пользователя с таким логином в хранилище ещё нет;
        //логин пользователя содержит не менее 6 символов;
        //пароль пользователя содержит не менее 6 символов;
        //возраст пользователя не менее 18 лет.
        if (user == null) {
            throw new UserIsNullException("User is null");
        }
        if (user.getLogin() == null) {
            throw new LoginIsNullException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new PasswordIsNullException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new AgeIsNullException("Age can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User with login '" + user.getLogin() + "' already exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new LoginShorterThanSixException("Login should be longer than 6" + user.getLogin().length());
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordShorterThanSixException("Password should be longer than 6" + user.getPassword().length());
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserYoungerThanEighteenException("User should be alder than 18" + user.getAge());
        }
        return storageDao.add(user);
    }
}
