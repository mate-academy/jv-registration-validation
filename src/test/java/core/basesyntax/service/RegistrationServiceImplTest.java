package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String ANOTHER_PASSWORD = "anotherPassword";
    private static final int VALID_AGE = 20;
    private static final int ANOTHER_AGE = 25;
    private static final int MIN_AGE = 18;
    private static final int UNDER_AGE = 17;
    private static final int NEGATIVE_AGE = -1;
    private static final String EMPTY_STRING = "";
    private static final String THREE_CHAR_STRING = "abc";
    private static final String FIVE_CHAR_STRING = "abcdf";
    private static final String SIX_CHAR_STRING = "abcdef";
    private static final String EIGHT_CHAR_STRING = "abcdefgh";

    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
        assertEquals(validUser, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        validUser.setLogin(FIVE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_minLengthLogin_Ok() {
        validUser.setLogin(SIX_CHAR_STRING);
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(validUser);
        User duplicateUser = new User();
        duplicateUser.setLogin(VALID_LOGIN);
        duplicateUser.setPassword(ANOTHER_PASSWORD);
        duplicateUser.setAge(ANOTHER_AGE);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(duplicateUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        validUser.setPassword(FIVE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_emptyPassword_notOk() {
        validUser.setPassword(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_threeCharPassword_notOk() {
        validUser.setPassword(THREE_CHAR_STRING);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_sixCharPassword_Ok() {
        validUser.setPassword(SIX_CHAR_STRING);
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_eightCharPassword_Ok() {
        validUser.setPassword(EIGHT_CHAR_STRING);
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_minAge_Ok() {
        validUser.setAge(MIN_AGE);
        User actual = registrationService.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void register_negativeAge_notOk() {
        validUser.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }

    @Test
    void register_underAge_notOk() {
        validUser.setAge(UNDER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(validUser);
        });
    }
}

