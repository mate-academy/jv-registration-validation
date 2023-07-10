package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getPassword() == null || user.getLogin() == null || user.getAge() == null) {
            throw new NullPointerException("Field can't be null");
        } else if (user.getAge() < 18) {
            throw new InvalidDataException(" Age should be greater than 16 " + user.getAge());
        } else if (user.getPassword().length() < 6) {
            throw new InvalidDataException(" Password length should be greater than 6 ");
        } else if (user.getLogin().isEmpty()) {
            throw new InvalidDataException(" Login can't be empty " + user.getLogin());
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException(" Login already created " + user.getLogin());
        }
        storageDao.add(user);
        return user;
    }
}

