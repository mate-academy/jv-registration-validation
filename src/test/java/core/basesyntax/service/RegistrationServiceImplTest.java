package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("UserLogin");
        user.setPassword("qwerty");
    }

    @Test
    void register_userIsValid_ok() {
        User actual = registrationService.register(user);
        assertEquals(user,actual);
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_withExistLogin_notOk() {
        User user1 = new User();
        user1.setLogin("mate");
        user1.setPassword("password");
        user1.setAge(19);
        registrationService.register(user1);
        User user2 = new User();
        user2.setLogin("mate");
        user2.setPassword("password");
        user2.setAge(30);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user2));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_ageUnder18_notOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_ageAllowed_ok() {
        user.setAge(25);
        User actual = registrationService.register(user);
        assertEquals(user.getAge(), actual.getAge());
    }

    @Test
    void register_ageNegative_notOk() {
        user.setAge(-15);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordInvalid_notOk() {
        user.setPassword("11111");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
