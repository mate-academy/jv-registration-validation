package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final String SMALL_LETTERS_PATTERN = "[a-z]";
    private static final String CAPITAL_LETTERS_PATTERN = "[A-Z]";
    private static final String DIGITS_PATTERN = "[0-9]";
    private static final String METACHARACTERS_PATTERN = "[~!@#$%^&*()_+{}\\\\[\\\\]:;,.< >/?-]";

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
        Pattern patternSmallLetters = Pattern.compile(SMALL_LETTERS_PATTERN);
        Pattern patternCapitalLetters = Pattern.compile(CAPITAL_LETTERS_PATTERN);
        Pattern patternDigits = Pattern.compile(DIGITS_PATTERN);
        Pattern patternMetaCharacters = Pattern.compile(METACHARACTERS_PATTERN);

        if (patternMetaCharacters.matcher(user.getLogin()).find()) {
            return false;
        }
        return patternSmallLetters.matcher(user.getPassword()).find()
                && patternCapitalLetters.matcher(user.getPassword()).find()
                && patternDigits.matcher(user.getPassword()).find()
                && patternMetaCharacters.matcher(user.getPassword()).find();
    }
}
