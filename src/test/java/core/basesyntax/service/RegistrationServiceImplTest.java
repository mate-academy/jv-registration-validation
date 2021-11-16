package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        user = new User();
        user.setLogin("qwerty");
        user.setPassword("asdfgh");
        user.setAge(25);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginStartWithEmptyCharacter_notOk() {
        user.setLogin(" acascd");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginContainEmptyCharacter_notOk() {
        user.setLogin("ac   ascd");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordContainEmptyCharacter_notOk() {
        user.setPassword("ac   ascd");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("zxcvu");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_smallAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_firstUserRegistration_Ok(){
        User firstActualUser = registrationService.register(user);
        int expected = 1;
        int actual = Storage.people.size();
        assertEquals(expected, actual);
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User firstActualUser = registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstActualUser));
    }
}
