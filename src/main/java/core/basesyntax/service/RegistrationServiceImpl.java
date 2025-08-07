package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_LENGTH_PASSWORD_AND_LOGIN = 6;
    private static final String[] popularPasswords = new String[]{"123456789", "qwerty"};
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws NotValidData {
        if (user == null) {
            throw new NotValidData("User can not be null. Create another User.");
        }
        if (user.getPassword() == null) {
            throw new NotValidData("Can not created new profile. Your password is too short.");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD_AND_LOGIN) {
            throw new NotValidData("Can not created new profile. Your password is empty.");
        }
        if (user.getPassword().trim().isEmpty()) {
            throw new NotValidData("Can not created new profile."
                    + " Your password don not have symbols.");
        }
        for (String currentPassword : popularPasswords) {
            if (currentPassword.equals(user.getPassword())) {
                throw new NotValidData("Using this password it`s bad practice, "
                        + " you must creating something new.");
            }
        }
        if (user.getAge() == null) {
            throw new NotValidData("Your age is null.");
        }

        if (user.getAge() < MIN_AGE) {
            throw new NotValidData("Your age must be more than " + MIN_AGE + " years old.");
        }
        if (user.getLogin() == null) {
            throw new NotValidData("Can not created new profile. Your login is null.");
        }
        if (user.getLogin().length() < MIN_LENGTH_PASSWORD_AND_LOGIN) {
            throw new NotValidData("Can not created new profile. Your login is too short.");
        }
        if (user.getLogin().trim().isEmpty()) {
            throw new NotValidData("Can not created new profile. Your login do not have symbols.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidData("Profile with this login already exists.");
        }
        storageDao.add(user);
        return user;
    }
}
