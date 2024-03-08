package core.basesyntax.service;

import core.basesyntax.LoginValidateException;
import core.basesyntax.PasswordValidate;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    private boolean loginValidation(String login) {
        if (login.length() <= 4 || login == null) {
            throw new LoginValidateException("your login must be more than 4 characters");
        } else if (!Character.isLetter(login.charAt(0))) {
            throw new LoginValidateException("your password should start from the letter");
        }
        return true;
    }

    private boolean passwordValidation(String password) {
        if (password == null || password.length() < 6) {
            throw new PasswordValidate("password must be longer than six characters");
        }
        return true;
    }

    @Override
    public User register(User user) {
        storageDao.get(user.getLogin());
        return null;
    }
}
