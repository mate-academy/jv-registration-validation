package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User firstUserOk;
    private static User secondUserOk;
    private static User thirdUserOk;

    @BeforeAll
    public static void beforeClass() {
        registrationService = new RegistrationServiceImpl();
        firstUserOk = new User("Bob", "qwerty", 18);
        secondUserOk = new User("Alice", "asdfghjk", 29);
        thirdUserOk = new User("Jhon", "zxcvbnm", 25);
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
    }

    @Test
    public void register_nullLogin_notOk() {
        User userNullLoginNotOk = new User(null, "asdfghjk", 23);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(userNullLoginNotOk));
    }

    @Test
    public void register_emptyLogin_notOk() {
        User userEmptyLoginNotOk = new User( "", "asdfghjk", 23);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(userEmptyLoginNotOk));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_passwordLessThanMinLength_notOk() {
        User userPasswordLessThanMinLengthNotOk = new User("BadBob", "qwer5", 19);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(userPasswordLessThanMinLengthNotOk));
    }

    @Test
    public void register_nullPassword_notOk() {
        User userNullPasswordNotOk = new User("BadBob", null, 19);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(userNullPasswordNotOk));
    }

    @Test
    public void register_ageLessThanMinAge_notOk() {
        User userAgeLessThanMinAgeNotOk = new User("BadAlice", "asdfghjk", 17);
        assertThrows(RuntimeException.class, ()
                -> registrationService.register(userAgeLessThanMinAgeNotOk));
    }

    @Test
    public void register_nullAge_notOk() {
        User userNullAgeNotOk = new User("BadBob", "qwer789", null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userNullAgeNotOk));
    }

    @Test
    public void register_secondUserExists_notOk() {
        registrationService.register(firstUserOk);
        registrationService.register(secondUserOk);
        registrationService.register(thirdUserOk);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUserOk));
    }

    @Test
    public void register_threeUsers_Ok() {
        int expect = 3;
        registrationService.register(firstUserOk);
        registrationService.register(secondUserOk);
        registrationService.register(thirdUserOk);
        int actual = Storage.people.size();
        assertEquals(expect, actual);
    }
}
