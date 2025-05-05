package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String INVALID_EMPTY_LINE = "";
    private static final String INVALID_ONE_CHARACTER_LINE = "a";
    private static final String INVALID_THREE_CHARACTERS_LINE = "abc";
    private static final String INVALID_FIVE_CHARACTERS_LINE = "12345";
    private static final String VALID_EDGE_SIX_SYMBOLS_LINE = "Always";
    private static final String VALID_LOGIN = "anotherLogin";
    private static final String VALID_PASSWORD = "longEnoughPassword";
    private static final String NULL_USER_EXCEPTION_MESSAGE = "There is no user to add";
    private static final String NULL_LOGIN_EXCEPTION_MESSAGE = "Login can't be null";
    private static final String NULL_PASSWORD_EXCEPTION_MESSAGE = "Password can't be null";
    private static final String NULL_AGE_EXCEPTION_MESSAGE = "Age can't be null";
    private static final String INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE =
            "Login should be at least 6 characters long. User's login is ";
    private static final String INVALID_PASSWORD_LENGTH_EXCEPTION_MESSAGE =
            "Password should be at least 6 characters long. User's password is ";
    private static final String INVALID_AGE_EXCEPTION_MESSAGE =
            "Age should be at least 18, user's age is ";
    private static final String USER_ALREADY_EXISTS_EXCEPTION_MESSAGE =
            "User with same login already exists";
    private static final int INVALID_NEGATIVE_AGE = -599;
    private static final int INVALID_ZERO_AGE = 0;
    private static final int INVALID_SEVENTEEN_AGE = 17;
    private static final int VALID_EDGE_EIGHTEEN_AGE = 18;
    private static final int VALID_AGE = 27;
    private final RegistrationService service = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    public void add_validUser_Ok() {
        User validUser = createStandardUser();
        service.register(validUser);
        assertEquals(validUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void add_sixCharactersLoginUser_Ok() {
        User sixCharactersLoginUser = createStandardUser();
        sixCharactersLoginUser.setLogin(VALID_EDGE_SIX_SYMBOLS_LINE);
        service.register(sixCharactersLoginUser);
        assertEquals(sixCharactersLoginUser, storageDao.get(VALID_EDGE_SIX_SYMBOLS_LINE));
    }

    @Test
    public void add_sixCharactersPasswordUser_Ok() {
        User sixCharactersPasswordUser = createStandardUser();
        sixCharactersPasswordUser.setPassword(VALID_EDGE_SIX_SYMBOLS_LINE);
        service.register(sixCharactersPasswordUser);
        assertEquals(sixCharactersPasswordUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void add_eighteenYearsOldUser_Ok() {
        User eighteenYearsOldUser = createStandardUser();
        eighteenYearsOldUser.setAge(VALID_EDGE_EIGHTEEN_AGE);
        service.register(eighteenYearsOldUser);
        assertEquals(eighteenYearsOldUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void add_negativeAgeUser_notOk() {
        User negativeAgeUser = createStandardUser();
        negativeAgeUser.setAge(INVALID_NEGATIVE_AGE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(negativeAgeUser));
        assertEquals(INVALID_AGE_EXCEPTION_MESSAGE
                + INVALID_NEGATIVE_AGE, exception.getMessage());
    }

    @Test
    public void add_null_notOk() {
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(null));
        assertEquals(NULL_USER_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void add_nullLoginUser_notOk() {
        User nullLoginUser = createStandardUser();
        nullLoginUser.setLogin(null);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(nullLoginUser));
        assertEquals(NULL_LOGIN_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void add_nullPasswordUser_notOk() {
        User nullPasswordUser = createStandardUser();
        nullPasswordUser.setPassword(null);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(nullPasswordUser));
        assertEquals(NULL_PASSWORD_EXCEPTION_MESSAGE, exception.getMessage());

    }

    @Test
    public void add_nullLoginAndPasswordUser_notOk() {
        User nullLoginAndPasswordUser = createStandardUser();
        nullLoginAndPasswordUser.setLogin(null);
        nullLoginAndPasswordUser.setPassword(null);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(nullLoginAndPasswordUser));
        assertEquals(NULL_LOGIN_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void add_emptyLoginUser_notOk() {
        User emptyLoginUser = createStandardUser();
        emptyLoginUser.setLogin(INVALID_EMPTY_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(emptyLoginUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_EMPTY_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_emptyPasswordUser_notOk() {
        User emptyPasswordUser = createStandardUser();
        emptyPasswordUser.setPassword(INVALID_EMPTY_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(emptyPasswordUser));
        assertEquals(INVALID_PASSWORD_LENGTH_EXCEPTION_MESSAGE
                + INVALID_EMPTY_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_emptyLoginAndPasswordUser_notOk() {
        User emptyLoginAndPasswordUser = createStandardUser();
        emptyLoginAndPasswordUser.setLogin(INVALID_EMPTY_LINE);
        emptyLoginAndPasswordUser.setPassword(INVALID_EMPTY_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(emptyLoginAndPasswordUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_EMPTY_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_oneSymbolLoginUser_notOk() {
        User oneSymbolLoginUser = createStandardUser();
        oneSymbolLoginUser.setLogin(INVALID_ONE_CHARACTER_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(oneSymbolLoginUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_ONE_CHARACTER_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_threeSymbolsLoginUser_notOk() {
        User threeSymbolLoginUser = createStandardUser();
        threeSymbolLoginUser.setLogin(INVALID_THREE_CHARACTERS_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(threeSymbolLoginUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_THREE_CHARACTERS_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_fiveSymbolsLoginUser_notOk() {
        User fiveSymbolLoginUser = createStandardUser();
        fiveSymbolLoginUser.setLogin(INVALID_FIVE_CHARACTERS_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(fiveSymbolLoginUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_FIVE_CHARACTERS_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_oneSymbolPasswordUser_notOk() {
        User oneSymbolPasswordUser = createStandardUser();
        oneSymbolPasswordUser.setPassword(INVALID_ONE_CHARACTER_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(oneSymbolPasswordUser));
        assertEquals(INVALID_PASSWORD_LENGTH_EXCEPTION_MESSAGE
                + INVALID_ONE_CHARACTER_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_threeSymbolsPasswordUser_notOk() {
        User threeSymbolPasswordUser = createStandardUser();
        threeSymbolPasswordUser.setPassword(INVALID_THREE_CHARACTERS_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(threeSymbolPasswordUser));
        assertEquals(INVALID_PASSWORD_LENGTH_EXCEPTION_MESSAGE
                + INVALID_THREE_CHARACTERS_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_fiveSymbolsPasswordUser_notOk() {
        User fiveSymbolPasswordUser = createStandardUser();
        fiveSymbolPasswordUser.setPassword(INVALID_FIVE_CHARACTERS_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(fiveSymbolPasswordUser));
        assertEquals(INVALID_PASSWORD_LENGTH_EXCEPTION_MESSAGE
                + INVALID_FIVE_CHARACTERS_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_fiveSymbolsLoginAndPasswordUser_notOk() {
        User shortLoginAndPasswordUser = createStandardUser();
        shortLoginAndPasswordUser.setLogin(INVALID_FIVE_CHARACTERS_LINE);
        shortLoginAndPasswordUser.setPassword(INVALID_FIVE_CHARACTERS_LINE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(shortLoginAndPasswordUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_FIVE_CHARACTERS_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_zeroAgeUser_notOk() {
        User zeroAgeUser = createStandardUser();
        zeroAgeUser.setAge(INVALID_ZERO_AGE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(zeroAgeUser));
        assertEquals(INVALID_AGE_EXCEPTION_MESSAGE
                + INVALID_ZERO_AGE, exception.getMessage());
    }

    @Test
    public void add_seventeenAgeUser_notOk() {
        User seventeenAgeUser = createStandardUser();
        seventeenAgeUser.setAge(INVALID_SEVENTEEN_AGE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(seventeenAgeUser));
        assertEquals(INVALID_AGE_EXCEPTION_MESSAGE
                + INVALID_SEVENTEEN_AGE, exception.getMessage());
    }

    @Test
    public void add_invalidAllFieldsUser_notOk() {
        User invalidAllFieldsUser = createStandardUser();
        invalidAllFieldsUser.setLogin(INVALID_ONE_CHARACTER_LINE);
        invalidAllFieldsUser.setPassword(INVALID_THREE_CHARACTERS_LINE);
        invalidAllFieldsUser.setAge(INVALID_SEVENTEEN_AGE);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(invalidAllFieldsUser));
        assertEquals(INVALID_LOGIN_LENGTH_EXCEPTION_MESSAGE
                + INVALID_ONE_CHARACTER_LINE.length(), exception.getMessage());
    }

    @Test
    public void add_noAgeUser_notOk() {
        User noAgeUser = createStandardUser();
        noAgeUser.setAge(null);
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(noAgeUser));
        assertEquals(NULL_AGE_EXCEPTION_MESSAGE, exception.getMessage());

    }

    @Test
    public void add_sameLoginUser_notOk() {
        User firstUser = createStandardUser();
        service.register(firstUser);
        User sameLoginUser = createStandardUser();
        Throwable exception = assertThrows(RegistrationException.class,
                () -> service.register(sameLoginUser));
        assertEquals(USER_ALREADY_EXISTS_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @AfterEach
    public void resetStorage() {
        Storage.people.clear();
    }

    private User createStandardUser() {
        User standartUser = new User();
        standartUser.setLogin(VALID_LOGIN);
        standartUser.setPassword(VALID_PASSWORD);
        standartUser.setAge(VALID_AGE);
        return standartUser;
    }
}
