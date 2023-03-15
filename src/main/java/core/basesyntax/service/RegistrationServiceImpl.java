package core.basesyntax.service;

import core.basesyntax.custom.exceptions.AgeNotValidException;
import core.basesyntax.custom.exceptions.LoginNotValidException;
import core.basesyntax.custom.exceptions.PasswordNotValidException;
import core.basesyntax.custom.exceptions.UserNotValidException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 130;
    private static final int PASSWORD_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNotValidException("User not valid");
        }
        if (user.getLogin() == null) {
            throw new LoginNotValidException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new LoginNotValidException("Login already exist");
        }
        if (user.getPassword() == null) {
            throw new PasswordNotValidException("Password can't be null");
        }
        if (user.getPassword().length() != PASSWORD_SIZE) {
            throw new PasswordNotValidException("Password not valid");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new AgeNotValidException("Not valid age");
        }
        return storageDao.add(user);
    }
}
