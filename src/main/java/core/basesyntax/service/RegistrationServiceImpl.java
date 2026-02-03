package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Null login is not allowed");
        }

        if (user.getPassword() == null) {
            throw new InvalidDataException("Null password is not allowed");
        }

        if (user.getPassword().isEmpty()) {
            throw new InvalidDataException("Empty password is not allowed");

        }

        if (user.getLogin().isEmpty()) {
            throw new InvalidDataException("Null login is not allowed");
        }

        if (!Storage.people.contains(storageDao.get(user.getLogin()))) {
            if (user.getLogin().length() >= 6) {

                if (user.getPassword().length() >= 6) {

                    if (user.getAge() >= 18) {
                        storageDao.add(user);
                        return user;
                    } else {
                        throw new InvalidDataException("Age should be over 18");
                    }

                } else {
                    throw new InvalidDataException("User password is too short! "
                            + "Min. 6 characters!");

                }

            } else {
                throw new InvalidDataException("User login is too short! Min. 6 characters!");
            }

        } else {
            throw new InvalidDataException("User exists in the storage!");
        }

    }
}
