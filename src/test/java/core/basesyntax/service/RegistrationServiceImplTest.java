package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static StorageDao storageDao;
    private static final String EMPTY_LINE = "";
    private static final String ONE_SYMBOL_LINE = "a";
    private static final String THREE_SYMBOLS_LINE = "abc";
    private static final String FIVE_SYMBOLS_LINE = "12345";
    private static final String SIX_SYMBOLS_LINE = "Always";
    private static final String VALID_LOGIN = "anotherLogin";
    private static final String SECOND_VALID_LOGIN = "MateAcademy";
    private static final String SIX_SYMBOLS_LOGIN = "Chance";
    private static final String ANOTHER_VALID_LOGIN = "veryOriginalLogin";
    private static final String VALID_PASSWORD = "longEnoughPassword";
    private static final int NEGATIVE_AGE = -599;
    private static final int ZERO_AGE = 0;
    private static final int SEVENTEEN_AGE = 17;
    private static final int EIGHTEEN_AGE = 18;
    private static final int VALID_AGE = 27;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
    }

    @Test
    public void add_validUser_Ok() {
        User validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
        service.register(validUser);
        assertEquals(validUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void add_sixCharactersLoginUser_Ok() {
        User sixCharactersLoginUser = new User();
        sixCharactersLoginUser.setLogin(SIX_SYMBOLS_LOGIN);
        sixCharactersLoginUser.setPassword(VALID_PASSWORD);
        sixCharactersLoginUser.setAge(VALID_AGE);
        service.register(sixCharactersLoginUser);
        assertEquals(sixCharactersLoginUser, storageDao.get(SIX_SYMBOLS_LOGIN));
    }

    @Test
    public void add_sixCharactersPasswordUser_Ok() {
        User sixCharactersPasswordUser = new User();
        sixCharactersPasswordUser.setLogin(SIX_SYMBOLS_LINE);
        sixCharactersPasswordUser.setPassword(SIX_SYMBOLS_LINE);
        sixCharactersPasswordUser.setAge(VALID_AGE);
        service.register(sixCharactersPasswordUser);
        assertEquals(sixCharactersPasswordUser, storageDao.get(SIX_SYMBOLS_LINE));
    }

    @Test
    public void add_eighteenYearsOldUser_Ok() {
        User eighteenYearsOldUser = new User();
        eighteenYearsOldUser.setLogin(SECOND_VALID_LOGIN);
        eighteenYearsOldUser.setPassword(VALID_PASSWORD);
        eighteenYearsOldUser.setAge(EIGHTEEN_AGE);
        service.register(eighteenYearsOldUser);
        assertEquals(eighteenYearsOldUser, storageDao.get(SECOND_VALID_LOGIN));
    }

    @Test
    public void add_negativeAgeUser_not_Ok() {
        User negativeAgeUser = new User();
        negativeAgeUser.setLogin(ANOTHER_VALID_LOGIN);
        negativeAgeUser.setPassword(VALID_PASSWORD);
        negativeAgeUser.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> service.register(negativeAgeUser));
    }

    @Test
    public void add_null_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    public void add_nullLoginUser_notOk() {
        User nullLoginUser = new User();
        nullLoginUser.setLogin(null);
        nullLoginUser.setPassword(ANOTHER_VALID_LOGIN);
        nullLoginUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(nullLoginUser));
    }

    @Test
    public void add_nullPasswordUser_notOk() {
        User nullPasswordUser = new User();
        nullPasswordUser.setLogin(ANOTHER_VALID_LOGIN);
        nullPasswordUser.setPassword(null);
        nullPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(nullPasswordUser));
    }

    @Test
    public void add_nullLoginAndPasswordUser_notOk() {
        User nullLoginAndPasswordUser = new User();
        nullLoginAndPasswordUser.setLogin(null);
        nullLoginAndPasswordUser.setPassword(null);
        nullLoginAndPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(nullLoginAndPasswordUser));
    }

    @Test
    public void add_emptyLoginUser_notOk() {
        User emptyLoginUser = new User();
        emptyLoginUser.setLogin(EMPTY_LINE);
        emptyLoginUser.setPassword(VALID_PASSWORD);
        emptyLoginUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(emptyLoginUser));
    }

    @Test
    public void add_emptyPasswordUser_notOk() {
        User emptyPasswordUser = new User();
        emptyPasswordUser.setLogin(ANOTHER_VALID_LOGIN);
        emptyPasswordUser.setPassword(EMPTY_LINE);
        emptyPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(emptyPasswordUser));
    }

    @Test
    public void add_emptyLoginAndPasswordUser_notOk() {
        User emptyLoginAndPasswordUser = new User();
        emptyLoginAndPasswordUser.setLogin(EMPTY_LINE);
        emptyLoginAndPasswordUser.setPassword(EMPTY_LINE);
        emptyLoginAndPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> service.register(emptyLoginAndPasswordUser));
    }

    @Test
    public void add_oneSymbolLoginUser_notOk() {
        User oneSymbolLoginUser = new User();
        oneSymbolLoginUser.setLogin(ONE_SYMBOL_LINE);
        oneSymbolLoginUser.setPassword(VALID_PASSWORD);
        oneSymbolLoginUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(oneSymbolLoginUser));
    }

    @Test
    public void add_threeSymbolsLoginUser_notOk() {
        User threeSymbolLoginUser = new User();
        threeSymbolLoginUser.setLogin(THREE_SYMBOLS_LINE);
        threeSymbolLoginUser.setPassword(VALID_PASSWORD);
        threeSymbolLoginUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(threeSymbolLoginUser));
    }

    @Test
    public void add_fiveSymbolsLoginUser_notOk() {
        User fiveSymbolLoginUser = new User();
        fiveSymbolLoginUser.setLogin(FIVE_SYMBOLS_LINE);
        fiveSymbolLoginUser.setPassword(VALID_PASSWORD);
        fiveSymbolLoginUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(fiveSymbolLoginUser));
    }

    @Test
    public void add_oneSymbolPasswordUser_notOk() {
        User oneSymbolPasswordUser = new User();
        oneSymbolPasswordUser.setLogin(ANOTHER_VALID_LOGIN);
        oneSymbolPasswordUser.setPassword(ONE_SYMBOL_LINE);
        oneSymbolPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(oneSymbolPasswordUser));
    }

    @Test
    public void add_threeSymbolsPasswordUser_notOk() {
        User threeSymbolPasswordUser = new User();
        threeSymbolPasswordUser.setLogin(ANOTHER_VALID_LOGIN);
        threeSymbolPasswordUser.setPassword(THREE_SYMBOLS_LINE);
        threeSymbolPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(threeSymbolPasswordUser));
    }

    @Test
    public void add_fiveSymbolsPasswordUser_notOk() {
        User fiveSymbolPasswordUser = new User();
        fiveSymbolPasswordUser.setLogin(ANOTHER_VALID_LOGIN);
        fiveSymbolPasswordUser.setPassword(FIVE_SYMBOLS_LINE);
        fiveSymbolPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(fiveSymbolPasswordUser));
    }

    @Test
    public void add_fiveSymbolsLoginAndPasswordUser_notOk() {
        User shortLoginAndPasswordUser = new User();
        shortLoginAndPasswordUser.setLogin(FIVE_SYMBOLS_LINE);
        shortLoginAndPasswordUser.setPassword(FIVE_SYMBOLS_LINE);
        shortLoginAndPasswordUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> service.register(shortLoginAndPasswordUser));
    }

    @Test
    public void add_zeroAgeUser_notOk() {
        User zeroAgeUser = new User();
        zeroAgeUser.setLogin(ANOTHER_VALID_LOGIN);
        zeroAgeUser.setPassword(VALID_PASSWORD);
        zeroAgeUser.setAge(ZERO_AGE);
        assertThrows(RegistrationException.class, () -> service.register(zeroAgeUser));
    }

    @Test
    public void add_seventeenAgeUser_notOk() {
        User seventeenAgeUser = new User();
        seventeenAgeUser.setLogin(ANOTHER_VALID_LOGIN);
        seventeenAgeUser.setPassword(VALID_PASSWORD);
        seventeenAgeUser.setAge(SEVENTEEN_AGE);
        assertThrows(RegistrationException.class, () -> service.register(seventeenAgeUser));
    }

    @Test
    public void add_invalidAllFieldsUser_notOk() {
        User invalidAllFieldsUser = new User();
        invalidAllFieldsUser.setLogin(ONE_SYMBOL_LINE);
        invalidAllFieldsUser.setPassword(THREE_SYMBOLS_LINE);
        invalidAllFieldsUser.setAge(SEVENTEEN_AGE);
        assertThrows(RegistrationException.class,
                () -> service.register(invalidAllFieldsUser));
    }

    @Test
    public void add_noAgeUser_notOk() {
        User noAgeUser = new User();
        noAgeUser.setLogin(ANOTHER_VALID_LOGIN);
        noAgeUser.setPassword(VALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> service.register(noAgeUser));
    }

    @Test
    public void add_sameLoginUser_notOk() {
        User sameLoginUser = new User();
        sameLoginUser.setLogin(VALID_LOGIN);
        sameLoginUser.setPassword(VALID_PASSWORD);
        sameLoginUser.setAge(VALID_AGE);
        assertThrows(RegistrationException.class, () -> service.register(sameLoginUser));
    }
}
