/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.advice;

import com.sg.vendingmachine.dao.SnackInventoryPersistenceException;
import org.aspectj.lang.JoinPoint;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;

/**
 *
 * @author timpinkerton
 */
public class LoggingAdvice {

    VendingMachineAuditDao auditDao;

    //using constructor injection to allow LoggingAdvice to use the auditDao
    public LoggingAdvice(VendingMachineAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void createAuditEntry(JoinPoint jp, Throwable ex) {
        
        //join points gets the arguments and puts them in an array
        //  args are parameters in getOneSnack (String, BidDecimal)
        Object[] args = jp.getArgs();
        
        //jp.getSignature().getName() gets the name of the method (in this case
        //  "addStudent()")
        //This is the first part of the auditEntry
        String auditEntry = 
                "Method: " + jp.getSignature().getName() + " | " + 
                ex.getMessage() + 
                " | Selection ID: " + args[0] + 
                " | User money: " + args[1];
        
        
        //replacing this for loop with the String concatenation above
        // to better format the audit log entries
//        for (Object currentArg : args) {
//            auditEntry += currentArg;
//        }

        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (SnackInventoryPersistenceException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }

    }

}
