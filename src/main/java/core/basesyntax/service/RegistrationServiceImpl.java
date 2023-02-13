package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 90;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNotValidException("User is null!");
        }
        String login = user.getLogin();
        String password = user.getPassword();
        if (login == null || login.isEmpty()) {
            throw new UserNotValidException("User's login is null or empty!");
        }
        if (password == null) {
            throw new UserNotValidException("User's password is null!");
        }
        if (password.length() < MIN_CHARACTERS) {
            throw new UserNotValidException("User password's length is less than 6 characters!");
        }
        if (storageDao.get(login) != null) {
            throw new UserNotValidException("User is already in the storage!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new UserNotValidException(
                    String.format("User must be at least %d and not bigger than %d!",
                            MIN_AGE, MAX_AGE));
        }
        return storageDao.add(user);
    }
}
