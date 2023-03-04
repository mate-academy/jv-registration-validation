package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputExcepton(
                    "Sorry, seems like an internal error has occurred: user is null");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputExcepton(
                    "Sorry, seems like an internal error has occurred: login is null");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputExcepton(
                    "Sorry, seems like an internal error has occurred: password is null");
        }
        if (user.getAge() < 18) {
            throw new InvalidInputExcepton(
                    "Minors are only allowed under parents' supervision");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidInputExcepton(
                    "Password is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputExcepton(
                    "Login already exists, try another login");
        }
        storageDao.add(user);
        return user;
    }
}
