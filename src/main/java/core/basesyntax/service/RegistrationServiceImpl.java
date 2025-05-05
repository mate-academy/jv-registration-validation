package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.EmptyLoginException;
import core.basesyntax.exceptions.EmptyPasswordException;
import core.basesyntax.exceptions.EqualUsersException;
import core.basesyntax.exceptions.LengthLoginException;
import core.basesyntax.exceptions.LengthPasswordException;
import core.basesyntax.exceptions.NegativeAgeException;
import core.basesyntax.exceptions.NullAgeException;
import core.basesyntax.exceptions.NullLoginException;
import core.basesyntax.exceptions.NullPasswordException;
import core.basesyntax.exceptions.NullUserException;
import core.basesyntax.exceptions.UnderAgeException;
import core.basesyntax.exceptions.WhiteSpacedPasswordException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_PASS_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullUserException("Argument cannot be null!");
        }
        if (user.getAge() == null) {
            throw new NullAgeException("User's age is cannot be null!");
        }
        if (user.getAge() < 0) {
            throw new NegativeAgeException("Age cannot be negative");
        }
        if (user.getPassword() == null) {
            throw new NullPasswordException("Password cannot be null");
        }
        if (user.getPassword().equals("")) {
            throw new EmptyPasswordException("Password cannot be empty");
        }
        if (user.getPassword().trim().equals("")) {
            throw new WhiteSpacedPasswordException("Password cannot contain only whitespace");
        }
        if (user.getPassword().length() < MINIMAL_PASS_LENGTH) {
            throw new LengthPasswordException("Password must be longer than "
                    + MINIMAL_PASS_LENGTH);
        }
        if (user.getLogin() == null) {
            throw new NullLoginException("Login cannot be null");
        }
        if (user.getLogin().trim().equals("")) {
            throw new EmptyLoginException("Login cannot contain whitespace or to be empty");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new LengthLoginException("Login must be longer than "
                    + MINIMAL_LOGIN_LENGTH);
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new UnderAgeException("Age should be bigger than " + MINIMAL_AGE);
        }
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        }
        throw new EqualUsersException("Such user already exists");
    }
}
