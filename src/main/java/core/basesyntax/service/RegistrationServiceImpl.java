package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RuntimeException {
        if (user == null) {
            throw new InvalidUserException("User can't be null!");
        }
        List<String> exceptionList = new ArrayList<>();
        if (!checkUserForAge(user)) {
            exceptionList.add("Incorrect age!");
        }
        if (!checkUserLoginForIdentity(user)) {
            exceptionList.add("There is user with this login already!");
        }
        if (!checkUserLoginForIdentity(user) || !checkUserLoginForLength(user)) {
            exceptionList.add("Incorrect login!");
        }
        if (!checkUserPasswordForLength(user)) {
            exceptionList.add("Incorrect password!");
        }
        if (exceptionList.size() == 0) {
            storageDao.add(user);
            return user;
        }
        throw new InvalidUserException(String.join(System.lineSeparator(), exceptionList));
    }

    public boolean checkLogin(User user) {
        return checkUserLoginForIdentity(user);
    }

    private boolean checkUserLoginForIdentity(User user) {
        return user.getLogin() != null && storageDao.get(user.getLogin()) == null;
    }

    private boolean checkUserLoginForLength(User user) {
        return user.getLogin().length() >= MINIMAL_LENGTH;
    }

    private boolean checkUserPasswordForLength(User user) {
        return user.getPassword() != null && user.getPassword().length() >= MINIMAL_LENGTH;
    }

    private boolean checkUserForAge(User user) {
        return user.getAge() != null && user.getAge() >= MINIMAL_AGE;
    }
}
