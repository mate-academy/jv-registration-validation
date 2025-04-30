package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationError {
        if (user.getLogin() == null) {
            throw new RegistrationError(Errors.Login_NotNull);
        } else if (user.getLogin().length() < 6) {
            throw new RegistrationError(Errors.Short_UserLogin);
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationError(Errors.Login_inUse);
        }

        if (user.getPassword() == null) {
            throw new RegistrationError(Errors.Password_NotNull);
        } else if (user.getPassword().length() < 6) {
            throw new RegistrationError(Errors.Short_UserPassword);
        }

        if (user.getAge() == null) {
            throw new RegistrationError(Errors.Age_NotNull);
        } else if (user.getAge() < 18) {
            throw new RegistrationError(Errors.User_AgeYounger);
        }
        return storageDao.add(user);
    }
}
