/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.SnackInventoryPersistenceException;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author timpinkerton
 */
public interface VendingMachineServiceLayer {
        
    //get all snacks
    List<Snack> getAllSnacks() throws SnackInventoryPersistenceException; 
    
    //get one snack with snackId and UserMoney
    Snack getOneSnack(String snackId, BigDecimal userMoney) 
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException; 
    
    //get one snack with just snackId
    Snack getOneSnack(String snackId) throws SnackInventoryPersistenceException;
            
    //update one snack to reduce quantity
    void updateSnack(String snackId) throws SnackInventoryPersistenceException; 
            
    //make change
    Map<Coin, BigDecimal> makeChange(BigDecimal changeDue); 
    
}
