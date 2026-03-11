package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = createUser("qwerty", "123456", 18);

        User actual = registrationService.register(user);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals("qwerty", actual.getLogin());
        Assertions.assertEquals("123456", actual.getPassword());
        Assertions.assertEquals(18, actual.getAge());
        Assertions.assertEquals(1, Storage.people.size());
        Assertions.assertEquals(actual, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    // інші тести — аналогічно (Assertions.assertThrows / assertEquals / assertNotNull)

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
