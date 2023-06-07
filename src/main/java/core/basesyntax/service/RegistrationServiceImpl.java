package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (checkLogin(user.getLogin())
                && checkPassword(user.getPassword())
                && checkAge(user.getAge())) {
            storageDao.add(user);
        }
        return user;
    }

    public boolean checkLogin(String login) {
        if (login == null) {
            throw new CustomException(
                    "Login can't be empty, login must be at list 6 characters long"
            );
        }
        if (storageDao.get(login) != null) {
            throw new CustomException("User with such login is already registered");
        }
        if (login.length() < 6) {
            throw new CustomException("User login must be at least 6 characters long");
        }
        return true;
    }

    public boolean checkPassword(String password) {
        boolean passwordIsOk = true;
        if (password == null) {
            passwordIsOk = false;
            throw new CustomException("Password can't be empty");
        }
        if (password.length() < 6) {
            passwordIsOk = false;
            throw new CustomException("Password must be at least 6 characters long");
        }
        return passwordIsOk;
    }

    public boolean checkAge(Integer age) {
        boolean ageIsOk = true;
        if (age == null) {
            throw new CustomException("User age can't be empty or null");
        }
        if (age < 18) {
            ageIsOk = false;
            throw new CustomException("User age must be at least 18");
        }
        return ageIsOk;
    }
}
