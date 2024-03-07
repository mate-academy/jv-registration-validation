package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String STRING_5_CHARACTERS = "Elian";
    public static final String STRING_6_CHARACTERS = "Harris";
    public static final String STRING_7_CHARACTERS = "Griffin";
    public static final String EMPTY_STRING = "";
    public static final int NEGATIVE_AGE = -20;
    public static final int ZERO_AGE = 0;
    public static final int AGE_17 = 17;
    public static final int AGE_18 = 18;
    public static final int AGE_19 = 19;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User(STRING_6_CHARACTERS, STRING_6_CHARACTERS, AGE_18);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(ZERO_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_login5Char_notOk() {
        user.setLogin(STRING_5_CHARACTERS);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_password5Char_notOk() {
        user.setPassword(STRING_5_CHARACTERS);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_age17_notOk() {
        user.setAge(AGE_17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addExistUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithLogin6Characters_Ok() {
        user.setLogin(STRING_6_CHARACTERS);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithLogin7Characters_Ok() {
        user.setLogin(STRING_7_CHARACTERS);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithPassword6Characters_Ok() {
        user.setPassword(STRING_6_CHARACTERS);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userWithPassword7Characters_Ok() {
        user.setPassword(STRING_7_CHARACTERS);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userAge18_Ok() {
        user.setAge(AGE_18);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_userAge19_Ok() {
        user.setAge(AGE_19);
        registrationService.register(user);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_multipleCall_Ok() {
        for (int i = 0; i < 1000; i++) {
            registrationService.register(new User(STRING_6_CHARACTERS
                    + i, STRING_6_CHARACTERS, AGE_18));
        }
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
