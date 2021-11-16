package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    static RegistrationService registrationService;
    static User normalUser1;
    static User normalUser2;
    static User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        normalUser1 = new User();
        normalUser1.setLogin("Vasya");
        normalUser1.setAge(19);
        normalUser1.setPassword("abracadabra");
        normalUser2 = new User();
        normalUser2.setLogin("Kate");
        normalUser2.setAge(33);
        normalUser2.setPassword("MAGA2024");
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
        testUser.setPassword("qwer");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_underage_notOk() {
        testUser.setLogin("Slavik");
        testUser.setAge(15);
        testUser.setPassword("MyHornyPony");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(normalUser1);
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
        registrationService.register(normalUser1);
        testUser.setLogin(null);
        testUser.setAge(19);
        testUser.setPassword("BestPassword");
        assertThrows(RuntimeException.class, () ->
            registrationService.register(testUser));
    }

    @Test
    void register_normalCase_ok() {
        registrationService.register(normalUser1);
        registrationService.register(normalUser2);
        assertTrue(Storage.people.contains(normalUser1));
        assertEquals(normalUser1, Storage.people.get(0));
        assertTrue(Storage.people.contains(normalUser2));
        assertEquals(normalUser2, Storage.people.get(1));
    }
}
