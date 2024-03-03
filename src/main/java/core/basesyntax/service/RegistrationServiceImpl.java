package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.UserNullException;
import core.basesyntax.exeption.age.InvalidAgeException;
import core.basesyntax.exeption.login.InvalidLoginCharacterException;
import core.basesyntax.exeption.login.InvalidLoginLengthException;
import core.basesyntax.exeption.login.LoginAlreadyExistsException;
import core.basesyntax.exeption.login.NullLoginException;
import core.basesyntax.exeption.password.InvalidPasswordCharacterException;
import core.basesyntax.exeption.password.InvalidPasswordLengthException;
import core.basesyntax.exeption.password.NullPasswordException;
import core.basesyntax.exeption.password.SequentialPatternException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 130;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int DIGIT_START = 48;
    private static final int DIGIT_END = 57;
    private static final int UPPERCASE_START = 65;
    private static final int UPPERCASE_END = 90;
    private static final int LOWERCASE_START = 97;
    private static final int LOWERCASE_END = 122;
    private static final int CHARACTER_RANGE_START = 33;
    private static final int CHARACTER_RANGE_END = 126;
    private static final int MAX_REPEATED_CHARACTERS = 3;
    private static final int MAX_SEQUENTIAL_INCREASING_SYMBOLS = 3;
    private static final int MAX_SEQUENTIAL_DECREASING_SYMBOLS = 3;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserNullException();
        }
        checkUserAge(user);
        validateLogin(user);
        validatePassword(user);
        return storageDao.add(user);
    }

    public void checkUserAge(User user) {
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidAgeException("Registration age must be over 18");
        }
        if (user.getAge() > MAX_USER_AGE) {
            throw new InvalidAgeException("Enter your real age!");
        }
    }

    public void validateLogin(User user) {
        validateLoginNotNull(user);
        validateLoginLength(user);
        checkLoginAvailability(user);
        validateLoginCharacters(user);
    }

    public void validateLoginNotNull(User user) {
        if (user.getLogin() == null) {
            throw new NullLoginException();
        }
    }

    public void validateLoginLength(User user) {
        int currentLoginLength = user.getLogin().length();
        if (currentLoginLength < MIN_LOGIN_LENGTH || currentLoginLength > MAX_LOGIN_LENGTH) {
            throw new InvalidLoginLengthException(currentLoginLength);
        }
    }

    public void checkLoginAvailability(User user) {
        String currentLogin = user.getLogin();
        if (storageDao.get(currentLogin) != null) {
            throw new LoginAlreadyExistsException();
        }
    }

    public void validateLoginCharacters(User user) {
        char[] loginCharacters = user.getLogin().toCharArray();
        List<Character> unacceptableSymbols = new ArrayList<>();
        for (int i = 0; i < loginCharacters.length; i++) {
            int charCode = loginCharacters[i];
            if (charCode < DIGIT_START || charCode > DIGIT_END
                    && charCode < UPPERCASE_START || charCode > UPPERCASE_END
                    && charCode < LOWERCASE_START || charCode > LOWERCASE_END) {
                unacceptableSymbols.add(loginCharacters[i]);
            }
        }
        if (!unacceptableSymbols.isEmpty()) {
            throw new InvalidLoginCharacterException(unacceptableSymbols.toString());
        }
    }

    public void validatePassword(User user) {
        validatePasswordNotNull(user);
        validatePasswordLength(user);
        validatePasswordCharacters(user);
        checkForSequentialPattern(user);
    }

    public void validatePasswordNotNull(User user) {
        if (user.getPassword() == null) {
            throw new NullPasswordException();
        }
    }

    public void validatePasswordLength(User user) {
        int currentPasswordLength = user.getPassword().length();
        if (currentPasswordLength < MIN_PASSWORD_LENGTH
                || currentPasswordLength > MAX_PASSWORD_LENGTH) {
            throw new InvalidPasswordLengthException(currentPasswordLength);
        }
    }

    public void validatePasswordCharacters(User user) {
        char[] passwordCharacters = user.getPassword().toCharArray();
        List<Character> unacceptableSymbols = new ArrayList<>();
        for (int i = 0; i < passwordCharacters.length; i++) {
            int charCode = passwordCharacters[i];
            if (charCode < CHARACTER_RANGE_START || charCode > CHARACTER_RANGE_END) {
                unacceptableSymbols.add(passwordCharacters[i]);
            }
        }
        if (!unacceptableSymbols.isEmpty()) {
            throw new InvalidPasswordCharacterException(unacceptableSymbols.toString());
        }
    }

    public void checkForSequentialPattern(User user) {
        char[] passwordCharacters = user.getPassword().toCharArray();
        checkForRepeatedCharacters(passwordCharacters);
        checkForSequentiallyIncreasingSymbols(passwordCharacters);
        checkForSequentiallyDecreasingSymbols(passwordCharacters);
    }

    private void checkForRepeatedCharacters(char[] passwordCharacters) {
        List<Character> invalidCharacterSequence = new ArrayList<>();
        int repeatedCharacters = 0;
        for (int i = 0; i < passwordCharacters.length - 1; i++) {
            int previousCharCode = passwordCharacters[i];
            int currentCharCode = passwordCharacters[i + 1];
            if (previousCharCode == currentCharCode) {
                if (repeatedCharacters == 0) {
                    invalidCharacterSequence.clear();
                    invalidCharacterSequence.add(passwordCharacters[i]);
                    repeatedCharacters++;
                } else {
                    invalidCharacterSequence.add(passwordCharacters[i]);
                    repeatedCharacters++;
                    if (repeatedCharacters == MAX_REPEATED_CHARACTERS) {
                        invalidCharacterSequence.add(passwordCharacters[i + 1]);
                        throw new SequentialPatternException(invalidCharacterSequence.toString());
                    }
                }
            } else {
                repeatedCharacters = 0;
                invalidCharacterSequence.clear();
            }
        }
    }

    private void checkForSequentiallyIncreasingSymbols(char[] passwordCharacters) {
        List<Character> invalidCharacterSequence = new ArrayList<>();
        int sequentiallyIncreasingSymbols = 0;
        for (int i = 0; i < passwordCharacters.length - 1; i++) {
            int previousCharCode = passwordCharacters[i];
            int currentCharCode = passwordCharacters[i + 1];
            if (currentCharCode - previousCharCode == 1) {
                if (sequentiallyIncreasingSymbols == 0) {
                    invalidCharacterSequence.clear();
                    invalidCharacterSequence.add(passwordCharacters[i]);
                    sequentiallyIncreasingSymbols++;
                } else {
                    invalidCharacterSequence.add(passwordCharacters[i]);
                    sequentiallyIncreasingSymbols++;
                    if (sequentiallyIncreasingSymbols == MAX_SEQUENTIAL_INCREASING_SYMBOLS) {
                        invalidCharacterSequence.add(passwordCharacters[i + 1]);
                        throw new SequentialPatternException(invalidCharacterSequence.toString());
                    }
                }
            } else {
                sequentiallyIncreasingSymbols = 0;
                invalidCharacterSequence.clear();
            }
        }
    }

    private void checkForSequentiallyDecreasingSymbols(char[] passwordCharacters) {
        List<Character> invalidCharacterSequence = new ArrayList<>();
        int charactersDecreaseSequentially = 0;
        for (int i = 0; i < passwordCharacters.length - 1; i++) {
            int previousCharCode = passwordCharacters[i];
            int currentCharCode = passwordCharacters[i + 1];
            if (currentCharCode - previousCharCode == -1) {
                if (charactersDecreaseSequentially == 0) {
                    invalidCharacterSequence.clear();
                    invalidCharacterSequence.add(passwordCharacters[i]);
                    charactersDecreaseSequentially++;
                } else {
                    invalidCharacterSequence.add(passwordCharacters[i]);
                    charactersDecreaseSequentially++;
                    if (charactersDecreaseSequentially == MAX_SEQUENTIAL_DECREASING_SYMBOLS) {
                        invalidCharacterSequence.add(passwordCharacters[i + 1]);
                        throw new SequentialPatternException(invalidCharacterSequence.toString());
                    }
                }
            } else {
                charactersDecreaseSequentially = 0;
                invalidCharacterSequence.clear();
            }
        }
    }
}
