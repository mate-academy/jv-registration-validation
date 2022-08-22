package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.db.Storage;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 500;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid data for register user");
        }
        if (Storage.people.size() > 0) {
            for (User userInList : Storage.people) {
                if (userInList.getLogin().equals(user.getLogin())) {
                    throw new RuntimeException("The user with login "
                            + user.getLogin() + "has already been created");
                }
            }
        }
        if (checkLogin(user)
                && user.getPassword() != null
                && checkAge(user)
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

    private boolean checkAge(User user) {
        if (user.getAge() != null
                && user.getAge() >= MIN_AGE
                && user.getAge() < MAX_AGE) {
            return true;
        }
        return false;
    }

}
