package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User firstUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        firstUser.setId(1L);
    }

    @Test
    void register_nullUser_notOk() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("User value can't be Null");
    }

    @Test
    void register_nullAge_notOk() {
        try {
            firstUser.setPassword("1234567");
            firstUser.setLogin("testUser1");
            firstUser.setAge(null);
            registrationService.register(firstUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("User age can't be Null");
    }

    @Test
    void register_nullLogin_notOk() {
        try {
            firstUser.setPassword("1234567");
            firstUser.setLogin(null);
            firstUser.setAge(19);
            registrationService.register(firstUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("User login can't be Null");
    }

    @Test
    void register_nullPassword_notOk() {
        try {
            firstUser.setPassword(null);
            firstUser.setLogin("User");
            firstUser.setAge(19);
            registrationService.register(firstUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("User password can't be Null");
    }

    @Test
    void register_less18Age_notOk() {
        firstUser.setPassword("12345678");
        firstUser.setLogin("User");
        firstUser.setAge(17);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_less6PassLength_notOk() {
        firstUser.setPassword("12345");
        firstUser.setLogin("User");
        firstUser.setAge(20);

        assertThrows(RuntimeException.class, () -> {
            registrationService.register(firstUser);
        });
    }

    @Test
    void register_issetLogin_notOk() {
        firstUser.setPassword("12345678");
        firstUser.setLogin("User1");
        firstUser.setAge(20);
        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setAge(35);
        secondUser.setPassword("qawsedrftg");
        secondUser.setLogin("User1");
        registrationService.register(firstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_User_Ok() {
        firstUser.setPassword("12345678");
        firstUser.setLogin("FirstUser");
        firstUser.setAge(20);
        User returnUser = registrationService.register(firstUser);
        assertEquals(firstUser, returnUser, "User not added");
        System.out.println("User successfully added");
    }
}
