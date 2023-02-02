package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegisterServiceImplExeption;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_SYMBOLS_AMOUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null) {
            throw new RegisterServiceImplExeption("Password can't be null !");
        }
        if (user.getLogin() == null) {
            throw new RegisterServiceImplExeption("Login can't be null !");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegisterServiceImplExeption("Age can't be less than " + MIN_AGE + " !");
        }
        if (user.getPassword().length() < MIN_SYMBOLS_AMOUNT) {
            throw new RegisterServiceImplExeption("Password must be at least "
                    + MIN_SYMBOLS_AMOUNT + " charters !");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegisterServiceImplExeption("User already exist !");
        } else {
            return storageDao.add(user);
        }
    }

}
