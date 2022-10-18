package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int EIGHTEEN_YEARS_OLD = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User can't be Null");
        }
        if (Storage.people.contains(user)) {
            throw new RuntimeException("User already exist");
        }
        if (user.getAge() < EIGHTEEN_YEARS_OLD) {
            throw new RuntimeException("User to young");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User's password to short, it mast consist of 6 character");
        }
        storageDao.add(user);

        return user;
    }
}
