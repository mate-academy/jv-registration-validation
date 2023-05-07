package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        for (User eachUser : Storage.people) {
            if (eachUser.getLogin().equals(user.getLogin())) {
                throw new ValidationException("This user is already present in the storage.");
            }
        }
        if (user.getLogin().length() < 6) {
            throw new ValidationException("User`s login must have more than 6 symbols");
        }
        if (user.getPassword().length() < 6) {
            throw new ValidationException("User`s password must have more than 6 symbols");
        }
        if (user.getAge() < 18) {
            throw new ValidationException("User`s age must be more than 18 years");
        }
        return storageDao.add(user);
    }
}
