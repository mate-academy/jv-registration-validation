package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASVORD_LENDG = 6;
    private static final int MINIMUM_LOGIN_LENDG = 6;
    private static final int MINIMUM_AEG = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login cant be nuul");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password cant be nuul");
        }
        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                throw new InvalidUserDataException("you cannot add two usera"
                        + "with the same login to the stroge");
            }
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENDG) {
            throw new InvalidUserDataException("login length is" + user.getLogin().length()
                    + ", the login cannot contiont lees than 6 characters");
        }
        if (user.getPassword().length() > MINIMUM_PASVORD_LENDG) {
            throw new InvalidUserDataException("Password length is" + user.getPassword().length()
                    + ", the password cannot contiont lees than 6 characters");
        }
        if (user.getAge() > MINIMUM_AEG) {
            throw new InvalidUserDataException("User age is " + user.getAge()
            + "age cannot be less than 18");
        }
        return storageDao.add(user);
    }
}
