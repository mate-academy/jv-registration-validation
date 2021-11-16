package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationServiceImplTest {
    static RegistrationService registrationService;
    static User firstUser;
    static User secondUser;
    static User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        firstUser.setLogin("Vasya");
        firstUser.setAge(19);
        firstUser.setPassword("abracadabra");
        secondUser = new User();
        secondUser.setLogin("Kate");
        secondUser.setAge(33);
        secondUser.setPassword("MAGA2024");
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        testUser = new User();
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setLogin("Fedir");
        testUser.setAge(null);
        testUser.setPassword("OctoPussy");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_nullUser_notOk() {
        testUser = null;
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_shortPassword_notOk() {
        testUser.setLogin("Anna");
        testUser.setAge(19);
        testUser.setPassword("qwert");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_underAge_notOk() {
        testUser.setLogin("Slavik");
        testUser.setAge(17);
        testUser.setPassword("MyHornyPony");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(firstUser);
        testUser.setLogin("Vasya");
        testUser.setAge(33);
        testUser.setPassword("JoshuaAshfieldMegaCool");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setLogin("Alissa");
        testUser.setAge(35);
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        registrationService.register(firstUser);
        testUser.setLogin(null);
        testUser.setAge(19);
        testUser.setPassword("BestPassword");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_normalCase_ok() {
        User actualFirst = registrationService.register(firstUser);
        User actualSecond = registrationService.register(secondUser);
        assertEquals(firstUser, actualFirst);
        assertEquals(secondUser, actualSecond);
    }
}
