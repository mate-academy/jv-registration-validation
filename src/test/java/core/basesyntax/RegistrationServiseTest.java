package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiseTest {
    private static StorageDao dao;
    private static RegistrationService registrationService;
    private User firstUser;

    @BeforeAll
    public static void getStorage() {
        dao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void createUser() {
        firstUser = new User();
        firstUser.setLogin("FirstLogin");
        firstUser.setPassword("password");
        firstUser.setAge(20);

    }

    @Test
    public void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null),
                "In case of null-User should throw RuntimeException."
        );
    }

    @Test
    public void register_ExistingUser_NotOk() {
        try {
            User sameUser = registrationService.register(firstUser);
            registrationService.register(sameUser);
        } catch (RuntimeException e) {
            return;
        }
        fail("In case of the same User should throw RuntimeException.");
    }

    @Test
    public void register_TooYoungUser_NotOk() {
        firstUser.setAge(11);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser),
                "In case of the User\'s age smaller 18 should throw RuntimeException."
        );
    }

    @Test
    public void register_ShortPassword_NotOk() {
        firstUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser),
                "In case of too short password should throw RuntimeException."
        );
    }

    @Test
    public void register_newUser_Ok() {
        registrationService.register(firstUser);
        User actualUser = dao.get(firstUser.getLogin());
        assertEquals(actualUser.getAge(), firstUser.getAge(),
                "the actual age value is " + firstUser.getAge()
                        + ", but the age is " + actualUser.getAge()
        );
        assertEquals(actualUser.getPassword(), firstUser.getPassword(),
                "the actual password is " + firstUser.getPassword()
                        + ", but the password is " + actualUser.getPassword()
        );
    }

}
