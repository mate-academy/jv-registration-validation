package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationServiceException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String DEFAULT_LOGIN = "SomeLoginName";
    public static final String DEFAULT_PASSWORD = "Word123Pass";
    public static final String LESS_THAN_6_PASSWORD = "rat";
    public static final int DEFAULT_AGE = 75;
    public static final int AGE_LESS_THAN_18 = 2;
    public static final int AGE_IS_18 = 18;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void registrationServiceCreation() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin(DEFAULT_LOGIN);
        testUser.setPassword(DEFAULT_PASSWORD);
        testUser.setAge(DEFAULT_AGE);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_loginExist_notOk() {
        User tempUser = new User();
        tempUser.setLogin(DEFAULT_LOGIN);
        tempUser.setPassword(DEFAULT_PASSWORD);
        tempUser.setAge(AGE_IS_18);
        registrationService.register(tempUser);
        testUser.setLogin(DEFAULT_LOGIN);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(testUser);
        }, "RegistrationServiceException expected for login " + DEFAULT_LOGIN);
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(testUser);
        }, "RegistrationServiceException expected for null age");
    }

    @Test
    void register_passwordLessSymbols_notOk() {
        testUser.setPassword(LESS_THAN_6_PASSWORD);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(testUser);
        }, "RegistrationServiceException expected for password " + LESS_THAN_6_PASSWORD);
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(testUser);
        }, "RegistrationServiceException expected for null age");
    }

    @Test
    void register_ageEquals18_Ok() {
        testUser.setAge(AGE_IS_18);
        registrationService.register(testUser);
        assertEquals(AGE_IS_18, testUser.getAge(), "Age accepted");
    }

    @Test
    void register_ageLessThan18_notOk() {
        testUser.setAge(AGE_LESS_THAN_18);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(testUser);
        }, "RegistrationServiceException expected for age " + AGE_LESS_THAN_18);
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(testUser);
        }, "RegistrationServiceException expected for null age");
    }

    @Test
    void register_correctData_ok() {
        registrationService.register(testUser);
        assertEquals(DEFAULT_LOGIN, testUser.getLogin());
        assertEquals(DEFAULT_PASSWORD, testUser.getPassword());
        assertEquals(DEFAULT_AGE, testUser.getAge());
        assertNotNull(testUser.getId());
    }
}
