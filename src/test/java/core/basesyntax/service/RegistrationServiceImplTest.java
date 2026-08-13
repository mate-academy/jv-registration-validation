package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("validPassword");
        user.setAge(21);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
        assertEquals(actual, Storage.people.get(0));
        assertNotNull(actual.getId());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(null)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("valid01");
        user.setAge(21);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("user0");
        user.setPassword("valid01");
        user.setAge(21);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_sixCharactersLogin_ok() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid01");
        user.setAge(21);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_existingLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("user01");
        firstUser.setPassword("valid1");
        firstUser.setAge(21);

        registrationService.register(firstUser);

        User secondUser = new User();
        secondUser.setLogin("user01");
        secondUser.setPassword("valid2");
        secondUser.setAge(31);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(secondUser)
        );

        assertEquals(1, Storage.people.size());
        assertEquals(firstUser, Storage.people.get(0));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword(null);
        user.setAge(21);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid");
        user.setAge(21);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_sixCharactersPassword_ok() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid1");
        user.setAge(21);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid01");
        user.setAge(null);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid01");
        user.setAge(17);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid1");
        user.setAge(-1);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );

        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("");
        user.setAge(21);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );

        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_threeCharactersPassword_notOk() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("abc");
        user.setAge(21);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );

        assertEquals(0, Storage.people.size());
    }

    @Test
    void register_eighteenYearsOld_ok() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("valid01");
        user.setAge(18);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_eightCharactersPassword_ok() {
        User user = new User();
        user.setLogin("user01");
        user.setPassword("password"); // 8 символів
        user.setAge(21);

        User actual = registrationService.register(user);

        assertEquals(user, actual);
        assertEquals(1, Storage.people.size());
    }
}
