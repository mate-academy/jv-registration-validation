package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static User user = new User();

    @BeforeAll
    static void beforeAll(){
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp(){
        Storage.people.clear();
        user = new User();
        user.setAge(18);
        user.setLogin("vasiliy@gmail.com");
        user.setPassword("123456");
    }

    @Test
    void register_userValid_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_wrongAge_notOk() {
        user.setAge(13);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithExistentLogin_ok() {
        User register = registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(register));
    }

    @Test
    void register_shortPass_notOk() {
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_NullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullPass_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }


}
