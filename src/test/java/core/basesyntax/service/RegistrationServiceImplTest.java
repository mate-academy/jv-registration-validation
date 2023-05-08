package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void containsValidUser_Ok() {
        User kate = new User();
        kate.setAge(40);
        kate.setLogin("1234566666");
        kate.setPassword("43434343");
        User expected = kate;
        User actual = registrationService.register(kate);
        assertEquals(expected, actual);
    }

    @Test
    void userIsAlreadyInStorage_NotOk() {
        User bob = new User();
        bob.setAge(22);
        bob.setLogin("1234567");
        bob.setPassword("21212121");

        Storage.people.add(bob);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(bob);
        });
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(ValidationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void loginIsMoreThan6Letters_NotOk() {
        User john = new User();
        john.setAge(30);
        john.setLogin("1");
        john.setPassword("4343");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(john);
        });
    }

    @Test
    void loginIsMoreThan6Letters_Ok() {
        User alice = new User();
        alice.setAge(32);
        alice.setLogin("1234567890");
        alice.setPassword("32323232");
        int expected = 10;
        int actual = alice.getLogin().length();
        assertEquals(expected, actual);
    }

    @Test
    void passwordIsMoreThan6Letters_NotOk() {
        User jane = new User();
        jane.setAge(45);
        jane.setLogin("1234567890");
        jane.setPassword("1");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(jane);
        });
    }

    @Test
    void passwordIsMoreThan6Letters_Ok() {
        User tim = new User();
        tim.setAge(25);
        tim.setLogin("1234567890");
        tim.setPassword("11111111");
        int expected = 8;
        int actual = registrationService.register(tim).getPassword().length();
        assertEquals(expected, actual);
    }

    @Test
    void userAge_Ok() {
        User ann = new User();
        ann.setAge(18);
        ann.setLogin("1234567890");
        ann.setPassword("11111111111");
        int expected = 18;
        int actual = ann.getAge();
        assertEquals(expected, actual);
    }

    @Test
    void userAge_NotOk() {
        User tanya = new User();
        tanya.setAge(10);
        tanya.setLogin("123456789");
        tanya.setPassword("111111111");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(tanya);
        });
    }

    @Test
    void passwordNullCheck_NotOk() {
        User mike = new User();
        mike.setAge(80);
        mike.setLogin("1200000");
        mike.setPassword(null);
        assertThrows(ValidationException.class, () -> {
            registrationService.register(mike);
        });
    }

    @Test
    void loginNullCheck_NotOk() {
        User dan = new User();
        dan.setAge(50);
        dan.setLogin(null);
        dan.setPassword("09090909");
        assertThrows(ValidationException.class, () -> {
            registrationService.register(dan);
        });
    }
}
