package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already existing");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Sorry, you are too young =)");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Your password is weak. Choose a better one");
        }
        storageDao.add(user);
        return user;
    }
}
