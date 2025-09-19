package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.*;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        //пользователя с таким логином в хранилище ещё нет;
        //логин пользователя содержит не менее 6 символов;
        //пароль пользователя содержит не менее 6 символов;
        //возраст пользователя не менее 18 лет.
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (user.getLogin() == null) {
            throw new LoginIsNullException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new PasswordIsNullException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException("User wirh this email already exixts:" + user.getLogin());
        }
        if (user.getLogin().length() < 6) {
            throw new LoginShorterThanSixException("Login should be longer than 6" + user.getLogin().length());
        }
        if (user.getPassword().length() < 6) {
            throw new PasswordShorterThanSixException("Password should be longer than 6" + user.getPassword().length());
        }
        if (user.getAge() < 18) {
            throw new UserYoungerThanEighteenException("User should be alder than 18" + user.getAge());
        }
        storageDao.add(user);
        return user;
    }
}
