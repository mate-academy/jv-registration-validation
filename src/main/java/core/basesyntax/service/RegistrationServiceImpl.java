package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_ALLOWABLE_AGE = 18;
    private static final int MINIMUM_NUMBER_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null && !Storage.people.contains(user)
                && user.getLogin() != null
                && user.getAge() >= MINIMUM_ALLOWABLE_AGE
                && user.getPassword().length() >= MINIMUM_NUMBER_CHARACTERS) {
            storageDao.add(user);
        } else {
            throw new RuntimeException("The added date is incorrect");
        }
        return user;
    }
}
