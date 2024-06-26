package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws CustomException {
        if (user == null) {
            throw new CustomException("User is null.");
        } else if (user.getLogin() == null) {
            throw new CustomException("The login is null.");
        } else if (user.getPassword() == null) {
            throw new CustomException("The password is null.");
        } else if (user.getAge() == null) {
            throw new CustomException("The age is null.");
        }
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null && user.getLogin().equals(existingUser.getLogin())) {
            throw new CustomException("There is an user with the same login.");
        }
        if (user.getLogin().length() < 6) {
            throw new CustomException("The length of login is less than 6.");
        } else if (user.getPassword().length() < 6) {
            throw new CustomException("The length of password is less than 6.");
        } else if (user.getAge() < 0) {
            throw new CustomException("The age of user must be greater than 0.");
        } else if (user.getAge() < 18) {
            throw new CustomException("The age of user is less than 18.");
        }
        storageDao.add(user);
        return user;
    }
}
