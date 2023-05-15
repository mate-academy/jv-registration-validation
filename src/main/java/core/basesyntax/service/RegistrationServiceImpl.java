package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MinAge = 18;
    private static final int minLengthPassword = 8;
    private static final int minLengthLogin = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < minLengthPassword) {
            throw new RegistrationException("User's password cannot be null or less than "
                    + minLengthPassword);
        }
        if (user.getLogin() == null || user.getLogin().length() < minLengthLogin) {
            throw new RegistrationException("User's login cannot be null or less than "
                    + minLengthLogin);
        }
        if (user.getAge() == null || user.getAge() < MinAge) {
            throw new RegistrationException("User's age cannot be null or less than "
                    + MinAge);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already taken");
        }

        return storageDao.add(user);
    }
}
