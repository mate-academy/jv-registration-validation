package core.basesyntax.service;

import core.basesyntax.Exception.NoValidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        char []loginLength = user.getLogin().toCharArray();
        char []passwordLength = user.getPassword().toCharArray();
        for (User user1 : Storage.people) {
            if (user.getLogin().equals(user1.getLogin())) {
                throw new NoValidDataException("User with such login already exist");
            }
        }
        if (loginLength.length < 6) {
            throw new NoValidDataException("Login should have at least 6 characters");
        }
        if (passwordLength.length < 6) {
            throw new NoValidDataException("Password should have at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new NoValidDataException("User need to be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
