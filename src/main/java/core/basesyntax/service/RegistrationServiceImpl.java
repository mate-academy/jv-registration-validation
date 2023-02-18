package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_AGE = 18;
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegisterNotValidException("User can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterNotValidException("User already exist!");
        }
        if (user.getAge() == null) {
            throw new RegisterNotValidException("Age can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RegisterNotValidException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new RegisterNotValidException("Password can't be null!");
        }
        if (user.getLogin().isBlank()) {
            throw new RegisterNotValidException("Login can't be empty!");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegisterNotValidException("For registration, user has to be at least minimum "
                    + MINIMUM_AGE + " years old");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegisterNotValidException("Password has to be minimum at least minimum "
                    + MINIMUM_PASSWORD_LENGTH + " characters");
        }
        return storageDao.add(user);
    }
}
