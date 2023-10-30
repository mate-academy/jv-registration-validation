package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user_1;
    private User user_2;
    private User user_3;
    private User user_4;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        user_1 = new User();
        user_1.setAge(18);
        user_1.setLogin("username1");
        user_1.setPassword("123456");

        user_2 = new User();
        user_2.setAge(18);
        user_2.setLogin("username2");
        user_2.setPassword("123456");

        user_3 = new User();
        user_3.setAge(18);
        user_3.setLogin("username3");
        user_3.setPassword("123456");

        user_4 = new User();
        user_4.setAge(18);
        user_4.setLogin("username4");
        user_4.setPassword("123456");
    }

    @Test
    void register_nullAge_notOk() {
        user_1.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_negativeAge_notOk() {
        user_1.setAge(Integer.MIN_VALUE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setAge(-10);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setAge(-1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_insufficientAge_notOk() {
        user_1.setAge(0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setAge(10);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_sufficientAge_Ok() throws InvalidDataException {
        assertEquals(user_1, registrationService.register(user_1));
        user_2.setAge(19);
        assertEquals(user_2, registrationService.register(user_2));
        user_3.setAge(100);
        assertEquals(user_3, registrationService.register(user_3));
        user_4.setAge(Integer.MAX_VALUE);
        assertEquals(user_4, registrationService.register(user_4));
    }

    @Test
    void register_nullLogin_notOk() {
        user_1.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_smallLogin_notOk() {
        user_1.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setLogin(" ");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setLogin("q");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setLogin("qwe");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setLogin("qwert");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_normalLogin_Ok() throws InvalidDataException {
        assertEquals(user_1, registrationService.register(user_1));
        user_2.setLogin("login10");
        assertEquals(user_2, registrationService.register(user_2));
        user_3.setLogin("userloginuserloginuserlogin");
        assertEquals(user_3, registrationService.register(user_3));
    }

    @Test
    void register_nullPassword_notOk() {
        user_1.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_smallPassword_notOk() {
        user_1.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setPassword(" ");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setPassword("1");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setPassword("123");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
        user_1.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user_1));
    }

    @Test
    void register_normalPassword_Ok() throws InvalidDataException {
        assertEquals(user_1, registrationService.register(user_1));
        user_2.setPassword("1234567");
        assertEquals(user_2, registrationService.register(user_2));
        user_3.setPassword("1234567891011121314151617");
        assertEquals(user_3, registrationService.register(user_3));
    }

    @Test
    void register_addingExistingUser_notOk() throws InvalidDataException {
        registrationService.register(user_1);
        assertNull(registrationService.register(user_1));
    }

    @Test
    void register_addingSameUser_notOk() throws InvalidDataException {
        registrationService.register(user_1);
        user_2.setLogin(user_1.getLogin());
        assertNull(registrationService.register(user_2));
    }

    @Test
    void registration_addingUniqueUsers_Ok() throws InvalidDataException {
        assertEquals(user_1, registrationService.register(user_1));
        assertEquals(user_2, registrationService.register(user_2));
        assertEquals(user_3, registrationService.register(user_3));
        assertEquals(user_4, registrationService.register(user_4));
    }
}