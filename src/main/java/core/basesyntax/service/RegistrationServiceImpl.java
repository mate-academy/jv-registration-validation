package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getId() == null) {
            throw new NullPointerException();
        }
        if (!checkUserExists(user.getLogin())
                && checkAge(user.getAge())
                && checkPassword(user.getPassword())) {
            Storage.people.add(user);
            return user;
        }
        throw new RuntimeException("Invalid input data");
    }

    private boolean checkUserExists(String login) {
        return storageDao.get(login) != null;
    }

    private boolean checkAge(int age) {
        return age >= 18;
    }

    private boolean checkPassword(String password) {
        return password.length() >= 6;
    }
}
