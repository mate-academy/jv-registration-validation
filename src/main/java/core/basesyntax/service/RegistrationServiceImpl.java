package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("The user that was passed in argument equals null."
                    + " Registration is not possible");
        }
        if ((user.getLogin() == null || user.getLogin().length() < 6)) {
            throw new RegistrationException("The user's login can not be null "
                    + "or contains less than 6 characters. Registration is not possible");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("The user's password can not be null "
                    + "or contains less than 6 characters. Registration is not possible");
        }
        if (user.getAge() == null || user.getAge() < 18 || user.getAge() > 120) {
            throw new RegistrationException("The user's age can not be null and must "
                    + "be between 18 and 120 years old. Registration is not possible");
        }
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).equals(user)) {
            throw new RegistrationException("The data base has contained the user " + user
                    + " yet. Registration is not possible");
        }
        return storageDao.add(user);
    }
}
