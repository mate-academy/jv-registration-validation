package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        firstUser = new User("Mike", "mike12345", 22);
        secondUser = new User("Alice", "password", 19);
        thirdUser = new User("Melisa", "qwerty", 20);
        fourthUser = new User("MaX", "MaX24MaX", 24);
    }

    @Test
    public void register_passwordLength_isValid() {
        firstUser.setPassword("dddssssssssad");
        secondUser.setPassword("asdadado");
        thirdUser.setPassword("sss123osaa");
        fourthUser.setPassword("qwrerasfdsdfs");
        assertEquals(firstUser, registrationService.register(firstUser));
        assertEquals(secondUser, registrationService.register(secondUser));
        assertEquals(thirdUser, registrationService.register(thirdUser));
        assertEquals(fourthUser, registrationService.register(fourthUser));
    }

    @Test
    public void register_passwordLength_isNotValid() {
        firstUser.setPassword("ddd");
        secondUser.setPassword("0");
        thirdUser.setPassword(null);
        fourthUser.setPassword("..");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(thirdUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(fourthUser));
    }

    @Test
    public void register_age_isNotValid() {
        firstUser.setAge(10);
        secondUser.setAge(null);
        thirdUser.setAge(17);
        fourthUser.setAge(-100);

        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(thirdUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(fourthUser));
    }

    @Test
    public void register_age_isValid() {
        assertEquals(firstUser, registrationService.register(firstUser));
        assertEquals(secondUser, registrationService.register(secondUser));
        assertEquals(thirdUser, registrationService.register(thirdUser));
        assertEquals(fourthUser, registrationService.register(fourthUser));
    }

    @Test
    public void checkAdditionToList_ok() {
        registrationService.register(firstUser);
        assertEquals(1, Storage.people.size());
        registrationService.register(secondUser);
        assertEquals(2, Storage.people.size());
        registrationService.register(thirdUser);
        assertEquals(3, Storage.people.size());
    }

    @Test
    public void getLoginCheck_isValid() {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        registrationService.register(thirdUser);
        registrationService.register(fourthUser);

        StorageDao getUserBylogin = new StorageDaoImpl();
        assertEquals(firstUser, getUserBylogin.get("Mike"));
        assertEquals(secondUser, getUserBylogin.get("Alice"));
        assertEquals(thirdUser, getUserBylogin.get("Melisa"));
        assertEquals(fourthUser, getUserBylogin.get("MaX"));
    }

    @Test
    public void getLoginCheck_isNotValid() {
        StorageDao getUserBylogin = new StorageDaoImpl();
        assertNull(getUserBylogin.get("AAAA"));
        assertNull(getUserBylogin.get("max"));
        assertNull(getUserBylogin.get("BoDyA"));
        assertNull(getUserBylogin.get(null));
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}
