package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final int VALID_AGE = 25;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        assertNotNull(actual.getId());
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_existingLogin_notOk() {
        Storage.people.add(createUser(VALID_LOGIN, "otherPassword", 30));
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        assertNotOk(createUser(null, VALID_PASSWORD, VALID_AGE));
    }

    @Test
    void register_shortLogin_notOk() {
        assertNotOk(createUser("", VALID_PASSWORD, VALID_AGE));
        assertNotOk(createUser("abc", VALID_PASSWORD, VALID_AGE));
        assertNotOk(createUser("abcde", VALID_PASSWORD, VALID_AGE));
    }

    @Test
    void register_minLengthLogin_ok() {
        assertOk(createUser("abcdef", VALID_PASSWORD, VALID_AGE));
        assertOk(createUser("abcdefgh", VALID_PASSWORD, VALID_AGE));
    }

    @Test
    void register_nullPassword_notOk() {
        assertNotOk(createUser(VALID_LOGIN, null, VALID_AGE));
    }

    @Test
    void register_shortPassword_notOk() {
        assertNotOk(createUser(VALID_LOGIN, "", VALID_AGE));
        assertNotOk(createUser(VALID_LOGIN, "abc", VALID_AGE));
        assertNotOk(createUser(VALID_LOGIN, "abcde", VALID_AGE));
    }

    @Test
    void register_minLengthPassword_ok() {
        assertOk(createUser("firstUser", "abcdef", VALID_AGE));
        assertOk(createUser("secondUser", "abcdefgh", VALID_AGE));
    }

    @Test
    void register_nullAge_notOk() {
        assertNotOk(createUser(VALID_LOGIN, VALID_PASSWORD, null));
    }

    @Test
    void register_underMinAge_notOk() {
        assertNotOk(createUser(VALID_LOGIN, VALID_PASSWORD, -5));
        assertNotOk(createUser(VALID_LOGIN, VALID_PASSWORD, 0));
        assertNotOk(createUser(VALID_LOGIN, VALID_PASSWORD, 17));
    }

    @Test
    void register_minAge_ok() {
        assertOk(createUser("firstUser", VALID_PASSWORD, 18));
        assertOk(createUser("secondUser", VALID_PASSWORD, 60));
    }

    private void assertOk(User user) {
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    private void assertNotOk(User user) {
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
