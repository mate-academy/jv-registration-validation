package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final StorageDao STORAGEDAO = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getPassword() == null || user.getLogin() == null) {
            throw new NullPointerException("Some field is null");
        }
        if (STORAGEDAO.get(user.getLogin()) != null) {
            throw new RuntimeException("This user is in the storage");
        }
        if (user.getAge() < ADULT) {
            throw new RuntimeException("Your age is less than 18");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("The length your password is less than 6 symbols");
        }
        STORAGEDAO.add(user);
        return user;
    }
}
