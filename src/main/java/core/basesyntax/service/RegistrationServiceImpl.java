package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_THRESHOLD_YEARS = 18;
    private static final int PASSWORD_LENGTH = 6;
    private StorageDao storageData = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("You have to fill all fields");
        }
        if (storageData.get(user.getLogin()) != null) {
            throw new RuntimeException("Login you enter already exist, try another one");
        }
        if (user.getAge() < MIN_THRESHOLD_YEARS) {
            throw new RuntimeException("You are too young for this service");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RuntimeException("Your password is unsafe, try longer one");
        }
        return storageData.add(user);
    }
}
