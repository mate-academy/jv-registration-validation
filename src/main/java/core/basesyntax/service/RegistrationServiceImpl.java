package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_YEARS = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getLogin().contains(" ")
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new NullPointerException("Incorrect input");
        }
        if (user.getAge() <= MIN_YEARS) {
            throw new RuntimeException("You are under 18");
        }
        if (user.getPassword().length() <= MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user is already exist");
        }
        return storageDao.add(user);
    }
}
