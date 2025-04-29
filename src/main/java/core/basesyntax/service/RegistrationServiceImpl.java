package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            if (user.getLogin() == null) {
                throw new IncorrectDataException("Login can't be null");
            } else if (user.getLogin().length() < 6) {
                throw new IncorrectDataException("Login length "
                        + "must be more than 6 characters");
            }

            passwordVerification(user.getPassword());

            if (user.getAge() == null) {
                throw new IncorrectDataException("Age can't be null");
            } else if (user.getAge() < 18) {
                throw new IncorrectDataException("Age must "
                        + "be equal to or greater than 18");
            }
            storageDao.add(user);
            return user;
        }
        return null;
    }

    public void passwordVerification(String password) {
        if (password == null) {
            throw new IncorrectDataException("Password can't be null");
        } else if (password.length() < 6) {
            throw new IncorrectDataException("Password length "
                    + "must be more than 6 characters");
        }

        char[] passwordCharacters = password.toCharArray();

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialSymbol = false;

        for (Character element : passwordCharacters) {
            if (Character.isUpperCase(element)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(element)) {
                hasLowerCase = true;
            } else if (Character.isDigit(element)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(element)) {
                hasSpecialSymbol = true;
            }
        }

        if (!hasUpperCase) {
            throw new IncorrectDataException("Password must contain at least one capital letter");
        } else if (!hasLowerCase) {
            throw new IncorrectDataException("Password must contain at least one lowercase letter");
        } else if (!hasNumber) {
            throw new IncorrectDataException("Password must contain at least one number");
        } else if (!hasSpecialSymbol) {
            throw new IncorrectDataException("Password must contain at least one special symbol");
        }
    }
}
