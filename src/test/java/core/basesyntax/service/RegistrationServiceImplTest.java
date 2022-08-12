package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static User user1;
    private static User user2;
    private RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        user1 = new User();
        user2 = new User();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown when User is null");
    }

    @Test
    void register_validUser_Ok() {
        user1.setAge(25);
        user1.setLogin("Bob");
        user1.setPassword("rocket");
        user2.setAge(18);
        user2.setLogin("Elise");
        user2.setPassword("rocket1981");
        User actual = registrationService.register(user1);
        User expected = user1;
        assertTrue(expected.equals(actual));
        actual = registrationService.register(user2);
        expected = user2;
        assertTrue(expected.equals(actual));
    }

    @Test
    void register_existedUser_notOk() {
        user1.setAge(25);
        user1.setLogin("Bob");
        user1.setPassword("rocket");
        registrationService.register(user1);
        try {
            registrationService.register(user1);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown when User is exist");
    }

    @Test
    void register_nullAgeUser_notOk() {
        user1.setAge(null);
        user1.setLogin("Bob");
        user1.setPassword("rocket");
        try {
            registrationService.register(user1);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown when Age is null");
    }

    @Test
    void register_lowAgeUser_notOk() {
        user1.setAge(17);
        user1.setLogin("Bob");
        user1.setPassword("rocket");
        try {
            registrationService.register(user1);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown when age less then 18");
    }

    @Test
    void register_shortPasswordUser_notOk() {
        user1.setAge(35);
        user1.setLogin("Alice");
        user1.setPassword("apple");
        try {
            registrationService.register(user1);
        } catch (RuntimeException e) {
            return;
        }
        fail("RuntimeException should be thrown when password shorte then 6");
    }
}