package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @BeforeEach
    void initDefaultUser() {
        user.setLogin("email@gmail.com");
        user.setAge(21);
        user.setPassword("Password21");
    }

    @AfterEach
    void clearList() {
        Storage.people.clear();
    }

    @Test
    void register_normalUser_ok() {
        User registered = registrationService.register(user);
        assertNotNull(registered.getId());
    }

    @Test
    void register_lowAge_notOk() {
        user.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setAge(-1);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicate_notOk() {
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
    void register_loginLengthMoreThan6_ok() {
        user.setLogin("rr@.r");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_NullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthMoreThan6_ok() {
        user.setPassword("Passs");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
        user.setPassword(" ");
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageMoreOrEquals18_ok() {
        user.setAge(18);
        Class expected = User.class;
        Class actual = registrationService.register(user).getClass();
        assertEquals(expected, actual);
    }
}
