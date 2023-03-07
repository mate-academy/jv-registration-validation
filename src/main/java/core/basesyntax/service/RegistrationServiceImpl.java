package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.InvalidDataUser;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_USER_AGE = 18;
    private final static int MIN_USER_LOGIN = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataUser("user null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataUser("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataUser("Password can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidDataUser("age < 18");
        }
        if (user.getPassword().length() < MIN_USER_LOGIN) {
            throw new InvalidDataUser("password < 6");
        }
        int k = -1;
        for (int i = 0; i < Storage.people.size(); i++) {
            if (Storage.people.get(i).getLogin().equals(user.getLogin())) {
                k = i;
                break;
            }
        }
        if (k > -1) {
            throw new InvalidDataUser("login wrong");
        }

        return storageDao.add(user);
    }
}
