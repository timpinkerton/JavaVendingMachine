/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author timpinkerton
 */
public class VendingMachineDaoTest {

    private VendingMachineDao dao = new VendingMachineDaoFileImpl();
    List<Snack> snackList;

    public VendingMachineDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SnackInventoryPersistenceException {
        snackList = dao.getAllSnacks();

        List<Snack> setUpSnackInventory = snackList;

        for (Snack snack : setUpSnackInventory) {
            //call dao.remove method to delete everything...
            dao.removeSnack(snack.getSnackId());
        }
    }

    @After
    public void tearDown() throws SnackInventoryPersistenceException {
        List<Snack> tearDownSnackInventory = snackList;

        for (Snack snack : tearDownSnackInventory) {
            //call dao.add to put it all back
            dao.addSnack(snack.getSnackId(), snack);
        }
    }

    /**
     * Test of getAllSnacks method, of class VendingMachineDao.
     */
    @Test
    public void testGetAllSnacks() throws Exception {

        //creating a snack to test
        Snack testSnack = new Snack("1111");
        testSnack.setName("Cookies");
        testSnack.setPrice(new BigDecimal("6.99"));
        testSnack.setQuantity(1);

        //adding the snack to the hashmap (inventory)
        dao.addSnack(testSnack.getSnackId(), testSnack);

        //the size returned should be 1
        assertEquals(1, dao.getAllSnacks().size());

        //removing the test snack from the hashmap
        dao.removeSnack(testSnack.getSnackId());
    }

    /**
     * Test of getOneSnack method, of class VendingMachineDao.
     */
    @Test
    public void testGetOneSnack() throws Exception {

        Snack testSnack = new Snack("2222");
        testSnack.setName("Chips");
        testSnack.setPrice(new BigDecimal("1.99"));
        testSnack.setQuantity(5);

        dao.addSnack(testSnack.getSnackId(), testSnack);

        Snack snackFromDao = dao.getOneSnack("2222");

        //Test snack is created above, then added to the inventory.  Then the
        //  getOneSnack is called on the same snackId.  
        //  Test is to see if the two are equal
        assertEquals(testSnack, snackFromDao);

        dao.removeSnack(testSnack.getSnackId());
    }

    /**
     * Test of updateSnack method, of class VendingMachineDao.
     */
    @Test
    public void testUpdateSnack() throws Exception {

        Snack testSnack = new Snack("3333");
        testSnack.setName("Bananas");
        testSnack.setPrice(new BigDecimal("9.99"));
        testSnack.setQuantity(89);

        dao.addSnack(testSnack.getSnackId(), testSnack);

        int qtyBeforeUpdate = testSnack.getQuantity();
        dao.updateSnack(testSnack.getSnackId());

        int qtyAfterUpdate = testSnack.getQuantity();
        assertEquals(qtyBeforeUpdate, (qtyAfterUpdate + 1));

        dao.removeSnack(testSnack.getSnackId());

    }

    /**
     * Test of makeChange method, of class VendingMachineDao.
     */
    @Test
    public void testMakeChange() {

        //setting a variable for the amount of change the user should get back
        BigDecimal changeDue = new BigDecimal("3.19");

        //creating map to store the results of the makeChange() method
        Map<Coin, BigDecimal> changeReturned = new HashMap<>();

        changeReturned = dao.makeChange(changeDue);

        //creating a variable to store the amount returned from the makeChange()
        //  as a BigDecimal
        BigDecimal changeReturnedAsBigDecimal = BigDecimal.ZERO;

        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");
        BigDecimal penny = new BigDecimal("0.01");

        BigDecimal numberOfQuarters;
        BigDecimal numberOfDimes;
        BigDecimal numberOfNickels;
        BigDecimal numberOfPennies;

        BigDecimal valueOfQuartersDispensed;
        BigDecimal valueOfDimesDispensed;
        BigDecimal valueOfNickelsDispensed;
        BigDecimal valueOfPenniesDispensed; 

        //calculating the number of quarters that should be returned
        numberOfQuarters = changeDue.divide(quarter);
        //calculating the value of the quarters returned
        valueOfQuartersDispensed = numberOfQuarters.setScale(0, RoundingMode.DOWN).multiply(quarter);
        //calculating how much change is left to be dispensed after quarters
        BigDecimal changeAfterQuarters = changeDue.subtract(valueOfQuartersDispensed);
        
        //adding the value of the quarters returned
        changeReturnedAsBigDecimal = changeReturnedAsBigDecimal.add(valueOfQuartersDispensed);

        //dimes
        numberOfDimes = changeAfterQuarters.divide(dime);
        valueOfDimesDispensed = numberOfDimes.setScale(0, RoundingMode.DOWN).multiply(dime);

        BigDecimal changeAfterDimes = changeAfterQuarters.subtract(valueOfDimesDispensed);
        
        changeReturnedAsBigDecimal = changeReturnedAsBigDecimal.add(valueOfDimesDispensed);
        
        //nickels
        numberOfNickels = changeAfterDimes.divide(nickel);
        valueOfNickelsDispensed = numberOfNickels.setScale(0, RoundingMode.DOWN).multiply(nickel);

        BigDecimal changeAfterNickels = changeAfterDimes.subtract(valueOfNickelsDispensed);
        
        changeReturnedAsBigDecimal = changeReturnedAsBigDecimal.add(valueOfNickelsDispensed);
        
        //pennies
        numberOfPennies = changeAfterNickels.divide(penny);
        valueOfPenniesDispensed = numberOfPennies.setScale(0, RoundingMode.DOWN).multiply(penny);
        
        changeReturnedAsBigDecimal = changeReturnedAsBigDecimal.add(valueOfPenniesDispensed);

        //testing if the amount of change returnes equals the amount of change that is due
        assertEquals(changeDue, changeReturnedAsBigDecimal);
    }

}
