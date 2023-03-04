package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 130;
    private static final int MIN_CHARS_COUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User is null");
        }
        if (Storage.people.contains(user)) {
            throw new InvalidInputDataException("Such user is already present in the storage");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputDataException("Login can't be null");
        }
        if (user.getLogin().trim().equals("")) {
            throw new InvalidInputDataException("Login can't be []]");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputDataException("Password can't be null");
        }
        if (user.getPassword().trim().equals("")) {
            throw new InvalidInputDataException("Password can't be []");
        }
        if (user.getPassword().length() < MIN_CHARS_COUNT) {
            throw new InvalidInputDataException("User's password has less characters than 6");
        }
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputDataException("User's age is less than 18");
        }
        if (user.getAge() > MAX_AGE) {
            throw new InvalidInputDataException("User is too old. "
                    + "Nobody in the world can't have such age");
        }
        if (user.getId() != null) {
            throw new InvalidInputDataException("Id should be null, but id is ["
                    + user.getId() + "]");
        }
        return storageDao.add(user);
    }
}
