package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        if (Storage.people.contains(storageDao.get(user.getLogin()))) {
            throw new InvalidDataException("User already exists "
                    + user.getLogin()
                    + " with such login");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login cannot be null");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Not valid login." + "Min length is " + MIN_LENGTH);
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("User password cannot be null");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Not valid password." + "Min length is " + MIN_LENGTH);
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("User age cannot be null");
        }
        if (user.getAge() < 0) {
            throw new InvalidDataException("Not valid age "
                    + user.getAge()
                    + ". Because age is negative");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("Not valid age "
                    + user.getAge()
                    + ". Min age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
