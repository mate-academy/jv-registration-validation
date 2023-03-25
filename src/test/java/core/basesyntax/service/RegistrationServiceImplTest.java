package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static User testUser;
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    private static final String VALID_PASSWORD = "123456";
    private static final String SHORT_PASSWORD = "1234";
    private static final Integer VALID_AGE = 21;
    private static final Integer INVALID_AGE = 15;
    private static final String VALID_LOGIN = "user";
    private static final String VALID_LOGIN_1 = "user1";
    private static final String VALID_LOGIN_2 = "user2";
    private static final String VALID_LOGIN_3 = "user3";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
    }

    @Test
    void register_invalidAge_NotOk() {
        testUser.setAge(INVALID_AGE);
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register1_validData_Ok() {
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
        User registratedUser = registrationService.register(testUser);
        assertEquals(testUser, registratedUser);
        assertEquals(1, Storage.people.size());
        assertEquals(testUser, Storage.people.get(0));
    }

    @Test
    void register2_sameLogin_NotOk() {
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_invalidPassword_NotOk() {
        testUser.setLogin(VALID_LOGIN_1);
        testUser.setPassword(SHORT_PASSWORD);
        testUser.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setLogin(VALID_LOGIN_2);
        testUser.setAge(null);
        testUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        testUser.setLogin(null);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setLogin(VALID_LOGIN_3);
        testUser.setPassword(null);
        testUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));

    }

    @Test
    void register_nullUser_notOk() {
        testUser = null;
        assertThrows(RegistrationException.class, () ->
                registrationService.register(testUser));
    }
}
