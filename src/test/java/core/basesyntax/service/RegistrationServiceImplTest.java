package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user = new User();
    }

    @Test
    void register_newUser_Ok() {
        user.setAge(19);
        user.setLogin("JohnCoolBoyyyy");
        user.setPassword("val1dPassw0rd11");
        assertEquals(user, service.register(user));
    }

    @Test
    void register_userExist_notOk() {
        user.setLogin("validUserName");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setAge(20);
        user.setPassword("val1dPassw0rd22");

        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setLogin("n");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setLogin("na");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setLogin("nam");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setLogin("name");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setLogin("name1");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_validLogin_Ok() {
        user.setAge(20);
        user.setPassword("password11");
        user.setLogin("MeowMeowBigDog");
        assertEquals(user, service.register(user));
        user = new User();
        user.setAge(20);
        user.setPassword("password22");
        user.setLogin("111111111111111114");
        assertEquals(user, service.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setAge(20);
        user.setLogin("HamzahCoolBoy");

        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setPassword("p");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setPassword("pa");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setPassword("pas");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setPassword("pass");
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setPassword("passw");
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_validPassword_Ok() {
        user.setAge(20);
        user.setPassword("password44");
        user.setLogin("IAmLegend");
        assertEquals(user, service.register(user));

        user = new User();
        user.setAge(20);
        user.setPassword("paswordinjo1231");
        user.setLogin("GangstaBoy123");
        assertEquals(user, service.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setPassword("password6667");
        user.setLogin("abcdef");

        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setAge(0);
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> service.register(user));

        user.setAge(5);
        assertThrows(InvalidDataException.class, () -> service.register(user));
    }

    @Test
    void register_validAge_Ok() {
        user.setLogin("HamzahAbdullHafiz");
        user.setPassword("password2511");

        user.setAge(18);
        assertEquals(user, service.register(user));

        user = new User();
        user.setPassword("password2135");
        user.setLogin("IAmAWolf");
        user.setAge(150);
        assertEquals(user, service.register(user));
    }
}
