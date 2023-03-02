package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User bob = new User(55555L, "bobr", "123456", 16);
    private static final User john = new User(4444L, "john1998", "123", 26);
    private static final User steve = new User(333L, null, "123456", 65);
    private static final User alice = new User(22L, "alice", "qwerty56", null);
    private static final User artur = new User(22L, "artur", "123456789", -18);
    private static final User kris = new User(22L, "kris", null, 18);
    private static final User kirill = new User(1L, "Kirill", "987654321", 90);
    private static final User stas = new User(154354L, "Stas", "798564213", 40);
    private static final User dima = new User(164354L, "Dima", "9413543fg545", 25);
    private static final User sveta = new User(154352L, "Sveta", "asdfgh456", 31);
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @Test
    void register_userNull_notOk() {
        assertThrows(CustomException.class, () -> registrationService.register(null));
    }

    @Test
    void registerLogin_notOk() {
        assertThrows(CustomException.class, () -> registrationService.register(steve));
    }

    @Test
    void registerAge_notOk() {
        assertThrows(CustomException.class, () -> registrationService.register(bob));
        assertThrows(CustomException.class, () -> registrationService.register(alice));
        assertThrows(CustomException.class, () -> registrationService.register(artur));
    }

    @Test
    void registerPassword_notOk() {
        assertThrows(CustomException.class, () -> registrationService.register(john));
        assertThrows(CustomException.class, () -> registrationService.register(kris));
    }

    @Test
    void storage_add_notOk() {
        storageDao.add(kirill);
        storageDao.add(kirill);

        assertThrows(CustomException.class, () -> registrationService.register(kirill));
    }

    @Test
    void storage_addAndGet_Ok() {
        storageDao.add(kirill);
        storageDao.add(stas);
        storageDao.add(sveta);
        storageDao.add(dima);

        final User firstActualValue = storageDao.get(kirill.getLogin());
        final User secondActualValue = storageDao.get(stas.getLogin());
        final User thirdActualValue = storageDao.get(sveta.getLogin());
        final User fourthActualValue = storageDao.get(dima.getLogin());

        assertEquals(kirill, firstActualValue);
        assertEquals(stas, secondActualValue);
        assertEquals(sveta, thirdActualValue);
        assertEquals(dima, fourthActualValue);
    }

    @Test
    void register_Ok() {
        registrationService.register(kirill);
        registrationService.register(stas);
        registrationService.register(sveta);
        registrationService.register(dima);

        final User firstActualValue = storageDao.get(kirill.getLogin());
        final User secondActualValue = storageDao.get(stas.getLogin());
        final User thirdActualValue = storageDao.get(sveta.getLogin());
        final User fourthActualValue = storageDao.get(dima.getLogin());

        assertEquals(kirill, firstActualValue);
        assertEquals(stas, secondActualValue);
        assertEquals(sveta, thirdActualValue);
        assertEquals(dima, fourthActualValue);
    }
}
