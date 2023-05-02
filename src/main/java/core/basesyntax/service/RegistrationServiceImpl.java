package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    private boolean isUserExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean loginIsAtLeast_6_Characters(User user) {
        if (user.getLogin().length() < 6) {
            System.out.println("User " + user.getLogin() + " login is least then 6 characters");
        }
        return user.getLogin().length() >= 6;
    }

    private boolean passwordIsAtLeast_6_Characters(User user) {
        if (user.getPassword().length() < 6) {
            System.out.println("User " + user.getLogin() + " password is least then 6 characters");
        }
        return user.getPassword().length() >= 6;
    }

    private boolean ageIsAtLeast_18_YearsOld(User user) {
        if (user.getAge() < 18) {
            System.out.println("User " + user.getLogin() + " age is least then 18 characters");
        }
        return user.getAge() >= 18;
    }

    @Override
    public User register(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("User login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("User password can't be null!");
        }
        if (user.getAge() == null) {
            throw new ValidationException("User age can't be null!");
        }
        if (!loginIsAtLeast_6_Characters(user)) {
            throw new ValidationException("Login can't be less then 6 characters");
        }
        if (!passwordIsAtLeast_6_Characters(user)) {
            throw new ValidationException("Password can't be less then 6 characters");
        }
        if (!ageIsAtLeast_18_YearsOld(user)) {
            throw new ValidationException("Age should be 18 and more");
        }
        if (isUserExist(user)) {
            throw new ValidationException("User with login " + user.getLogin() + " already exist");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    @Override
    public User getUser(String login) throws ValidationException {
        if (login == null) {
            throw new ValidationException("Login can't be null!");
        }
        if (storageDao.get(login) == null) {
            throw new ValidationException("User with login " + login + " doesn't exist");
        }
        return storageDao.get(login);
    }
}
