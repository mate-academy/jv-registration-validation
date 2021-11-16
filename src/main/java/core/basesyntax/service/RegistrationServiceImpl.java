package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("incorrect data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This user already exists");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age < " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Password length < " + MIN_PASS_LENGTH);
        }
        return storageDao.add(user);
    }
}
