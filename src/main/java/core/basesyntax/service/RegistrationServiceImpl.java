package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int minAge = 18;
    private static final int minCredentialLength = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }
        Integer userAge = user.getAge();
        if (userAge == null || userAge < minAge) {
            String exceptionMessage =
                    userAge == null ? "Age mustn't be null"
                            : "You must be 18 years old in order to register";
            throw new RegistrationException(exceptionMessage);
        }
        String userLogin = user.getLogin();
        if (userLogin == null || userLogin.length() < minCredentialLength) {
            String exceptionMessage =
                    userLogin == null ? "Login mustn't be null"
                            : "Your login must be at least 6 characters long";
            throw new RegistrationException(exceptionMessage);
        }
        if (storageDao.get(userLogin) != null) {
            throw new RegistrationException(
                    "Such login already exists. Please choose a different one");
        }
        String userPassword = user.getPassword();
        if (userPassword == null || userPassword.length() < minCredentialLength) {
            String exceptionMessage =
                    userPassword == null ? "Password mustn't be null"
                            : "Your password must be at least 6 characters long";
            throw new RegistrationException(exceptionMessage);
        }
        return storageDao.add(user);
    }
}
