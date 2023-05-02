package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.NotValidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isValidData(user);
        if (storageDao.get(user.getLogin()) != null) {
            return user;
        }
        storageDao.add(user);
        return user;
    }

    private void isValidData(User user) {
        if (user == null) {
            throw new NotValidDataException("User can`t be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new NotValidDataException("Login have to be longer then 6 character but was: "
                    + user.getLogin().length());
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new NotValidDataException("Password have to be longer then 6 character but was: "
                    + user.getPassword().length());
        }
        if (user.getAge() < 18) {
            throw new NotValidDataException("Age have to be 18 or older, but was"
                    + user.getAge());
        }
    }
}
