package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User firstUser = new User();
    private static final User secondUser = new User();
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
        firstUser.setAge(MINIMUM_VALID_AGE);
        firstUser.setPassword(SIX_CHARACTERS_STRING);
        firstUser.setLogin(EIGHT_CHARACTERS_STRING);
        secondUser.setAge(VALID_AGE);
        secondUser.setPassword(EIGHT_CHARACTERS_STRING);
        secondUser.setLogin(SIX_CHARACTERS_STRING);
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }

    @Test
    void register_invalidAge_notOk() {
        firstUser.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
        firstUser.setAge(ZERO_AGE);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
        firstUser.setAge(UNDERAGE_AGE);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }

    @Test
    void register_validAge_Ok() {
        firstUser.setAge(MINIMUM_VALID_AGE);
        assertEquals(firstUser, service.register(firstUser));
        secondUser.setAge(VALID_AGE);
        assertEquals(secondUser, service.register(secondUser));
    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        firstUser.setLogin(FOUR_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
        firstUser.setLogin(ZERO_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
        firstUser.setLogin(FIVE_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }

    @Test
    void register_validLogin_Ok() {
        firstUser.setLogin(EIGHT_CHARACTERS_STRING);
        assertEquals(firstUser, service.register(firstUser));
        secondUser.setLogin(SIX_CHARACTERS_STRING);
        assertEquals(secondUser, service.register(secondUser));
    }

    @Test
    void register_nullPassword_notOk() {
        firstUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        firstUser.setPassword(FOUR_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
        firstUser.setPassword(ZERO_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
        firstUser.setPassword(FIVE_CHARACTERS_STRING);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }

    @Test
    void register_validPassword_Ok() {
        firstUser.setPassword(EIGHT_CHARACTERS_STRING);
        assertEquals(firstUser, service.register(firstUser));
        secondUser.setPassword(SIX_CHARACTERS_STRING);
        assertEquals(secondUser, service.register(secondUser));
    }

    @Test
    void register_DuplicateLogin_notOk() {
        Storage.people.add(firstUser);
        assertThrows(RegistrationException.class, () -> service.register(firstUser));
    }
}
