package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTests {
    private static final String LOGIN_DEFAULT = "yurii.storozhuk@gmail.com";
    private static final String PASSWORD_DEFAULT = "12345678";
    private static final int AGE_DEFAULT = 18;
    private static RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUpDefaultUser() {
        registrationService = new RegistrationServiceImpl();
        user = new User(LOGIN_DEFAULT, PASSWORD_DEFAULT, AGE_DEFAULT);
    }

    @Test
    void register_userPasswordMinLength_Ok() throws RegistrationServiceException {
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userPasswordMinLength_notOk() throws RegistrationServiceException {
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_userAgeMin_Ok() throws RegistrationServiceException {
        User actual = registrationService.register(user);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void register_userAgeMin_notOk() throws RegistrationServiceException {
        user.setAge(16);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            User actual = registrationService.register(user);
        });
    }

    @Test
    void register_userNull_Ok() {
        user = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            User actual = registrationService.register(user);
        });
    }

    @Test
    void register_userExist_Ok() throws Exception {
        registrationService.register(user);
        Assertions.assertThrows(RegistrationServiceException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void finishSetUp() {
        Storage.people.clear();
    }
}
