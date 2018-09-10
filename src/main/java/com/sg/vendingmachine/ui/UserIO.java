/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author timpinkerton
 */
public interface UserIO {

    void print(String message);

    double readDouble(String promptMsg);

    double readDouble(String promptMsg, double minVal, double maxVal);

    float readFloat(String promptMsg);

    float readFloat(String promptMsg, float minVal, float maxVal);

    int readInt(String promptMsg);

    int readInt(String promptMsg, int minVal, int maxVal);

    long readLong(String promptMsg);

    long readLong(String promptMsg, long minVal, long maxVal);
    
    BigDecimal readBigDecimal(String promptMsg); 
    
    BigDecimal readBigDecimal(String promptMsg, BigDecimal minVal, BigDecimal maxVal);

    String readString(String promptMsg);
    
    Date readDate(String promptMsg); 
}
