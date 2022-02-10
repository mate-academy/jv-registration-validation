package core.basesyntax;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl(new StorageDaoImpl());
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword("passwordTest123");
        user.setLogin("testLogin");
        user.setAge(21);
    }

    @Test
    void userAdd_OK() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registerUserIsNull_notOK() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void registerUserLoginIsNull_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerIncorrectPassword_notOK() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPasswordIsEmpty_notOK() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPasswordNull_notOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("Should return throws when user age is less than 18")
    void register_lessAge_notOK() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("Should throw exception when user exists")
    void registerUserAlreadyExists_notOK() {
        User userValid = new User();
        userValid.setLogin("testLogin1");
        userValid.setAge(22);
        userValid.setId(1L);
        userValid.setPassword("passwordTest1234");
        people.add(userValid);
        assertThrows(RuntimeException.class, () -> registrationService.register(userValid));
    }

    @AfterEach
    void clear() {
        people.clear();
    }
}
