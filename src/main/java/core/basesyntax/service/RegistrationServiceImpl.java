package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.WrongDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String SPACE = " ";
    private final StorageDao storageDao = new StorageDaoImpl();
    private final int minAge;
    private final int maxAge;
    private final int minLengthOfPassword;
    private final int maxLengthOfPassword;
    private final int maxLengthOfLogin;

    public RegistrationServiceImpl() {
        this.minAge = 18;
        this.maxAge = 100;
        this.minLengthOfPassword = 6;
        this.maxLengthOfPassword = 12;
        this.maxLengthOfLogin = 6;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new WrongDataException("User cannot be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new WrongDataException("A user with this login already exists!");
        }
        if (user.getLogin() == null
                || user.getLogin().length() == 0
                || user.getLogin().length() > maxLengthOfLogin
                || user.getLogin().contains(SPACE)) {
            throw new WrongDataException("The login must contain from 1 to 6 characters!");
        }
        if (user.getAge() == null
                || user.getAge() < minAge
                || user.getAge() > maxAge) {
            throw new WrongDataException("Your age must be from 18 to 100 years old!");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < minLengthOfPassword
                || user.getPassword().length() > maxLengthOfPassword) {
            throw new WrongDataException("The password must contain from 6 to 12 characters!");
        }
        storageDao.add(user);
        return user;
    }
}
