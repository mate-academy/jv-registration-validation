package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.dao.UsersDataIsNotValidException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTER_SIZE_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public boolean register(User user) {
        if (user.getLogin() == null) {
            return false;
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        } else {
            throw new UsersDataIsNotValidException("This login is already registered");
        }
        if (user.getAge() == null) {
            return false;
        }
        if (user.getAge() >= MIN_AGE) {
            storageDao.add(user);
        } else {
            throw new UsersDataIsNotValidException("The user's age is less than 18");
        }
        if (user.getPassword() == null) {
            return false;
        }
        if (user.getPassword().length() >= MIN_CHARACTER_SIZE_PASSWORD) {
            storageDao.add(user);
        } else {
            return false;
        }
        return true;
    }
}
