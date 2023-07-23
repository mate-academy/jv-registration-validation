package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AlreadyRegistered;
import core.basesyntax.exceptions.ValidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws AlreadyRegistered, ValidDataException {
        // Null login check
        if (user.getLogin() == null) {
            throw new ValidDataException("You did`t fill login field!");
        }
        // Null password check
        if (user.getPassword() == null) {
            throw new ValidDataException("You did`t fill password field!");
        }
        // Null age check
        if (user.getAge() == null) {
            throw new ValidDataException("You did`t fill age field!");
        }
        // Same login check
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getLogin().equals(user.getLogin())) {
            throw new AlreadyRegistered("Same login already registered");
        }
        // Same id check
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getId() != null
                && storageDao.get(user.getLogin()).getId().equals(user.getId())) {
            throw new AlreadyRegistered("Same id already registered");
        }
        // Additional validation for password and login
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new ValidDataException("Password must be at least 6 characters long.");
        }
        if (user.getAge() < 18) {
            throw new ValidDataException("Your age is not acceptable. "
                    + "Come here again after " + (18 - user.getAge())
                    + " year/s \n We will wait for you ;)");
        }
        return storageDao.add(user);
    }
}
