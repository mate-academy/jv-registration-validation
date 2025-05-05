package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 150;
    private static final int DEFAULT_ACCEPTED_AGE = 18;
    private static final int MIN_PASSWORD_CHARS = 6;
    private static final int MIN_LOGIN_LENGTH = 2;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age must be filled");  
        }
        if (user.getAge() < DEFAULT_ACCEPTED_AGE) {
            throw new RuntimeException("Accepted age should be at least " + DEFAULT_ACCEPTED_AGE);
        }
        if (user.getAge() > MAX_AGE) {
            throw new RuntimeException("Scientists consider that it is impossible"
                    + " to live more than " + MAX_AGE + " years, for the present");
        }
        if (user.getLogin().isBlank() || user.getLogin().endsWith(" ") 
                || user.getLogin().startsWith(" ") || user.getLogin().contains("  ")) {
            throw new RuntimeException(
                    "Login cant be blank, contains repeated spaces, spaces starts "
                    + "or ends your login");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RuntimeException("Login length must be " + MIN_LOGIN_LENGTH 
                    + " symbols at least");
        }
        if (user.getPassword().length() < MIN_PASSWORD_CHARS) {
            throw new RuntimeException("Password length must be " + MIN_PASSWORD_CHARS 
                    + " symbols at least");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with " + user.getLogin() + "login already exist");
        }

        return storageDao.add(user);
    }
}
