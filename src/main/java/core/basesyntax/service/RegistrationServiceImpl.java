package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationFailedException {
        if (user.getAge() == null
                || user.getAge() < 18) {
            throw new RegistrationFailedException("Your age must be over 18!");
        } else if ( user.getPassword() == null
                || user.getPassword().length() <= 6) {
            throw new RegistrationFailedException("Your password must be more than 6 characters!");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailedException("Your login already exist!");
        }
        return user;
    }
}
