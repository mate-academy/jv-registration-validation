package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegisterFailedException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static StorageDao storageDao;

    private static final User firstUser = new User("Bob_Super", "Bob12345", 18);
    private static final User secondUser = new User("X_Ann_X", "Ann123", 25);
    private static final User thirdUser = new User("Thomas", "123Tom", 31);

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(firstUser);
        storageDao.add(secondUser);
        storageDao.add(thirdUser);
    }

    @Test
    void register_validUser_Ok() {
        User validEdgeLoginNewUser = new User("_Emily", "emily_1234", 21);
        User validEdgePasswordNewUser = new User("_Emily2_", "emily_", 21);
        User validEdgeAgeNewUser = new User("_Emily3_", "emily_1234", 18);

        User validEdgeLoginResult = service.register(validEdgeLoginNewUser);
        User validEdgePasswordResult = service.register(validEdgePasswordNewUser);
        User validEdgeAgeResult = service.register(validEdgeAgeNewUser);

        assertEquals(validEdgeLoginResult, validEdgeLoginNewUser);
        assertEquals(validEdgePasswordResult, validEdgePasswordNewUser);
        assertEquals(validEdgeAgeResult, validEdgeAgeNewUser);
    }

    @Test
    void register_wrongLoginUser_NotOk() {
        User wrongLoginUser = new User("Jack", "jack255", 55);
        User edgeLoginUser = new User("JackJ", "jack255", 55);
        User nullLoginUser = new User(null, "jack255", 55);

        assertThrows(RegisterFailedException.class, () ->
                service.register(wrongLoginUser));
        assertThrows(RegisterFailedException.class, () ->
                service.register(edgeLoginUser));
        assertThrows(RegisterFailedException.class, () ->
                service.register(nullLoginUser));
    }

    @Test
    void register_wrongPasswordUser_NotOk() {
        User wrongPasswordUser = new User("Jack123", "jack", 40);
        User edgePasswordUser = new User("Jack123", "jack1", 40);
        User nullPasswordUser = new User("Jack123", null, 40);

        assertThrows(RegisterFailedException.class, () ->
                service.register(wrongPasswordUser));
        assertThrows(RegisterFailedException.class, () ->
                service.register(edgePasswordUser));
        assertThrows(RegisterFailedException.class, () ->
                service.register(nullPasswordUser));
    }

    @Test
    void register_wrongAgeUser_NotOk() {
        User wrongAgeUser = new User("AlexGL", "alex_gl", 17);
        User edgeAgeUser = new User("AlexGL", "alex_gl", 10);
        User nullAgeUser = new User("AlexGL", "alex_gl", null);

        assertThrows(RegisterFailedException.class, () ->
                service.register(wrongAgeUser));
        assertThrows(RegisterFailedException.class, () ->
                service.register(edgeAgeUser));
        assertThrows(RegisterFailedException.class, () ->
                service.register(nullAgeUser));
    }

    @Test
    void register_sameLoginUser_NotOk() {
        User sameLoginUser = new User("Bob_Super", "Bob2345", 20);

        assertThrows(RegisterFailedException.class, () ->
                service.register(sameLoginUser));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                service.register(null));
    }
}
