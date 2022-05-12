package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class RegistrationServiceImplTest {
    private static final int NEGATIVE_AGE = -1;
    private static final int AGE_0 = 0;
    private static final int AGE_17 = 17;
    private static final int AGE_18 = 18;
    private static final int AGE_30 = 30;
    private static final String FIVE_SYMBOLS = "qwert";
    private static final String SIX_SYMBOLS = "qwerty";
    private static final String TEN_SYMBOLS = "?-qwerty@7";
    private static final String EXISTING_LOGIN = "here_i_am";
    private static final String NEW_LOGIN = "new_first";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_SYMBOL = " ";
    private static final String TAB_SYMBOL = "  ";
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
        Storage.people.clear();
        final User existingInStorage = new User();
        existingInStorage.setLogin(EXISTING_LOGIN);
        storageDao.add(existingInStorage);
        user = new User();
        user.setLogin(NEW_LOGIN);
        user.setAge(AGE_18);
        user.setPassword(SIX_SYMBOLS);
    }

    @Test
    void register_nullUser_NotOk() {
        user = null;
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("Invalid input user", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("Invalid login or already exists", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    void register_existingLogin_NotOk() {
        user.setLogin(EXISTING_LOGIN);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("Invalid login or already exists", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin(EMPTY_STRING);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("Invalid login or already exists", e.getMessage());
            return;
        }
        fail("The user with an empty login should not be added to Storage");
    }

    @Test
    void register_spacesLogin_NotOk() {
        user.setLogin(SPACE_SYMBOL);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("Invalid login or already exists", e.getMessage());
            return;
        }
        fail("The user with an empty login should not be added to Storage");
        user.setLogin(TAB_SYMBOL);
        Executable actualTabSymbol = () -> registrationService.register(user);
        assertThrows(RuntimeException.class, actualTabSymbol);
        user.setLogin(SIX_SPACES);
        Executable actualSixSpaces = () -> registrationService.register(user);
        assertThrows(RuntimeException.class, actualSixSpaces);

    }

    @Test
    void register_Age18Password6SymbolsNewLogin_Ok() {
        User actualSuitableUser = registrationService.register(user);
        assertEquals(user, actualSuitableUser);
    }

    @Test
    void register_ageOver18_Ok() {
        user.setAge(AGE_30);
        User actualAge30 = registrationService.register(user);
        assertEquals(user, actualAge30);
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("The age should be at least 18 years old", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    void register_ageUnder18_NotOk() {
        user.setAge(AGE_17);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("The age should be at least 18 years old", e.getMessage());
            return;
        }
        fail();
        user.setAge(AGE_0);
        Executable actualAge0 = () -> registrationService.register(user);
        assertThrows(RuntimeException.class, actualAge0);
        user.setAge(NEGATIVE_AGE);
        Executable actualAgeNegative = () -> registrationService.register(user);
        assertThrows(RuntimeException.class, actualAgeNegative);
    }

    @Test
    void register_passwordOverSixSymbols_Ok() {
        user.setPassword(TEN_SYMBOLS);
        User actualPassword10 = registrationService.register(user);
        assertEquals(user, actualPassword10);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("The password should be at least 6 symbols", e.getMessage());
            return;
        }
        fail();
    }

    @Test
    void register_passwordSixSpaces_NotOk() {
        user.setPassword(SIX_SPACES);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("The password should be at least 6 symbols", e.getMessage());
            return;
        }
        fail("");
    }

    @Test
    void register_passwordUnderSixSymbols_NotOk() {
        user.setPassword(FIVE_SYMBOLS);
        try {
            registrationService.register(user);
        } catch (RuntimeException e) {
            assertEquals("The password should be at least 6 symbols", e.getMessage());
            return;
        }
        fail();
        user.setPassword(EMPTY_STRING);
        Executable actualEmptyPassword = () -> registrationService.register(user);
        assertThrows(RuntimeException.class, actualEmptyPassword);
        user.setPassword(SPACE_SYMBOL);
        Executable actualSpacePassword = () -> registrationService.register(user);
        assertThrows(RuntimeException.class, actualSpacePassword);
    }
}
