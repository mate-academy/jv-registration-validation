package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private StorageDao storageData = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new NullPointerException("You have to fill all fields or check correct entry");
        }
        if (user.getAge() < MIN_PASS_AGE) {
            throw new RuntimeException("You must be 18 years old");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Your password must be consist of six characters");
        }
        return storageData.add(user);
    }
}
