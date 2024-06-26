package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user == null) {
            throw new CustomException("User is null.");
        }
        if (!checkLogin(user.getLogin())) {
            throw new CustomException("The login is null or the length of login is less than 6.");
        }
        if (!checkPassword(user.getPassword())) {
            throw new CustomException("The password is null or the "
                    + "length of password is less than 6.");
        }
        if (!checkAge(user.getAge())) {
            throw new CustomException("The age is null or is is less than 18.");
        }
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new CustomException("There is an user with the same login.");
        }
        storageDao.add(user);
        return user;
    }

    private boolean checkLogin(String login) {
        return login != null && login.length() >= 6;
    }

    private boolean checkPassword(String password) {
        return password != null && password.length() >= 6;
    }

    private boolean checkAge(Integer age) {
        return age != null && age >= 18;
    }
}
