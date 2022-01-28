package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final Pattern SMALL_LETTERS_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern CAPITAL_LETTERS_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern DIGITS_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern METACHARACTERS_PATTERN
            = Pattern.compile("[~!@#$%^&*()_+{}\\\\[\\\\]:;,.< >/?-]");

    @Override
    public User register(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        if (validate(user)) {
            storageDao.add(user);
        } else {
            throw new RuntimeException("Provided user data are not valid");
        }
        return storageDao.get(user.getLogin());
    }

    private boolean validate(User user) {
        if (user == null) {
            return false;
        }
        if (user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null
                || user.getAge() < MINIMUM_USER_AGE
                || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            return false;
        }
        for (User person : Storage.people) {
            if (person.getLogin().equals(user.getLogin())) {
                return false;
            }
        }
        if (METACHARACTERS_PATTERN.matcher(user.getLogin()).find()) {
            return false;
        }
        return SMALL_LETTERS_PATTERN.matcher(user.getPassword()).find()
                && CAPITAL_LETTERS_PATTERN.matcher(user.getPassword()).find()
                && DIGITS_PATTERN.matcher(user.getPassword()).find()
                && METACHARACTERS_PATTERN.matcher(user.getPassword()).find();
    }
}
