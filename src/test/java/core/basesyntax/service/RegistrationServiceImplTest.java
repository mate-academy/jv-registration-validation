package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserAddException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_NAME = "Markus";
    private static final String VALID_PASSWORD = "MarkPass";
    private static final int VALID_AGE = 22;
    private static final int MINIMUM_DEMANDING_LENGTH = 6;
    private static final int MINIMUM_DEMANDING_AGE = 18;
    private RegistrationService registrationService;
    private StorageDao storageDao;
    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User();
        newUser.setLogin(VALID_NAME);
        newUser.setPassword(VALID_PASSWORD);
        newUser.setAge(VALID_AGE);
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_nonExistingUser_Ok() {
        User actualUser = registrationService.register(newUser);
        User expectedUser = storageDao.get(newUser.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_userLoginEmpty_notOk() {
        newUser.setLogin("");
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user login should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userLoginNull_notOk() {
        newUser.setLogin(null);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userLogin3Symbols_notOk() {
        String invalid3SymbolsLogin = "Mar";
        newUser.setLogin(invalid3SymbolsLogin);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user login should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userLogin5Symbols_notOk() {
        String invalid5SymbolsLogin = "Marku";
        newUser.setLogin(invalid5SymbolsLogin);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user login should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userLoginShortThen6Symbols_notOk() {
        String invalidShortLogin = "Mark";
        newUser.setLogin(invalidShortLogin);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user login should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userPasswordEmpty_notOk() {
        String invalidEmptyPassword = "";
        newUser.setPassword(invalidEmptyPassword);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user password should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userPasswordNull_notOk() {
        newUser.setPassword(null);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userPassword3Symbols_notOk() {
        String invalid3SymbolsPassword = "Mar";
        newUser.setPassword(invalid3SymbolsPassword);
        UserAddException userAddException = assertThrows(
                UserAddException.class, () ->
                        registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user password should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userPassword5Symbols_notOk() {
        String invalid5SymbolsPassword = "Marku";
        newUser.setPassword(invalid5SymbolsPassword);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user password should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userPasswordShortThen6Symbols_notOk() {
        String invalidShortPassword = "Marku";
        newUser.setPassword(invalidShortPassword);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("Length of user password should be more or equals "
                        + MINIMUM_DEMANDING_LENGTH
                        + "symbols"));
    }

    @Test
    void register_userAgeNull_notOk() {
        newUser.setAge(null);
        assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userNegativeAge_notOk() {
        int negativeAge = -5;
        newUser.setAge(negativeAge);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("User age  should be more or equals "
                        + MINIMUM_DEMANDING_AGE
                        + " years"));
    }

    @Test
    void register_userUnderage18_notOk() {
        int invalidAge = 17;
        newUser.setAge(invalidAge);
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("User age  should be more or equals "
                        + MINIMUM_DEMANDING_AGE
                        + " years"));
    }

    @Test
    void register_existingUser_notOk() {
        storageDao.add(newUser);
        User existingUser = storageDao.get(newUser.getLogin());
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(existingUser));
        assertTrue(userAddException.getMessage()
                .contains("User " + existingUser.getLogin() + " has already been added"));
    }

    @Test
    void register_nullUser_notOk() {
        newUser = null;
        UserAddException userAddException = assertThrows(UserAddException.class, () ->
                registrationService.register(newUser));
        assertTrue(userAddException.getMessage()
                .contains("There is no user to add , because user is null"));
    }
}
