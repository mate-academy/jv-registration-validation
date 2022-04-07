package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() != null && user.getLogin().length() > 0) {
            for (User person : Storage.people) {
                if (person.getLogin().equals(user.getLogin())) {
                    throw new RuntimeException("User login is already exist");
                }
            }
            if (user.getAge() >= 18 && user.getPassword().length() >= 6) {
                storageDao.add(user);
                return user;
            }
        }
        throw new RuntimeException("User's data is wrong");
    }
}
