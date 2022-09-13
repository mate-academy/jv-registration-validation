package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if(storageDao.get(user.getLogin()) == null) {
            if(user.getAge() >= 18) {
                if(user.getPassword().length() >= 6) {
                    storageDao.add(user);
                    return user;
                }
                throw new RuntimeException("Can't add this user(Password is too short)");
            }
            throw new RuntimeException("Can't add this user(You are too young)");
        }
        throw new RuntimeException("Can't add this user(User already exist)");
    }
}

