package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_addAcceptableUser_ok() {
        User user1 = new User("Undead", "sc9opj8kum", 18);
        User user2 = new User("Linuxgramm", "IKnowYourName", 43);
        User user3 = new User("Applepie", "Yellow", 23);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertTrue(Storage.people.contains(user1));
        assertTrue(Storage.people.contains(user2));
        assertTrue(Storage.people.contains(user3));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_userAgeUnacceptable_notOk() {
        User userBadAge = new User("Guardian", "password", -20);
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(userBadAge);
        });
        userBadAge.setAge(0);
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(userBadAge);
        });
        userBadAge.setAge(17);
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(userBadAge);
        });
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_addUserWithSameLogin_notOk() {
        User user1 = new User("Sourcefire", "Password", 20);
        Storage.people.add(user1);
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_addUserWithUnacceptableLogin_notOk() {
        User badLoginUser = new User(null, "password", 20);
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badLoginUser);
        });
        badLoginUser.setLogin("");
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badLoginUser);
        });
        badLoginUser.setLogin("not");
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badLoginUser);
        });
        badLoginUser.setLogin("Five5");
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badLoginUser);
        });
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_addUserWithBadPassword_notOk() {
        User badPasswordUser = new User("Sourcefire", null, 20);
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badPasswordUser);
        });
        badPasswordUser.setPassword("");
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badPasswordUser);
        });
        badPasswordUser.setPassword("Two");
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badPasswordUser);
        });
        badPasswordUser.setPassword("Five5");
        assertThrows(IncorrectUserDataException.class, () -> {
            registrationService.register(badPasswordUser);
        });
        assertEquals(0, Storage.people.size());
    }

}
