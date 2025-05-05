package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        defaultUser = new User();
        defaultUser.setId(1234L);
        defaultUser.setAge(20);
        defaultUser.setLogin("milian");
        defaultUser.setPassword("maksym");
    }

    private void checkThrows(User user) {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void addNewUser_Ok() {
        User newUser = new User();
        newUser.setId(1234L);
        newUser.setAge(20);
        newUser.setLogin("login6");
        newUser.setPassword("maksym");
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    public void addUserWithExistedLogin_NotOk() {
        User newUser = new User();
        newUser.setId(3456L);
        newUser.setAge(22);
        newUser.setLogin("milian");
        newUser.setPassword("asdasd");
        registrationService.register(defaultUser);
        checkThrows(newUser);
    }

    @Test
    public void addUserWithShortLogin_NotOk() {
        User newUser = new User();
        newUser.setId(3456L);
        newUser.setAge(22);
        newUser.setLogin("mili");
        newUser.setPassword("asdasd");
        checkThrows(newUser);
    }

    @Test
    public void addUserWithShortPassword_NotOk() {
        User newUser = new User();
        newUser.setId(3456L);
        newUser.setAge(22);
        newUser.setLogin("shortPassword");
        newUser.setPassword("asd");
        checkThrows(newUser);
    }

    @Test
    public void addUserUnderaged_NotOk() {
        User newUser = new User();
        newUser.setId(3456L);
        newUser.setAge(17);
        newUser.setLogin("underaged");
        newUser.setPassword("asdasd");
        checkThrows(newUser);
    }

    @Test
    public void addUserWithNullAge_NotOk() {
        User newUser = new User();
        newUser.setId(3456L);
        newUser.setAge(null);
        newUser.setLogin("underaged");
        newUser.setPassword("asdasd");
        checkThrows(newUser);
    }

    @Test
    public void nullUserAdding_NotOk() {
        checkThrows(null);
    }
}
