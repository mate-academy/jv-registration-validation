package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final String EXISTING_LOGIN = "here_i_am";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_SYMBOL = " ";
    private static final String SIX_SPACES = "      ";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        final String minSymbols = "qwerty";
        final String newLogin = "new_first";
        Storage.people.clear();
        final User existingInStorage = new User();
        existingInStorage.setLogin(EXISTING_LOGIN);
        storageDao.add(existingInStorage);
        user = new User();
        user.setLogin(newLogin);
        user.setAge(MIN_AGE);
        user.setPassword(minSymbols);
    }

    @Test
    void register_nullUser_NotOk() {
        user = null;
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("User could not be null", thrown.getMessage());
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Login could not be null", thrown.getMessage());
    }

    @Test
    void register_existingLogin_NotOk() {
        user.setLogin(EXISTING_LOGIN);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Login already exists " + user.getLogin(), thrown.getMessage());
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin(EMPTY_STRING);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Login could not be empty", thrown.getMessage());
    }

    @Test
    void register_spacesLogin_NotOk() {
        final String tabSymbol = "  ";
        user.setLogin(SPACE_SYMBOL);
        RuntimeException thrownSpace = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Login could not be empty", thrownSpace.getMessage());
        user.setLogin(tabSymbol);
        RuntimeException thrownTab = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Login could not be empty", thrownTab.getMessage());
        user.setLogin(SIX_SPACES);
        RuntimeException thrownSixSpaces = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Login could not be empty", thrownSixSpaces.getMessage());
    }

    @Test
    void register_Age18Password6SymbolsNewLogin_Ok() {
        User actualSuitableUser = registrationService.register(user);
        assertEquals(user, actualSuitableUser);
    }

    @Test
    void register_ageOver18_Ok() {
        final int age30 = 30;
        user.setAge(age30);
        User actualAge30 = registrationService.register(user);
        assertEquals(user, actualAge30);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Age could not be null", thrown.getMessage());
    }

    @Test
    void register_ageUnder18_NotOk() {
        final int negativeAge = -1;
        final int age0 = 0;
        final int age17 = 17;
        user.setAge(age17);
        RuntimeException thrownAge17 = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Age should be at least " + MIN_AGE + " years old", thrownAge17.getMessage());
        user.setAge(age0);
        RuntimeException thrownAge0 = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Age should be at least " + MIN_AGE + " years old", thrownAge0.getMessage());
        user.setAge(negativeAge);
        RuntimeException thrownNegativeAge = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Age should be at least "
                + MIN_AGE + " years old", thrownNegativeAge.getMessage());
    }

    @Test
    void register_passwordOverSixSymbols_Ok() {
        final String tenSymbols = "?-qwerty@7";
        user.setPassword(tenSymbols);
        User actualPassword10 = registrationService.register(user);
        assertEquals(user, actualPassword10);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Password could not be null", thrown.getMessage());
    }

    @Test
    void register_passwordSixSpaces_NotOk() {
        user.setPassword(SIX_SPACES);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Password should be at least 6 symbols", thrown.getMessage());
    }

    @Test
    void register_passwordUnderSixSymbols_NotOk() {
        final String fiveSymbols = "qwert";
        user.setPassword(fiveSymbols);
        RuntimeException thrownFiveSymbols = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Password should be at least 6 symbols", thrownFiveSymbols.getMessage());
        user.setPassword(EMPTY_STRING);
        RuntimeException thrownEmptyString = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Password should be at least 6 symbols", thrownEmptyString.getMessage());
        user.setPassword(SPACE_SYMBOL);
        RuntimeException thrownSpace = assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
        assertEquals("Password should be at least 6 symbols", thrownSpace.getMessage());
    }
}
