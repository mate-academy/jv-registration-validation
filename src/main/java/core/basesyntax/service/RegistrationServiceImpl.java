package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
      public User register(User user) {
        if ((user.getLogin() == null) || (user.getAge() == null) || (user.getPassword() == null)) {
            throw new RuntimeException("Please, enter the correct values");
        }
        if (storageDao.get(user.getLogin()) != null || user.getAge() < 18
                || user.getPassword().length() < 6) {
            throw new RuntimeException("The entered parameters are not valid");
        }
        return storageDao.add(user);
    }
}
