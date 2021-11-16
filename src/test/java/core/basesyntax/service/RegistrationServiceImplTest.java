package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static StorageDao storageDao ;
    private static RegistrationService registrationService;
    private static User testUser;

    @BeforeAll
    public static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void beforeNewTest() {
        testUser = new User();
        Storage.people.clear();
    }

    @Test
    void newUserRegistration_Ok(){
        testUser.setLogin("UserLogin");
        testUser.setPassword("HackMeIfYouCan");
        testUser.setAge(18);
        assertNotNull(registrationService.register(testUser));
    }

    @Test
    void emptyAgeField_notOk(){
        testUser.setLogin("UserLogin");
        testUser.setPassword("easyPassword");
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void emptyLoginField_notOk(){
        testUser.setPassword("Password");
        testUser.setAge(105);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void emptyPasswordField_notOk(){
        testUser.setLogin("UserLogin_123");
        testUser.setAge(35);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void negativeAge_notOk(){
        testUser.setLogin("UserLogin");
        testUser.setPassword("Password");
        testUser.setAge(-23);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void notAdultAge_notOk(){
        testUser.setLogin("UserLogin");
        testUser.setPassword("Password");
        testUser.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void shortPassword_notOk(){
        testUser.setLogin("UserLogin");
        testUser.setPassword("short");
        testUser.setAge(22);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }

    @Test
    void registrationExistingUser_notOk(){
        testUser.setLogin("UserLogin");
        testUser.setPassword("normPassword");
        testUser.setAge(45);
        registrationService.register(testUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testUser));
    }
}
