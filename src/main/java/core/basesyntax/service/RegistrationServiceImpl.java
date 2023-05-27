package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user cais null");
        }
        if (checkForDuplicateLogins(user)) {
            throw new RegistrationException("The users login can not be duplicated");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("The login is null or has length less than 6");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("The password is null or has length less than 6");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("The age is null or less than 18");
        }
        return storageDao.add(user);
    }

    private boolean checkForDuplicateLogins(User user) {
        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }
}
