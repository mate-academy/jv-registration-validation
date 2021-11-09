package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null && user.getLogin() != null
                && user.getPassword() != null
                && user.getPassword().matches("^\\w*\\d+\\w*$")
                && user.getPassword().matches("^\\w*[A-Z]+\\w*$")
                && user.getPassword().length() >= 6
                && user.getLogin().matches("^\\w{6}\\w*$")
                && user.getAge() != null
                && user.getAge() > 18
                && user.getAge() < 110) {
            return storageDao.add(user);
        }
        return null;
    }
}
