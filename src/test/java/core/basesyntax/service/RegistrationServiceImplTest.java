package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationFailedException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int INITIAL_AGE = 35;
    private static final String INITIAL_LOGIN = "Shevshenko";
    private static final String INITIAL_PASSWORD = "Password2023";
    private static final String INCORRECT_PASSWORD = "12345";
    private static final int INCORRECT_LOW_AGE = 15;
    private static final int INCORRECT_HIGH_AGE = 131;
    private static User defaultUser;
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    public static void beforeAll() {
        defaultUser = new User();
    }

    @BeforeEach
    public void setUp() {
        defaultUser.setAge(INITIAL_AGE);
        defaultUser.setLogin(INITIAL_LOGIN);
        defaultUser.setPassword(INITIAL_PASSWORD);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_emptyLoginLength_notOk() {
        defaultUser.setLogin("");
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_correctUser_Ok() {
        registrationService.register(defaultUser);
        assertEquals(defaultUser, Storage.people.get(0));
    }

    @Test
    public void register_userIsNull_notOk() {
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_nullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_nullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_ageLessThanMin_notOk() {
        defaultUser.setAge(INCORRECT_LOW_AGE);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_ageMore100_notOk() {
        defaultUser.setAge(INCORRECT_HIGH_AGE);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_incorrectPassword_notOk() {
        defaultUser.setPassword(INCORRECT_PASSWORD);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }

    @Test
    public void register_userAlreadyExists_notOk() {
        registrationService.register(defaultUser);
        assertThrows(RegistrationFailedException.class, () -> {
            registrationService.register(defaultUser);
        });
    }
}
