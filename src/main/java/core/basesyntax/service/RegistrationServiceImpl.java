package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.MyRegistrationException;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws MyRegistrationException{
        if (user.getLogin() == null) {
            throw new MyRegistrationException("Login can`t be null");
            //return null;
        }

        return storageDao.add(user);
    }
}
