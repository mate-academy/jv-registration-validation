package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

////pull request check
class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User expectedUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        expectedUser = new User();
    }

    @Test
    void register_loginIsNull_notOk() {
        expectedUser.setLogin(null);
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }
    
    @Test
    void register_duplicateLogin_notOk() {
        expectedUser.setLogin("the_same_login");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        registrationService.register(expectedUser);

        User user2 = new User();
        user2.setLogin("the_same_login");
        user2.setPassword("placeholder_text");
        user2.setAge(18);

        assertThrows(InvalidDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_loginLength0_notOk() {
        expectedUser.setLogin("");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_loginLength3_notOk() {
        expectedUser.setLogin("pla");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_loginLength5_notOk() {
        expectedUser.setLogin("place");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_loginLength6_ok() {
        expectedUser.setLogin("placeh");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_loginLength16_ok() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_passwordIsNull_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword(null);
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_passwordLength0_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_passwordLength3_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("pla");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_passwordLength5_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("place");
        expectedUser.setAge(18);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_passwordLength6_ok() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeh");
        expectedUser.setAge(18);
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_passwordLength16_ok() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_ageIsNull_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_negativeAge_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(-5);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_howOld0_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_howOld5_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(5);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_howOld17_notOk() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(expectedUser));
    }

    @Test
    void register_howOld18_ok() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(18);
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }

    @Test
    void register_howOld25_ok() {
        expectedUser.setLogin("placeholder_text");
        expectedUser.setPassword("placeholder_text");
        expectedUser.setAge(25);
        User actual = registrationService.register(expectedUser);
        assertEquals(expectedUser, actual);
    }
}
