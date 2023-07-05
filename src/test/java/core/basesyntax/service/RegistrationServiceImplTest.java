package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static final User NULL_USER = null;
    private static final String NULL_LOGIN = null;
    private static final String EMPTY_LOGIN = " ";
    private static final String INVALID_LOGIN = "1234";
    private static final String NULL_PASSWORD = null;
    private static final String EMPTY_PASSWORD = " ";
    private static final String INVALID_PASSWORD = "pass";
    private static final Integer NULL_AGE = null;
    private static final Integer NEGATIVE_AGE = -5;
    private static final Integer ZERO_AGE = 0;
    private static final Integer UNDER_18_AGE = 15;

    private static RegistrationService registrationService;
    private User expectedUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        expectedUser = new User("correctLogin" , "correctPassword", 18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(NULL_USER));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        Storage.people.add(expectedUser);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        expectedUser.setLogin(NULL_LOGIN);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setLogin(EMPTY_LOGIN);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setLogin(INVALID_LOGIN);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        expectedUser.setPassword(NULL_PASSWORD);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setPassword(EMPTY_PASSWORD);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
    }

    @Test
    void register_invalidAge_notOk() {
        expectedUser.setAge(NULL_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setAge(NEGATIVE_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setAge(ZERO_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
        expectedUser.setAge(UNDER_18_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(expectedUser));
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(expectedUser);
        assertNotNull(actual.getId());
    }
}