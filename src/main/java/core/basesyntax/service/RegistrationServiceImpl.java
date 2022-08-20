package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null || user.getAge() < 0) {
            throw new RegistrationServiceException("Invalid data? how? why? ehhhh....");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("A few years missing! Come next year!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("We already have your namesake!");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationServiceException("A good password should be longer than 6 characters!");
        }
        return storageDao.add(user);
    }

    public static void main(String[] args) {
        User user = new User("login", "password", 18);
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        System.out.println(registrationService.register(user));
    }
}
