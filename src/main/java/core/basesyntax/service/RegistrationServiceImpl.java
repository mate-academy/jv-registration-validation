package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int MIN_AGE = 18;
    private final int MIN_COUNT_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null){
            throw new RegistrationException("Login is exist, change your login");
        }
        if (user.getLogin().length() < MIN_COUNT_CHARACTERS){
            throw new RegistrationException("Login is less than 6 characters");
        }
        if (user.getPassword().length() < MIN_COUNT_CHARACTERS){
            throw new RegistrationException("Password is less than 6 characters");
        }
        if (user.getAge() < MIN_AGE){
            throw new RegistrationException("Age is less than 18 year");
        }
        return storageDao.add(user);
    }
}
