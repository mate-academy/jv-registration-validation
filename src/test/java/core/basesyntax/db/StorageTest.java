package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class StorageTest {

    @Test
    public void storage_exists_ok() {
        assertNotNull(Storage.people);
    }
}
