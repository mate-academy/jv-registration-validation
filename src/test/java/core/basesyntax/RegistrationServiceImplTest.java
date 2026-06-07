package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        this.registrationService = new RegistrationServiceImpl();
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin("mylogin");
        user.setPassword("123456");
        user.setAge(18);
        return user;
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(null)
        );
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("login");

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createValidUser();
        user.setPassword("123");

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_underageUser_notOk() {
        User user = createValidUser();
        user.setAge(17);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_duplicateLogin_notOk() {
        User firstUser = createValidUser();
        User secondUser = createValidUser();

        registrationService.register(firstUser);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(secondUser)
        );
    }

    @Test
    void register_fiveCharactersLogin_notOk() {
        User user = createValidUser();
        user.setLogin("login");

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = createValidUser();
        user.setLogin("");

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_fiveCharactersPassword_notOk() {
        User user = createValidUser();
        user.setPassword("12345");

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = createValidUser();
        user.setPassword("");

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createValidUser();
        user.setAge(-1);

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_ageExactly18_ok() {
        User user = createValidUser();
        user.setAge(18);

        registrationService.register(user);
    }

    @Test
    void register_user_Ok() {
        User user = createValidUser();

        registrationService.register(user);
    }
}
