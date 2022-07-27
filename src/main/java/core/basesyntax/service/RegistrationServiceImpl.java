package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LENGT_PATHWORD = 6;
    public static final int MIN_AGE_USER = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getAge() == null || user.getAge() < 0) {
            throw new RuntimeException("No valid data!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such login already exist!");
        }
        if (user.getAge() < MIN_AGE_USER) {
            throw new RuntimeException("User age must be not less " + MIN_AGE_USER + "!");
        }
        if (user.getPassword().length() < MIN_LENGT_PATHWORD) {
            throw new RuntimeException("Minimal length of the password must be not less"
                    + MIN_LENGT_PATHWORD + "!");
        }
        return storageDao.add(user);
    }
}
