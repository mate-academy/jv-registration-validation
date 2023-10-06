package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
/*
Я сам не знаю як, але я тут щось нахімічив, ну і здається підкреслене червоним Assertion пропало,
але чи все вірно тут працює не знаю.
Якщо зможеш зрозуміти в чому тут проблема, то буду безмежно вдячний, але здається, що це - якась
локальна проблема :(

 */
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        return null;
    }
}
