package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            throw new RuntimeException("Login field is empty, try again.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("That login is taken. Try another.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age field is empty, try again.");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("You must be at least " + MINIMUM_AGE
                    + " years old.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("Password field is empty, try again.");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be at least "
                    + MINIMUM_PASSWORD_LENGTH + " characters, try again.");
        }
        return storageDao.add(user);

    }
}
