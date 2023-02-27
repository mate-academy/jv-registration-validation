package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    static final int EIGHTEEN = 18;
    static final int PASSWORD_SIX_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public void register(User user) throws RegistrationException {

        if (user.getAge() < EIGHTEEN) {
            throw new RegistrationException("Age must be over 17 and cannot be less than zero!");
        }
        if (user.getPassword() != null && user.getPassword().length() < PASSWORD_SIX_SYMBOLS) {
            throw new RegistrationException("Password must be at least 6 symbols");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Error: password null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Error: login null!");
        }
        if (user.getLogin().equals("") || user.getPassword().equals("")) {
            throw new RegistrationException("Maybe you forgot to fill in your login or password!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user already exists!");
        }
        storageDao.add(user);
    }
}
