package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        registrationService.register(null);
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("password");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("uniqueLogin");
        user1.setPassword("password");
        user1.setAge(20);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("uniqueLogin");
        user2.setPassword("password123");
        user2.setAge(21);
        registrationService.register(user2);
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test
    void register_simplePassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("simple");
        user.setAge(20);
        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("The password must be complex!",
                registrationException.getMessage());
    }

    @Test
    void register_ComplexPassword_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("password@/123");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassport");
        user.setAge(17);
        registrationService.register(user);
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassport");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
