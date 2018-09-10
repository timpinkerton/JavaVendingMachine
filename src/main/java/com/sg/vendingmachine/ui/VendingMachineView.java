/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Coin;
import com.sg.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 *
 * @author timpinkerton
 */
public class VendingMachineView {

    private UserIO io;

    //Constructor 
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayOptions(List<Snack> allSnacks) {
        io.print("===========VENDING MACHINE========");

        for (Snack snack : allSnacks) {
            io.print(" ");
            io.print("----------------------------------");
            io.print("Selection ID: " + snack.getSnackId());
            io.print("Snack Name: " + snack.getName());
            io.print("Price: $" + snack.getPrice());
            if (snack.getQuantity() > 0) {
                io.print("Qty: " + snack.getQuantity());
            } else {
                io.print("Qty: OUT OF STOCK");
            }
            io.print("----------------------------------");
            io.print(" ");
        }

        io.print("==================================");
        io.print(" ");
    }

    public int continueOrExit() {
        return io.readInt("Press \"1\" to make a purchase or \"2\" to exit: ", 1, 2);
    }

    public BigDecimal promptForMoney() {
        BigDecimal min = new BigDecimal("0.01");
        BigDecimal max = new BigDecimal("20.00");
        io.print(" ");
        io.print("Enter a minimum of $0.01 and maximum of $20 ");
        return io.readBigDecimal("Please enter your money: ", min, max);
    }

    public String promptForSelection() {
        return io.readString("Please enter the ID to dispense your snack: ");
    }

    public int enterMoreMoney() {
        return io.readInt("Press \"1\" to enter more money or \"2\" to continue: ", 1, 2);
    }

    public void displayCurrentBalance(BigDecimal userMoney) {
        io.print(" ");
        io.print("Your current balance is: $" + userMoney.setScale(2, RoundingMode.HALF_UP));
    }

    public void displayUnknownCommand() {
        io.print("Unknown Command!!!");
    }

    public void displayExitMessage() {
        io.print("Good Bye.");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print(" ");
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public int displaySubMenu() {
        io.print(" ");
        io.print("============================================");
        io.print("What would you like to do?");
        io.print(" ");
        io.print("1. Enter more money.");
        io.print("2. Try another selection.");
        io.print("3. Exit and get any available balance back.");
        io.print(" ");
        return io.readInt("Make a choice from the menu above: ", 1, 3);
    }

    public void displaySelectionAndChangeDue(Snack snackToDispense, BigDecimal changeDue) {
        io.print(" ");
        io.print("You chose " + snackToDispense.getName() + ", great choice.");
        io.print("Change due: $" + changeDue);
    }

    public void displayChange(Map<Coin, BigDecimal> listOfChange) {

        io.print(" ");

        for (Coin coin : Coin.values()) {
            io.print("Number of " + coin.name() + ": " + 
                    listOfChange.get(coin).setScale(0, RoundingMode.DOWN));
        }
    }

    public void displayNoPurchaseMessage() {
        io.print(" ");
        io.print("You didn't buy anything. Here is your money back in change: ");
    }

}
