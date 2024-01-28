package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_AGE = 18;
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;

    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) throws RegistrationException {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);

        User existingUserLogin = storageDao.get(user.getLogin());

        if (existingUserLogin != null) {
            throw new RegistrationException("The user with the provided login already exists");
        }
        return storageDao.add(user);
    }

    void checkLogin(User user) throws RegistrationException {
        if (user.getLogin() == null || user.getLogin().length() < LOGIN_LENGTH) {
            throw new RegistrationException("The login can not be null "
                    + "and must be no less than 6 characters");
        }
    }

    void checkPassword(User user) throws RegistrationException {
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_LENGTH) {
            throw new RegistrationException("The password can't be null and "
                    + "must contain at least 6 characters");
        }
    }

    void checkAge(User user) throws RegistrationException {
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RegistrationException("The allowed age must be 18 "
                    + "years old");
        }
    }
}

















//package core.basesyntax.service;
//
//import core.basesyntax.dao.StorageDao;
//import core.basesyntax.dao.StorageDaoImpl;
//import core.basesyntax.exceptions.RegistrationException;
//import core.basesyntax.model.User;
//
//public class RegistrationServiceImpl implements RegistrationService {
//
//    private final int MIN_VALID_AGE = 18;
//    private final int LOGIN_LENGTH = 6;
//    private final int PASSWORD_LENGTH = 6;
//
//    private final StorageDao storageDao = new StorageDaoImpl();
//
//    @Override
//    public User register(User user) throws RegistrationException {
//        checkLogin(user);
//        checkPassword(user);
//        checkAge(user);
//
//        User existingUserLogin = storageDao.get(user.getLogin());
//
//        if (existingUserLogin != null) {
//            try {
//                throw new RegistrationException("the user with the provided login" +
//                        " exist already");
//            } catch (RegistrationException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return storageDao.add(user);
//    }
//
//    public void checkLogin(User user) throws RegistrationException {
//        if (user.getLogin() == null || user.getLogin().length() < LOGIN_LENGTH) {
//            throw new RegistrationException("The login can not be null " +
//                    "and must be no less than 6 characters");
//        }
//    }
//
//    public void checkPassword(User user) throws RegistrationException {
//        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_LENGTH) {
//            throw new RegistrationException("The password can't be null and " +
//                    "must contain at least 6 characters");
//        }
//    }
//
//    public void checkAge(User user) throws RegistrationException {
//        if (user.getAge() < MIN_VALID_AGE) {
//            throw new RegistrationException("The allowed age must be 18 " +
//                    "years old");
//        }
//    }
//}
