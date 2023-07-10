package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getPassword() == null
                || user.getLogin() == null
                || user.getAge() == null) {
            throw new NullPointerException("Field can't be null");
        } else if (user.getAge() < 18) {
            throw new InvalidUserDataException(
                    "Age should be equal or greater than 18 " + user.getAge());
        } else if (user.getPassword().length() < 6 || user.getLogin().length() < 6) {
            throw new InvalidUserDataException(
                    "Password/Login length should be greater than 6 ");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "Login already exist " + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }
}
