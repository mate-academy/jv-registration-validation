package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final CheckUserService checkUserService = new CheckUserServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserService.checkNullRegister(user);
        checkUserService.checkUserAge(user);
        checkUserService.checkUserPassword(user);
        checkUserService.checkSameUserLogin(user);
        storageDao.add(user);
        return user;
    }
}
