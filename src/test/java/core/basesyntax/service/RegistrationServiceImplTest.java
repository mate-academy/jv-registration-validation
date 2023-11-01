package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User DEFAULT_VALID_USER = new User();
    private static final User SECOND_VALID_USER = new User();
    private static final int NEGATIVE_AGE = -13;
    private static final int ZERO_AGE = 0;
    private static final int UNDERAGE_AGE = 17;
    private static final int MINIMUM_VALID_AGE = 18;
    private static final int VALID_AGE = 23;
    private static final String ZERO_CHARACTERS_STRING = "";
    private static final String FOUR_CHARACTERS_STRING = "1234";
    private static final String FIVE_CHARACTERS_STRING = "12345";
    private static final String SIX_CHARACTERS_STRING = "123456";
    private static final String EIGHT_CHARACTERS_STRING = "12345678";

    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        DEFAULT_VALID_USER.setAge(MINIMUM_VALID_AGE);
        DEFAULT_VALID_USER.setPassword(SIX_CHARACTERS_STRING);
        DEFAULT_VALID_USER.setLogin(EIGHT_CHARACTERS_STRING);
        SECOND_VALID_USER.setAge(VALID_AGE);
        SECOND_VALID_USER.setPassword(EIGHT_CHARACTERS_STRING);
        SECOND_VALID_USER.setLogin(SIX_CHARACTERS_STRING);
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        DEFAULT_VALID_USER.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_invalidAge_notOk() {
        DEFAULT_VALID_USER.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setAge(ZERO_AGE);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setAge(UNDERAGE_AGE);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_validAge_Ok() {
        DEFAULT_VALID_USER.setAge(MINIMUM_VALID_AGE);
        assertEquals(DEFAULT_VALID_USER, service.register(DEFAULT_VALID_USER));
        SECOND_VALID_USER.setAge(VALID_AGE);
        assertEquals(SECOND_VALID_USER, service.register(SECOND_VALID_USER));
    }

    @Test
    void register_nullLogin_notOk() {
        DEFAULT_VALID_USER.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_invalidLogin_notOk() {
        DEFAULT_VALID_USER.setLogin(FOUR_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setLogin(ZERO_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setLogin(FIVE_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_validLogin_Ok() {
        DEFAULT_VALID_USER.setLogin(EIGHT_CHARACTERS_STRING);
        assertEquals(DEFAULT_VALID_USER, service.register(DEFAULT_VALID_USER));
        SECOND_VALID_USER.setLogin(SIX_CHARACTERS_STRING);
        assertEquals(SECOND_VALID_USER, service.register(SECOND_VALID_USER));
    }

    @Test
    void register_nullPassword_notOk() {
        DEFAULT_VALID_USER.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_invalidPassword_notOk() {
        DEFAULT_VALID_USER.setPassword(FOUR_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setPassword(ZERO_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
        DEFAULT_VALID_USER.setPassword(FIVE_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }

    @Test
    void register_validPassword_Ok() {
        DEFAULT_VALID_USER.setPassword(EIGHT_CHARACTERS_STRING);
        assertEquals(DEFAULT_VALID_USER, service.register(DEFAULT_VALID_USER));
        SECOND_VALID_USER.setPassword(SIX_CHARACTERS_STRING);
        assertEquals(SECOND_VALID_USER, service.register(SECOND_VALID_USER));
    }

    @Test
    void register_DuplicateLogin_notOk() {
        Storage.people.add(DEFAULT_VALID_USER);
        assertThrows(RegistrationException.class, () -> service.register(DEFAULT_VALID_USER));
    }
}
