package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new CustomException("User not found");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_CHARACTERS) {
            throw new CustomException("Password less than 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new CustomException("Not valid age: " + user.getAge());
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new CustomException("This user already exist");
        }
        if (user.getLogin() == null) {
            throw new CustomException("Can't find login");
        }
        return storageDao.add(user);
    }
}
