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
    private static final User user = new User("Bob_Super", "Bob12345", 18);

    private static RegistrationServiceImpl service;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao.add(user);
    }

    @Test
    void register_validUser_Ok() {
        User validNewUser = new User("_Emily_", "_emily_", 22);

        User validUserResult = service.register(validNewUser);

        assertEquals(validUserResult, validNewUser);
    }
    
    @Test
    void register_validEdgeValuesUser_Ok() {
        User validEdgeNewUser = new User("_Emily", "emily_", 18);

        User validEdgeLoginResult = service.register(validEdgeNewUser);

        assertEquals(validEdgeLoginResult, validEdgeNewUser);
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

    @Test
    void register_wrongLogin_NotOk() {
        User wrongLoginUser = new User("Jack", "jack255", 55);

        assertThrows(RegisterFailedException.class, () ->
                service.register(wrongLoginUser));
    }

    @Test
    void register_edgeWrongLogin_NotOk() {
        User edgeWrongLoginUser = new User("JackJ", "jack255", 55);

        assertThrows(RegisterFailedException.class, () ->
                service.register(edgeWrongLoginUser));
    }

    @Test
    void register_nullLogin_NotOk() {
        User nullLoginUser = new User(null, "jack255", 55);

        assertThrows(RegisterFailedException.class, () ->
                service.register(nullLoginUser));
    }

    @Test
    void register_wrongPassword_NotOk() {
        User wrongPasswordUser = new User("Jack123", "jack", 40);

        assertThrows(RegisterFailedException.class, () ->
                service.register(wrongPasswordUser));
    }

    @Test
    void register_edgePassword_NotOk() {
        User edgePasswordUser = new User("Jack123", "jack1", 40);

        assertThrows(RegisterFailedException.class, () ->
                service.register(edgePasswordUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        User nullPasswordUser = new User("Jack123", null, 40);

        assertThrows(RegisterFailedException.class, () ->
                service.register(nullPasswordUser));
    }

    @Test
    void register_wrongAge_NotOk() {
        User wrongAgeUser = new User("AlexGL", "alex_gl", 10);
        assertThrows(RegisterFailedException.class, () ->
                service.register(wrongAgeUser));
    }

    @Test
    void register_edgeAge_NotOk() {
        User edgeAgeUser = new User("AlexGL", "alex_gl", 17);
        assertThrows(RegisterFailedException.class, () ->
                service.register(edgeAgeUser));
    }

    @Test
    void register_nullAge_NotOk() {
        User nullAgeUser = new User("AlexGL", "alex_gl", null);
        assertThrows(RegisterFailedException.class, () ->
                service.register(nullAgeUser));
    }
}
