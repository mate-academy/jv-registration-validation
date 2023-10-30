package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user1;
    private User user2;
    private User user3;
    private User user4;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();

        user1 = new User();
        user1.setAge(18);
        user1.setLogin("username1");
        user1.setPassword("123456");

        user2 = new User();
        user2.setAge(18);
        user2.setLogin("username2");
        user2.setPassword("123456");

        user3 = new User();
        user3.setAge(18);
        user3.setLogin("username3");
        user3.setPassword("123456");

        user4 = new User();
        user4.setAge(18);
        user4.setLogin("username4");
        user4.setPassword("123456");
    }

    @Test
    void register_nullAge_notOk() {
        user1.setAge(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_negativeAge_notOk() {
        user1.setAge(Integer.MIN_VALUE);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setAge(-10);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setAge(-1);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_insufficientAge_notOk() {
        user1.setAge(0);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setAge(10);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setAge(17);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_sufficientAge_Ok() throws InvalidDataException {
        Assertions.assertEquals(user1, registrationService.register(user1));
        user2.setAge(19);
        Assertions.assertEquals(user2, registrationService.register(user2));
        user3.setAge(100);
        Assertions.assertEquals(user3, registrationService.register(user3));
        user4.setAge(Integer.MAX_VALUE);
        Assertions.assertEquals(user4, registrationService.register(user4));
    }

    @Test
    void register_nullLogin_notOk() {
        user1.setLogin(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_smallLogin_notOk() {
        user1.setLogin("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setLogin(" ");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setLogin("q");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setLogin("qwe");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setLogin("qwert");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_normalLogin_Ok() throws InvalidDataException {
        Assertions.assertEquals(user1, registrationService.register(user1));
        user2.setLogin("login10");
        Assertions.assertEquals(user2, registrationService.register(user2));
        user3.setLogin("userloginuserloginuserlogin");
        Assertions.assertEquals(user3, registrationService.register(user3));
    }

    @Test
    void register_nullPassword_notOk() {
        user1.setPassword(null);
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_smallPassword_notOk() {
        user1.setPassword("");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setPassword(" ");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setPassword("1");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setPassword("123");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
        user1.setPassword("12345");
        Assertions.assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1));
    }

    @Test
    void register_normalPassword_Ok() throws InvalidDataException {
        Assertions.assertEquals(user1, registrationService.register(user1));
        user2.setPassword("1234567");
        Assertions.assertEquals(user2, registrationService.register(user2));
        user3.setPassword("1234567891011121314151617");
        Assertions.assertEquals(user3, registrationService.register(user3));
    }

    @Test
    void register_addingExistingUser_notOk() throws InvalidDataException {
        registrationService.register(user1);
        Assertions.assertNull(registrationService.register(user1));
    }

    @Test
    void register_addingSameUser_notOk() throws InvalidDataException {
        registrationService.register(user1);
        user2.setLogin(user1.getLogin());
        Assertions.assertNull(registrationService.register(user2));
    }

    @Test
    void registration_addingUniqueUsers_Ok() throws InvalidDataException {
        Assertions.assertEquals(user1, registrationService.register(user1));
        Assertions.assertEquals(user2, registrationService.register(user2));
        Assertions.assertEquals(user3, registrationService.register(user3));
        Assertions.assertEquals(user4, registrationService.register(user4));
    }
}
