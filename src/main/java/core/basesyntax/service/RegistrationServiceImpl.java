package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String SPACE = " ";
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_LENGTH_OF_PASSWORD = 6;
    private static final int MAX_LENGTH_OF_PASSWORD = 12;
    private static final int MAX_LENGTH_OF_LOGIN = 6;
    private static final int MIN_LENGTH_OF_LOGIN = 1;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null && storageDao.get(user.getLogin()) != null) {
            throw new WrongDataException("A user with login " + user.getLogin()
                    + " already exists!");
        }
        if (user == null) {
            throw new WrongDataException("User cannot be null!");
        }
        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LENGTH_OF_LOGIN
                || user.getLogin().length() > MAX_LENGTH_OF_LOGIN
                || user.getLogin().contains(SPACE)) {
            throw new WrongDataException("The login must contain from "
                    + MIN_LENGTH_OF_LOGIN + " to " + MAX_LENGTH_OF_LOGIN + " characters!");
        }
        if (user.getAge() == null
                || user.getAge() < MIN_AGE
                || user.getAge() > MAX_AGE) {
            throw new WrongDataException("Your age must be from "
                    + MIN_AGE + " to " + MAX_AGE + " years old!");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_LENGTH_OF_PASSWORD
                || user.getPassword().length() > MAX_LENGTH_OF_PASSWORD) {
            throw new WrongDataException("The password must contain from "
                    + MIN_LENGTH_OF_PASSWORD + " to " + MAX_LENGTH_OF_PASSWORD + " characters!");
        }
        storageDao.add(user);
        return user;
    }
}
