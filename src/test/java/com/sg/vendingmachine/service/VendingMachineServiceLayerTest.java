/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author timpinkerton
 */
public class VendingMachineServiceLayerTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerTest() {
//        VendingMachineDao dao = new VendingMachineDaoStubImpl();
//        service = new VendingMachineServiceLayerImpl(dao);

    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    service = 
        ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetSnackInvalidSelection() throws Exception {
        BigDecimal testMoney = new BigDecimal("5.00");
        Snack testSnack = new Snack("X1");

        try {
            service.getOneSnack(testSnack.getSnackId(), testMoney);
            fail("Expected VendingMachineInvalidSelectionException was not thrown.");
        } catch (VendingMachineInvalidSelectionException e) {
            return;
        }
    }

    @Test
    public void testGetSnackInsufficientFunds() throws Exception {
        BigDecimal testMoney = new BigDecimal("2.49");
        Snack testSnack = new Snack("T1");

        try {
            service.getOneSnack(testSnack.getSnackId(), testMoney);
            fail("Expected VendingMachineInvalidSelectionException was not thrown.");
        } catch (VendingMachineInvalidSelectionException e) {
            return;
        }
    }

    @Test
    public void testGetSnackOutOfStock() throws Exception {
        BigDecimal testMoney = new BigDecimal("2.55");
        Snack testSnack = new Snack("T2");

        try {
            service.getOneSnack(testSnack.getSnackId(), testMoney);
            fail("Expected VendingMachineInvalidSelectionException was not thrown.");
        } catch (VendingMachineInvalidSelectionException e) {
            return;
        }
    }

    /**
     * Test of getAllSnacks method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAllSnacks() throws Exception {
        assertEquals(2, service.getAllSnacks().size()); 
    }

    /**
     * Test of getOneSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetOneSnack_String_BigDecimal() throws Exception {
    
    }

    /**
     * Test of getOneSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetOneSnack_String() throws Exception {
        Snack testSnack = service.getOneSnack("T1"); 
        assertNotNull(testSnack); 
        testSnack = service.getOneSnack("ZZZZ");
        assertNull(testSnack); 
    }

    /**
     * Test of updateSnack method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testUpdateSnack() throws Exception {
    }

    /**
     * Test of makeChange method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testMakeChange() {
    }

}
