/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.SnackInventoryPersistenceException;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author timpinkerton
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;

    //Constructor **************************************************************
    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Snack> getAllSnacks() throws SnackInventoryPersistenceException {
        return dao.getAllSnacks();
    }

    @Override
    public Snack getOneSnack(String snackId)
            throws SnackInventoryPersistenceException {

        return dao.getOneSnack(snackId);
    }

    @Override
    public Snack getOneSnack(String snackId, BigDecimal userMoney)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        //is the selection valid
        validateSelection(snackId);
        //is there inventory for the selected item
        checkInventory(snackId);
        //is there enough money
        sufficientMoney(snackId, userMoney);

        return dao.getOneSnack(snackId);
    }

    @Override
    public void updateSnack(String snackId) throws SnackInventoryPersistenceException {
        dao.updateSnack(snackId); 
    }

    @Override
    public Map<Coin, BigDecimal> makeChange(BigDecimal changeDue) {
        return dao.makeChange(changeDue); 
    }
    
    //Helper Methods ***********************************************************

    private void validateSelection(String selection)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {
        
        if (dao.getOneSnack(selection) == null) {
            throw new VendingMachineInvalidSelectionException("ERROR: That"
                    + " is not a valid selection.");
        }
    }

    private void checkInventory(String snackId)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {
        
        Snack selectedSnack = dao.getOneSnack(snackId);
        if (selectedSnack.getQuantity() < 1) {
            throw new VendingMachineInvalidSelectionException("ERROR: Sorry,"
                    + " that's out of stock.");
        }
    }

    private void sufficientMoney(String snackId, BigDecimal userMoney)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {
        
        Snack selectedSnack = dao.getOneSnack(snackId);

        if (userMoney.compareTo(selectedSnack.getPrice()) < 0) {
            throw new VendingMachineInvalidSelectionException("ERROR: Sorry, "
                    + "you don't have enough money to buy that. ");
        }
    }

}
