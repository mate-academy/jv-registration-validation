package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_valid_ok() {
        User validUser = new User();
        validUser.setLogin("validUser");
        validUser.setPassword("strongPassword");
        validUser.setAge(23);

        assertDoesNotThrow(() -> registrationService.register(validUser));
    }

    @Test
    void register_existingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("firstUser");
        firstUser.setPassword("password");
        firstUser.setAge(20);
        registrationService.register(firstUser);

        User secondUser = new User();
        secondUser.setLogin("firstUser");
        secondUser.setPassword("password2");
        secondUser.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void register_nullUser_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("pass");
        user.setAge(53);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("valisUser");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("");
        firstUser.setAge(20);

        User secondUser = new User();
        secondUser.setLogin("validUser2");
        secondUser.setPassword("abc");
        secondUser.setAge(20);

        User thirdUser = new User();
        thirdUser.setLogin("validUser3");
        thirdUser.setPassword("qwert");
        thirdUser.setAge(21);

        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
        assertThrows(RegistrationException.class, () -> registrationService.register(thirdUser));
    }

    @Test
    void register_validPassword_ok() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("qwerty");
        firstUser.setAge(20);

        User secondUser = new User();
        secondUser.setLogin("validUser2");
        secondUser.setPassword("password");
        secondUser.setAge(20);

        assertDoesNotThrow(() -> registrationService.register(firstUser));
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_ageUnder18_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(15);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }


}