package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    private User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void nullUser() {
        assertNotNull(user);
    }

    @Test
    void notNullLogin() {
        assertNull(user.getLogin());
    }

    @Test
    void notNullPassword() {
        assertNull(user.getPassword());
    }

    @Test
    void addUser() {
        User torin = new User();
        torin.setAge(60);
        torin.setLogin("oakShield");
        torin.setPassword("123456");
        registrationService.register(torin);
        assertNotNull(storageDao.get("oakShield"));
    }

    @Test
    void sizeAfterAdd() {
        User bruce = new User();
        bruce.setAge(19);
        bruce.setLogin("Batman");
        bruce.setPassword("whereisthedetonator");
        registrationService.register(bruce);
        int expectedSize = 1;
        assertEquals(expectedSize, Storage.people.size());
    }

    @Test
    void smallGuy() {
        User youngUser = new User();
        youngUser.setLogin("Kotyhoroshko");
        youngUser.setPassword("3,1415926535");
        youngUser.setAge(15);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(youngUser);
        });
    }

    @Test
    void shortPassword() {
        User peterParker = new User();
        peterParker.setAge(22);
        peterParker.setLogin("AmazingSpiderMan");
        peterParker.setPassword("MJ");
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(peterParker);
        });
    }
}
