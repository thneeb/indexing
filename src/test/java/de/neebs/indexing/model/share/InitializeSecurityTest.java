package de.neebs.indexing.model.share;

import de.neebs.indexing.model.common.Security;
import de.neebs.indexing.model.common.SecurityRepository;
import de.neebs.indexing.model.common.ShareFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitializeSecurityTest {
    @Autowired
    private ShareFactory shareFactory;

    @Autowired
    private SecurityRepository securityRepository;

    @Test
    public void testInitializeSecurity() {
        List<Security> shares = shareFactory.listShares();
        securityRepository.saveAll(shares);
    }
}
