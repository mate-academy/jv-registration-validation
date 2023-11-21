package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void registerUserNull_NotOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(null);
        }, "An exception should be thrown");

        User bobAgeNull = new User();
        bobAgeNull.setAge(null);
        bobAgeNull.setLogin("bobist");
        bobAgeNull.setPassword("hhjfbchbv");
        bobAgeNull.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bobAgeNull);
        }, "An exception should be thrown");

        User bobLoginNull = new User();
        bobLoginNull.setAge(19);
        bobLoginNull.setLogin(null);
        bobLoginNull.setPassword("hhjfbchbv");
        bobLoginNull.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bobLoginNull);
        }, "An exception should be thrown");

        User bobPasswordNull = new User();
        bobPasswordNull.setAge(19);
        bobPasswordNull.setLogin("bobist");
        bobPasswordNull.setPassword("");
        bobPasswordNull.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bobPasswordNull);
        }, "An exception should be thrown");

    }

    @Test
    void registerInvalidAge_NotOk() {
        User bob = new User();
        bob.setAge(10);
        bob.setLogin("bobist");
        bob.setPassword("hhjfbchbv");
        bob.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        User alice = new User();
        alice.setAge(15);
        alice.setLogin("aliceFox");
        alice.setPassword("hhjf562bv");
        alice.setId(36898L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(alice);
        }, "An exception should be thrown");
    }

    @Test
    void registerInvalidLogin_NotOk() {
        User john = new User();
        john.setAge(19);
        john.setLogin("bobist");
        john.setPassword("hhfjbcdyb");
        john.setId(1259L);
        registrationService.register(john);

        User bob = new User();
        bob.setAge(19);
        bob.setLogin("bobist");
        bob.setPassword("hiokfbchbv");
        bob.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");
    }

    @Test
    void registerLengthPassword_NotOk() {
        User bob = new User();
        bob.setAge(19);
        bob.setLogin("bobistik");
        bob.setPassword("hhjf");
        bob.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        User john = new User();
        john.setAge(19);
        john.setLogin("johnidd");
        john.setPassword("jo");
        john.setId(1259L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(john);
        }, "An exception should be thrown");
    }

    @Test
    void registerLengthLogin_NotOk() {
        User bob = new User();
        bob.setAge(19);
        bob.setLogin("bob");
        bob.setPassword("hhjfklk");
        bob.setId(52698L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(bob);
        }, "An exception should be thrown");

        User john = new User();
        john.setAge(19);
        john.setLogin("john");
        john.setPassword("jolop5963");
        john.setId(1259L);

        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(john);
        }, "An exception should be thrown");
    }
}
