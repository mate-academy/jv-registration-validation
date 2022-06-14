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

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User firstUser;
    private User secondUser;
    private User thirdUser;
    private User fourthUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        firstUser = new User("Mike", "mike12345", 22);
        secondUser = new User("Alice", "password", 19);
        thirdUser = new User("Melisa", "qwerty", 20);
        fourthUser = new User("MaX", "MaX24MaX", 24);
    }

    @Test
    void password_Length_Is_Valid() {
        firstUser.setPassword("ddd");
        secondUser.setPassword("asdadado");
        thirdUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
        assertEquals(secondUser, registrationService.register(secondUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(thirdUser));
    }

    @Test
    void age_Is_Valid() {
        firstUser.setAge(10);
        secondUser.setAge(22);
        thirdUser.setAge(null);
        fourthUser.setAge(-100);

        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
        assertEquals(secondUser, registrationService.register(secondUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(thirdUser));
        assertThrows(RuntimeException.class, () -> registrationService.register(fourthUser));
    }

    @Test
    void check_Addition_To_List() {
        fourthUser.setLogin("Mike");

        registrationService.register(firstUser);
        assertEquals(1, Storage.people.size());
        registrationService.register(secondUser);
        assertEquals(2, Storage.people.size());
        registrationService.register(thirdUser);
        assertEquals(3, Storage.people.size());
        assertThrows(RuntimeException.class, () -> registrationService.register(fourthUser));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void get_Login_check() {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        registrationService.register(thirdUser);
        registrationService.register(fourthUser);

        StorageDao getUserBylogin = new StorageDaoImpl();
        assertEquals(firstUser, getUserBylogin.get("Mike"));
        assertEquals(secondUser, getUserBylogin.get("Alice"));
        assertEquals(thirdUser, getUserBylogin.get("Melisa"));
        assertEquals(fourthUser, getUserBylogin.get("MaX"));
        assertNull(getUserBylogin.get("AAAA"));
        assertNull(getUserBylogin.get("max"));
        assertNull(getUserBylogin.get("BoDyA"));
        assertNull(getUserBylogin.get(null));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
