package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        int age = user.getAge() == null ? 0 : user.getAge();
        String login = user.getLogin() == null ? null : user.getLogin();
        String password = user.getPassword() == null ? null : user.getPassword();
        for (User curUser : Storage.people) {
            if (curUser.getLogin().equals(login)) {
                throw new RegistrationException("User with this login is already in the storage");
            }
        }
        if ((login == null ? 0 : login.length()) >= 6
                && (password == null ? 0 : password.length()) >= 6 && age >= 18) {
            storageDao.add(user);
            return user;
        } else {
            throw new RegistrationException("Wrong user data");
        }
    }
}
