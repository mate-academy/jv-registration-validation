package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User testUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
    }

    @BeforeEach
    public void beforeNewTest() {
        testUser.setLogin("UserLogin");
        testUser.setPassword("HackMeIfYouCan");
        testUser.setAge(18);
        Storage.people.clear();
    }

    @Test
    void register_newUserRegistration_Ok(){
        assertNotNull(registrationService.register(testUser));
    }

    @Test
    void register_nullAgeField_notOk(){
        testUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLoginField_notOk(){
        testUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_emptyLoginField_notOk(){
        testUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPasswordField_notOk(){
        testUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_negativeAge_notOk(){
        testUser.setAge(-23);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_notAdultAge_notOk(){
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_shortPassword_notOk(){
        testUser.setPassword("short");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_registrationExistingUser_notOk(){
        registrationService.register(testUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }
}
