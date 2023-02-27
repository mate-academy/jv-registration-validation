package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new WrongDataException("User cannot be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new WrongDataException("A user with this login already exists!");
        }
        if (user.getLogin() == null || user.getLogin().length() == 0) {
            throw new WrongDataException("The login must contain at least 1 characters!");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new WrongDataException("You must be at least 18 years old!");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new WrongDataException("The password must contain at least 6 characters!");
        }
        storageDao.add(user);
        return user;
    }
}
