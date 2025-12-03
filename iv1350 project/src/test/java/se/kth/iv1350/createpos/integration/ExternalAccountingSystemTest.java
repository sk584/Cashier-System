package se.kth.iv1350.createpos.integration;

import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.createpos.dto.SaleDTO;

public class ExternalAccountingSystemTest {
    private ExternalAccountingSystem externalAccountingSystem;

    @Before
    public void setUp() {
        externalAccountingSystem = new ExternalAccountingSystem();
    }

    @Test
    public void testUpdatesAccountingSystem() {
        SaleDTO saleDTO = new SaleDTO(5, 100,null);
        externalAccountingSystem.updatesAccountingSystem(saleDTO);
    }
    
}
