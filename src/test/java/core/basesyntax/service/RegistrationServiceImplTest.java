package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static User userBob;
    private User user;
    private static RegistrationServiceImpl registrationService;


    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        userBob = new User("Bob", "SomeLength", 22);
    }

    @BeforeEach
    void setUp() {
        user = new User("Alice", "Johnson", 63);
    }

    @Test
    public void checkUser_isNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null)
        );
    }

    @Test
    public void checkLogin_isNull_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    public void checkLogin_isDuplicate_notOk() {
        user.setLogin(userBob.getLogin());
        registrationService.register(user);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    public void checkLogin_isOk() {
        user.setLogin("SomeSmartName");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    public void checkPassword_isNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
            registrationService.register(user)
        );
    }

    @Test
    public void checkPassword_isShort_notOk() {
        user.setPassword("three");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    public void checkPassword_minimumLength_isOk() {
        user.setPassword("123456");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    public void checkAge_isNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    public void checkAge_lowerThanMinimum_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user)
        );
    }

    @Test
    public void checkAge_minimumValue_isOk() {
        user.setAge(18);
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
