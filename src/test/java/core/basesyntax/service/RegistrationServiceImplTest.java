package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @BeforeEach
    void getDefaultUser() {
        user.setLogin("email@gmail.com");
        user.setAge(21);
        user.setPassword("Password21");
    }

    @BeforeEach
    void getClearList() {
        Storage.people.clear();
    }

    @Test
    void registerAddingNormalUser_ok() {
        Class expected = User.class;
        Class actual = registrationService.register(user).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void registerUserYoungerThan18_ok() {
        user.setAge(17);
        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerDuplicateUsers_notOk() {
        user.setLogin("cece@vrvrrv.com");
        user.setAge(21);
        user.setPassword("Password123");
        User duplicate = new User();
        duplicate.setLogin("cece@vrvrrv.com");
        duplicate.setAge(21);
        duplicate.setPassword("Password123");
        Storage.people.add(user);
        assertThrows(InvalidDataException.class, () -> registrationService.register(duplicate));
    }

    @Test
    void registerShortLogin_notOk() {
        user.setLogin("rr@.r");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void registerShortPassword_notOk() {
        user.setPassword("Passs");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword(" ");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserLoginWithoutDogInLogin_notOk() {
        user.setLogin("Pjeirtnbegirlngreio");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserPasswordWithoutSpecialSymbols_notOk() {
        user.setPassword("rhghPewghreyu");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserPasswordWithoutUpperCaseLetter_notOk() {
        user.setPassword("rhghPewghreyu");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserLoginContainsSpace_notOk() {
        user.setLogin("email @vrfre.com");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserAge18_ok() {
        user.setAge(18);
        Class expected = User.class;
        Class actual = registrationService.register(user).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void registerUserLoginStartsWithSymbol_notOk() {
        user.setLogin("@vfevf@.gmail.com");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }
}
