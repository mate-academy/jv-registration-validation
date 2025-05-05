package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserRegisterException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {

    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_ok() {
        User newUser = new User();
        newUser.setLogin("anonym");
        newUser.setPassword("123456");
        newUser.setAge(18);
        User userFromDb = registrationService.register(newUser);

        assertNotNull(userFromDb.getId());
        assertEquals(newUser.getLogin(), userFromDb.getLogin());
    }

    @Test
    void register_exist_notOk() {
        User newUser = new User();
        newUser.setLogin("abcdef");
        newUser.setPassword("123456");
        newUser.setAge(19);
        registrationService.register(newUser); // добавляем пользователя
        User sameLoginUser = new User();
        sameLoginUser.setLogin("abcdef"); // тот же логин
        sameLoginUser.setPassword("654321");
        sameLoginUser.setAge(20);

        assertThrows(UserRegisterException.class,
                () -> registrationService.register(sameLoginUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User newUser = new User();
        newUser.setLogin("sabra");
        newUser.setPassword("123456");
        newUser.setAge(19);

        assertThrows(UserRegisterException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortPass_notOk() {
        User newUser = new User();
        newUser.setLogin("abcdef");
        newUser.setPassword("12345"); // менее 6 символов
        newUser.setAge(19);

        assertThrows(UserRegisterException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_age_notOk() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("123456");
        user.setAge(17);

        assertThrows(UserRegisterException.class, () -> registrationService.register(user));
    }
}
