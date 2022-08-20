package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getPassword() == null) {
            throw new RegistrationServiceException("Invalid data? how? why? ehhhh....");
        }
        return storageDao.add(user);
    }

    public static void main(String[] args) {
        User user = new User("login", "password", null);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        registrationService.register(user);
    }
}
