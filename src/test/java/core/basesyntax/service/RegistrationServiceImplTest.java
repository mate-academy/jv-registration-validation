package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static StorageDaoImpl storage;
    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        storage = new StorageDaoImpl();
        storage.add(new User(1535854439L, "bobTheOne", "iAmHereAlice", 23));
    }

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_ExistingUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535854439L, "bobTheOne", "iAmHereAlice", 23));
        });
    }

    @Test
    void register_UserAgeLessThan18_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(15358545039L, "Shazam", "SHAZAM!!!", 12));
        });
    }

    @Test
    void register_ShortPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535855239L, "John", "hi", 19));
        });
    }

    @Test
    void register_nullLogin_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1542876439L, null, "iHaveADream", 23));
        });
    }

    @Test
    void register_nullPassword_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1542854439L, "Batman", null, 23));
        });
    }

    @Test
    void register_nullAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535854439L, "Sakura", "iLoveCards", null));
        });
    }

    @Test
    void register_nullID_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(null, "NeoTheChosen", "followTheWhiteRabbit", 19));
        });
    }

    @Test
    void register_negativeAge_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(1535829039L, "BendjaminButton", "amILive?", -12));
        });
    }

    @Test
    void register_negativeID_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User(-1535854439L, "Penguin", "whereIsRobin?", 29));
        });
    }

    @Test
    void register_normalUser_Ok() {
        User user = service.register(new User(1535855239L, "MisterSandman", "BringMeADreams", 20));
        assertNotNull(user);
    }
}
