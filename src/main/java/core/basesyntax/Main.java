package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;

public class Main {
    public static void main(String[] args) {
        RegistrationService registrationService = new RegistrationServiceImpl();
        StorageDao storageDao = new StorageDaoImpl();

        registrationService.register(new User("ambrela", "corporation", 50));
        System.out.println(storageDao.get("ambrela"));

        registrationService.register(new User());
    }
}
