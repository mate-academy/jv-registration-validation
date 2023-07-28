package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_NullLoginUser_NotOk() {
        User nullLoginUser = new User(null, "password", 22);
        assertThrows(ValidationException.class, () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_NullPasswordUser_NotOk() {
        User nullPasswordUser = new User("normalLogin", null, 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(nullPasswordUser));
    }

    @Test
    void register_ShortLoginUser_NotOk() {
        User shortLoginUser = new User("Mike", "password", 22);
        assertThrows(ValidationException.class, () -> registrationService.register(shortLoginUser));
        User zeroLettersLoginUser = new User("", "password", 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(zeroLettersLoginUser));
        User edgeLettersLoginUser = new User("login", "password", 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(edgeLettersLoginUser));
    }

    @Test
    void register_ShortPasswordUser_NotOk() {
        User shortPasswordUser = new User("normalLogin", "pass", 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(shortPasswordUser));
        User edgeCharactersPasswordUser = new User("nextLogin", "12345", 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(edgeCharactersPasswordUser));
        User zeroCharactersPasswordUser = new User("thirdLogin", "", 22);
        assertThrows(ValidationException.class, () ->
                registrationService.register(zeroCharactersPasswordUser));
    }

    @Test
    void register_UnderageUser_NotOk() {
        User youngAgeUser = new User("normalLogin", "password", 12);
        assertThrows(ValidationException.class, () -> registrationService.register(youngAgeUser));
        User edgeAgeUser = new User("nextLogin", "password", 17);
        assertThrows(ValidationException.class, () -> registrationService.register(edgeAgeUser));
        User oneYearsAgeUser = new User("thirdLogin", "password", 1);
        assertThrows(ValidationException.class, () ->
                registrationService.register(oneYearsAgeUser));
    }

    @Test
    void register_ValidCase_Ok() {
        User validUser = new User("validLogin", "validPassword", 42);
        User ordinaryUser = new User("Michael", "Fratello", 2013);
        User edgeOkUser = new User("123456", "123456", 18);
        assertEquals(validUser, registrationService.register(validUser));
        assertEquals(ordinaryUser, registrationService.register(ordinaryUser));
        assertEquals(edgeOkUser, registrationService.register(edgeOkUser));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void registered_UsedLogin_NotOk() {
        User ordinaryUser = new User("Michael", "Fratello", 2013);
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(ordinaryUser);
        assertThrows(ValidationException.class, () -> registrationService.register(ordinaryUser));
    }
}
