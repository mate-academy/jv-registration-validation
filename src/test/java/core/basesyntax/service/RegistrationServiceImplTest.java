package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void serviceInitialization() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void userStorageInitialization() {
        user = new User(1L,"valid_login", "123456", 40);
        Storage.people.addAll(List.of(new User(2L,"LarsUrlich","myolddrums",60),
                new User(2L, "JamesHetfield","mastermaster",61),
                new User(3L,"KirkHammet","Imtoofastforyou",60),
                new User(4L,"JasonNewsted","imnothere777",99)));
    }

    @Test
    void nullLoginIfExceptionThrow_Ok() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void negativeAgeIfExceptionThrow_Ok() {
        user.setAge(-20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPasswordIfExceptionThrow_Ok() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void impossibleAgeIfExceptionThrow_Ok() {
        user.setAge(Integer.MAX_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void shortLoginIfExceptionThrow_Ok() {
        user.setLogin("John");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void shortPasswordIfExceptionThrow_Ok() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void existingUserIfExceptionThrow_Ok() {
        User doubledUser = new User(7L,"LarsUrlich","thellahunginjeet",19);
        Storage.people.add(doubledUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(doubledUser));
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
