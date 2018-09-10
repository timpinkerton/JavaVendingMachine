/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.main;

import com.sg.vendingmachine.controller.VendingMachineController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author timpinkerton
 */
public class App {

    public static void main(String[] args) {

//        UserIO myIo = new UserIOConsoleImpl(); 
//        VendingMachineView myView = new VendingMachineView(myIo); 
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl(); 
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao); 
//        VendingMachineController controller = new VendingMachineController(myView, myService); 
//        controller.run();

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller
                = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
        
    }
}
