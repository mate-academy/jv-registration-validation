package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String ACCEPTABLE_LOGIN = "SimpleLogin";
    private static final String EMPTY_STRING = "";
    private static final String TWO_CHARACTER_STRING = "Hi";
    private static final String FIVE_CHARACTER_STRING = "Hello";
    private static final int NEGATIV_AGE = -20;
    private static final int ZERO_AGE = 0;
    private static final int POSITIV_UNACCEPTABLE_AGE = 17;
    private RegistrationService registrationService;
    private List<User> users = new ArrayList<>();
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user1 = new User("Undead", "password", 18);
        user2 = new User("BlackDog", "9omwc8,ry", 30);
        user3 = new User("RainingMan", "dcfxr 9iuert", 29);
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_addAcceptableUser_ok() {
        for (User user : users) {
            Storage.people.add(user);
        }
        assertTrue(Storage.people.contains(user1));
        assertTrue(Storage.people.contains(user2));
        assertTrue(Storage.people.contains(user3));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_userAgeUnacceptable_notOk() {
        User userBadAge = new User(ACCEPTABLE_LOGIN, "password", NEGATIV_AGE);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(userBadAge);
        });
        userBadAge.setAge(ZERO_AGE);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(userBadAge);
        });
        userBadAge.setAge(POSITIV_UNACCEPTABLE_AGE);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(userBadAge);
        });
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_addUserWithSameLogin_notOk() {
        Storage.people.add(user1);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(user1);
        });
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_addUserWithBadPassword_notOk() {
        User badPasswordUser = new User(ACCEPTABLE_LOGIN, EMPTY_STRING, 20);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(badPasswordUser);
        });
        badPasswordUser.setPassword(TWO_CHARACTER_STRING);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(badPasswordUser);
        });
        badPasswordUser.setPassword(FIVE_CHARACTER_STRING);
        assertThrows(UnacceptableUserExeption.class, () -> {
            registrationService.register(badPasswordUser);
        });
        assertEquals(0, Storage.people.size());
    }

}
