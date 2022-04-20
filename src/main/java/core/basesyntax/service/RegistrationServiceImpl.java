package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            throw new RuntimeException("Please, enter a valid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user is already exist");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Please, enter a valid age");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("You should be at least " + MINIMAL_AGE
                   + " years old to register");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()
                || user.getPassword().isEmpty()) {
            throw new RuntimeException("Please, enter a valid password");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password should have at least "
                    + MINIMAL_PASSWORD_LENGTH + " characters."
                    + " Please, enter a valid password");
        }
        storageDao.add(user);
        return user;
    }
}
