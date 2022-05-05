package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASS_LENGTH = 6;
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;
    private Long id = 0L;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_nullUser_RunTimeException() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "Method should throw RunTimeException for null user!");
    }

    @Test
    void register_nullUserLogin_RunTimeException() {
        initUser(user, id++, null, "password", MINIMAL_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with null login!");
    }

    @Test
    void register_emptyUserLogin_RunTimeException() {
        initUser(user, id++, "", "password", MINIMAL_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with empty login!");
    }

    @Test
    void register_nullUserAge_RunTimeException() {
        initUser(user, id++, "User" + id, "password", MINIMAL_AGE);
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with null age!");
    }

    @Test
    void register_negativeUserAge_RunTimeException() {
        initUser(user,id++, "User" + id, "password", -20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with negative age!");
    }

    @Test
    void register_userAgeLessMinAge_RunTimeException() {
        initUser(user,id++, "User" + id, "password", MINIMAL_AGE - 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with age less " + MINIMAL_AGE + "!");
    }

    @Test
    void register_nullUserPass_RunTimeException() {
        initUser(user, id++, "User" + id, null, MINIMAL_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with null password");
    }

    @Test
    void register_UserPassLengthLessMinPassLength_RunTimeException() {
        initUser(user, id++, "User" + id, "12345", MINIMAL_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException for user with password's length less "
                        + MINIMAL_PASS_LENGTH + "!");
    }

    @Test
    void register_userLoginAlreadyExist_RunTimeException() {
        initUser(user, id++, "User", "password", MINIMAL_AGE);
        assertEquals(user, registrationService.register(user),
                "Method should add valid user to Storage!");
        initUser(user,id++, "User", "password", MINIMAL_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "Method should throw RunTimeException "
                        + "for user with already existing login in Storage!");
    }

    @Test
    void register_userValidData_Ok() {
        initUser(user, id++, "User" + id, "password", MINIMAL_AGE);
        assertEquals(user, registrationService.register(user),
                "Method should add valid user to Storage!");
    }

    private void initUser(User user, Long id, String login, String pass, int age) {
        user.setId(id);
        user.setLogin(login);
        user.setPassword(pass);
        user.setAge(age);
    }
}
