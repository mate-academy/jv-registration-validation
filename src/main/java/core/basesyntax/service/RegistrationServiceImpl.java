package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.exeptions.RegisterServiceException;
import core.basesyntax.model.User;
import core.basesyntax.validators.UserForRegistrationValidator;

public class RegistrationServiceImpl implements RegistrationService {
    private final static String INVALID_USER_MESSAGE = "User is invalid: ";
    private final static String USER_ALREADY_EXISTS_MESSAGE = "User with given login is already exists";
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) throws RegisterServiceException {
        try {
            new UserForRegistrationValidator(user).validateUser();
        } catch (InvalidUserException e) {
            throw new RegisterServiceException(INVALID_USER_MESSAGE + e.getMessage());
        }
        User userFromDatabase = storageDao.get(user.getLogin());
        if (userFromDatabase != null) {
            throw new RegisterServiceException(USER_ALREADY_EXISTS_MESSAGE);
        }
        storageDao.add(user);
        return user;
    }
}
