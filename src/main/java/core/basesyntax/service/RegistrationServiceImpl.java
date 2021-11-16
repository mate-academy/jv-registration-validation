package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final StorageDao storageDaoImpl = new StorageDaoImpl();
    
    @Override
    public User register(User user) {
        if (user == null 
                || user.getAge() == null
                || user.getAge() < MIN_AGE 
                || user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH
                || user.getLogin() == null
                || storageDaoImpl.get(user.getLogin()) != null) {
            throw new RuntimeException("Invalid data");
        }
        return storageDaoImpl.add(user);
    }
}
