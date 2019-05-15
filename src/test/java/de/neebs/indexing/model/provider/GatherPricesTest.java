package de.neebs.indexing.model.provider;

import de.neebs.indexing.controller.UpdateSharePrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatherPricesTest {
    @Autowired
    private UpdateSharePrice updateSharePrice;

    @Test
    public void testFull() {
        updateSharePrice.updateSecurityRate();
    }
}
