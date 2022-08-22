package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.db.Storage;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (Storage.people.size() >= 1) {
            for (User ifLogin : Storage.people) {
                if (ifLogin.getLogin().equals(user.getLogin())) {
                    throw new RuntimeException("The user with login "
                            + user.getLogin() + "has already been created");
                }
            }
        }
        if (checkLogin(user) == true
                && user.getPassword() != null
                && user.getAge() != null
                && user.getAge() >= MIN_AGE
                && user.getPassword().length() >= MIN_LENGTH) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException("Invalid data for register user");
    }

    private boolean checkLogin(User user) {
        if (user.getLogin() != null && !user.getLogin().equals("")) {
            return true;
        }
        return false;
    }

}
