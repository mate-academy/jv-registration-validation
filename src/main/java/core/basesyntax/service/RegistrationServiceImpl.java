package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.LoginExistException;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 130;
    private static final int MIN_AGE = 18;
    private static final int MIN_QUANTITY = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getAge() == null || user.getPassword() == null) {
            throw new RegistrationException("You need add all information for registration");
        }
        for (User userInStorage : Storage.people) {
            if (userInStorage.getLogin().equals(user.getLogin())) {
                throw new LoginExistException("This login is already exist");
            }
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Your can't register. "
                    + "This form only for user under 18 age old.");
        }
        if (user.getAge() > MAX_AGE) {
            throw new RegistrationException("Your can't register. "
                    + "For begin, you should send us your passport photo.)");
        }
        if (user.getPassword().length() < MIN_QUANTITY) {
            throw new RegistrationException("Password should be at least 6 signs");
        }
        return storageDao.add(user);
    }
}
