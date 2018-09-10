/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.SnackInventoryPersistenceException;
import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.service.VendingMachineInvalidSelectionException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author timpinkerton
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    //Constructor
    public VendingMachineController(VendingMachineView view, 
            VendingMachineServiceLayer service) {
        
        this.view = view;
        this.service = service;
    }

    public void run() {

        int continueOrExit;

        try {
            displayOptions();

            continueOrExit = getContinueOrExit();

            BigDecimal changeDue;

            switch (continueOrExit) {
                case 1:
                    changeDue = makePurchase();
                    getChange(changeDue);
                    exitMessage();
                    break;
                case 2:
                    exitMessage();
                    break;
                default:
                    unknownCommand();
            }

        } catch (VendingMachineInvalidSelectionException e) {
            view.displayErrorMessage(e.getMessage());
        } catch (SnackInventoryPersistenceException e) {

        }

    }

    //Controller methods *******************************************************
    private void displayOptions()
            throws SnackInventoryPersistenceException {

        List<Snack> allSnacks = service.getAllSnacks();
        view.displayOptions(allSnacks);
    }

    private int getContinueOrExit() {
        return view.continueOrExit();
    }

    private BigDecimal makePurchase()
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        BigDecimal userMoney = BigDecimal.ZERO;
        BigDecimal changeDue = BigDecimal.ZERO;
        String userSelection = "";
        int subMenuChoice = 1;

        do {

            switch (subMenuChoice) {
                case 1:
                    userMoney = getUserMoney(userMoney);
                    //no break. This will always fall through to case 2.  After
                    //  user is done entering money (case 1), they will be 
                    // prompted for a selection (case 2). 
                case 2:
                    userSelection = getUserSelection(userMoney);
                    //if an exception is thrown (userSelection is not valid),
                    //  the submenu will display

                    if (userSelection.isEmpty()) {
                        //display submenu
                        view.displayCurrentBalance(userMoney);
                        subMenuChoice = view.displaySubMenu();
                        break;
                    }

                    //if the selection is valid: 
                    Snack snackToDispense = service.getOneSnack(userSelection);
                    //qty will be updated
                    updateQty(snackToDispense);
                    //change due is calculated
                    changeDue = userMoney.subtract(snackToDispense.getPrice());
                    
                    view.displaySelectionAndChangeDue(snackToDispense, changeDue);

                    return changeDue;

                default:
                    unknownCommand();
            }

        } while (subMenuChoice != 3);

        //if the user does not buy anything and decides to exit, they get 
        //  all the money back
        changeDue = userMoney;
        view.displayNoPurchaseMessage();
        return changeDue;

    }

    private BigDecimal getUserMoney(BigDecimal userMoney) {

        int enterMoreMoney;
        BigDecimal moneyToAdd;
        BigDecimal newMoney;

        do {
            moneyToAdd = view.promptForMoney();
            newMoney = userMoney.add(moneyToAdd);
            userMoney = newMoney;
            view.displayCurrentBalance(userMoney);
            enterMoreMoney = view.enterMoreMoney();

        } while (enterMoreMoney == 1);

        return userMoney;
    }

    private String getUserSelection(BigDecimal userMoney)
            throws VendingMachineInvalidSelectionException,
            SnackInventoryPersistenceException {

        String selection = "";

        selection = view.promptForSelection();
        try {
            service.getOneSnack(selection, userMoney);

        } catch (VendingMachineInvalidSelectionException e) {
            selection = "";
            view.displayErrorMessage(e.getMessage());
        }

        return selection;

    }

    private void unknownCommand() {
        view.displayUnknownCommand();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }

    //getChange() is a helper method in the controller that will call 
    //  makeChange() from the service layer, which will return
    //  the number of each type of coin to be returned to the user.
    private void getChange(BigDecimal changeDue) {

        Map<Coin, BigDecimal> listOfChange = service.makeChange(changeDue);
        
        view.displayChange(listOfChange);

    }

    private void updateQty(Snack snackToDispense) 
            throws SnackInventoryPersistenceException {
        service.updateSnack(snackToDispense.getSnackId());
    }

}
