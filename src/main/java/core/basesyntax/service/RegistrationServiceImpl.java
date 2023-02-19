package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("Register argument is null");
        }
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new InvalidRegistrationDataException("Invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationDataException("User already registerd");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidRegistrationDataException("Invalid age");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidRegistrationDataException("Invalid password");
        }
        storageDao.add(user);
        return user;
    }
}
