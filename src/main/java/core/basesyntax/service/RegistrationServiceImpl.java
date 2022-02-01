package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User person : Storage.people) { /* Check login already exits */
            if (person.getLogin().equals(user.getLogin())) {
                throw new RuntimeException("Sorry, this login already exit");
            }
        }
        if (user.getAge() < 18) { /* Check age */
            throw new RuntimeException("Sorry, age should be the same 18 y. old.");
        }
        if (user.getPassword().length() < 6) { /* Check password length */
            throw new RuntimeException(
                    "Sorry, your password is too small." + user.getPassword().length());
        }
        return storageDao.add(user);
    }
}
