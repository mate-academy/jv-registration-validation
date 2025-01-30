package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can`t be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User login is already exist");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidDataException("Password length is less than 6");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Login length is less than 6");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("User age is less than 18");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
