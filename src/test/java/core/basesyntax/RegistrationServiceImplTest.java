package core.basesyntax;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
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
        user.setId(1L);
        user.setPassword("passwordTest123");
        user.setLogin("testLogin");
        user.setAge(21);
    }

    @Test
    void userAdd_OK() {
        assertNotNull(registrationService.register(user));
    }

    @Test
    void shouldReturnThrowsOn_NullUser() {
        user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void userLoginIs_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordValidate_notOK() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordIsEmpty_notOK() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userPasswordIsNull_notOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("Should return throws when user age is less than 18")
    void shouldReturnThrowsForInvalidAge_notOK() {
        user.setAge(15);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void shouldReturnThrowsForNullAge_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    @DisplayName("Should throw exception when user exists")
    void shouldThrowExceptionWhenUserAlreadyExists() {
        User userValid = new User();
        userValid.setLogin("testLogin1");
        userValid.setAge(22);
        userValid.setId(1L);
        userValid.setPassword("passwordTest1234");
        people.add(userValid);
        assertThrows(RuntimeException.class, () -> registrationService.register(userValid));
    }
}
