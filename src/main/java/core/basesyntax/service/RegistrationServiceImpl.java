package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }
        if (user.getLogin().length() < 6) {
            throw new NotValidRegistrationExeption("Login can't be less than 6 char");
        }
        if (user.getPassword().length() < 6) {
            throw new NotValidRegistrationExeption("Password can't be less than 6 char");
        }
        if (user.getAge() < 18) {
            throw new NotValidRegistrationExeption("Age suold be more than 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidRegistrationExeption("Email is already used");
        }
        storageDao.add(user);
        return user;
    }
}
