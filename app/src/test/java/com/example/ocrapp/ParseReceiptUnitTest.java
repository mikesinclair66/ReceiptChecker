package com.example.ocrapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.ArrayList;

public class ParseReceiptUnitTest {
    StringBuilder stringBuilder = new StringBuilder(
            "Main Street Restaurant\n" +
                    "6332 Business Drive\n" +
                    "Suite 528\n" +
                    "Palo Alto California 94301\n" +
                    "575-1628095\n" +
                    //"Fri 04/07/2017 11:36 AM\n" +
                    //"04/07/2017\n" +
                    //"Jan 17, 2017\n" +
                    //"01-02-2018\n" +
                    "Date & time: 05-05-18 13:31\n" +
                    "Merchant ID:\n" +
                    "Terminal ID:\n" +
                    "9hqjxvufdr\n" +
                    "11111\n" +
                    "Transaction ID: #e6d598ef\n" +
                    "Type:\n" +
                    "CREDIT\n" +
                    "PURCHASE\n" +
                    "XXXXXXXXXXXXO041\n" +
                    "Number\n" +
                    "Entry Mode:\n" +
                    "Card Type:\n" +
                    "Swiped\n" +
                    "DISCOVER\n" +
                    "Response:\n" +
                    "Approval Code:\n" +
                    "APPROVED\n" +
                    "819543\n" +
                    "Sub Total\n" +
                    "USD $25.23\n" +
                    "3.78\n" +
                    "Tip:\n" +
                    "Total\n" +
                    "USD$ 29.01\n" +
                    "Thanks for supporting\n" +
                    "local business!\n" +
                    "THANK YOU"
    );

    StringBuilder stringBuilder2 = new StringBuilder(
            "Main Street Restaurant\n" +
                    "6332 Business Drive\n" +
                    "Suite 528\n" +
                    "Palo Alto California 94301\n" +
                    "575-1628095\n" +
                    //"Fri 04/07/2017 11:36 AM\n" +
                    "04/07/2017\n" +
                    //"Jan 17, 2017\n" +
                    //"01-02-2018\n" +
                    //"Date & time: 05-05-18 13:31\n" +
                    "Merchant ID:\n" +
                    "Terminal ID:\n" +
                    "9hqjxvufdr\n" +
                    "11111\n" +
                    "Transaction ID: #e6d598ef\n" +
                    "Type:\n" +
                    "CREDIT\n" +
                    "PURCHASE\n" +
                    "XXXXXXXXXXXXO041\n" +
                    "Number\n" +
                    "Entry Mode:\n" +
                    "Card Type:\n" +
                    "Swiped\n" +
                    "DISCOVER\n" +
                    "Response:\n" +
                    "Approval Code:\n" +
                    "APPROVED\n" +
                    "819543\n" +
                    "Sub Total\n" +
                    "USD $25.23\n" +
                    "3.78\n" +
                    "Tip:\n" +
                    "Total\n" +
                    "35.50\n" +
                    //"USD$ 29.01\n" +
                    "Thanks for supporting\n" +
                    "local business!\n" +
                    "THANK YOU"
    );

    StringBuilder stringBuilder3 = new StringBuilder(
            "Main Street Restaurant\n" +
                    "6332 Business Drive\n" +
                    "Suite 528\n" +
                    "Palo Alto California 94301\n" +
                    "575-1628095\n" +
                    "Fri 04/07/2017 11:36 AM\n" +
                    //"04/07/2017\n" +
                    //"Jan 17, 2017\n" +
                    //"01-02-2018\n" +
                    //"Date & time: 05-05-18 13:31\n" +
                    "Merchant ID:\n" +
                    "Terminal ID:\n" +
                    "9hqjxvufdr\n" +
                    "11111\n" +
                    "Transaction ID: #e6d598ef\n" +
                    "Type:\n" +
                    "CREDIT\n" +
                    "PURCHASE\n" +
                    "XXXXXXXXXXXXO041\n" +
                    "Number\n" +
                    "Entry Mode:\n" +
                    "Card Type:\n" +
                    "Swiped\n" +
                    "DISCOVER\n" +
                    "Response:\n" +
                    "Approval Code:\n" +
                    "APPROVED\n" +
                    "819543\n" +
                    "Sub Total\n" +
                    "USD $25.23\n" +
                    "3.78\n" +
                    "Tip:\n" +
                    "Total\n" +
                    "$99.99\n" +
                    //"USD$ 29.01\n" +
                    "Thanks for supporting\n" +
                    "local business!\n" +
                    "THANK YOU"
    );


    @Test
    public void Date_isCorrect() {
        ArrayList<String> dates = new ArrayList<>();
        String strDate = "Date & time: 05-05-18 13:31";
        String date = null;
        ParseTest.useRegexForDates(stringBuilder.toString(), dates);
        if (!dates.isEmpty()) {
            date = dates.get(0);
            System.out.println("date: " + date);
        } else {
            System.out.println("date is empty");
        }
//        for (String i : dates) {
//            i = ParseTest.formatDate(i);
//        }
        assertEquals(strDate, date);
    }

    @Test
    public void Price_IsCorrect() {
        ArrayList<Double> prices = new ArrayList<>();
        double price = 29.01;
        double highest;

        ParseTest.useRegexForPrices(stringBuilder.toString(), prices);
        if (!prices.isEmpty()) {
            highest = ParseTest.returnHighestDouble(prices);

        } else {
            highest = 0;
        }

        assertEquals(price, highest, 0.001);

    }

    @Test
    public void Price_IsCorrect2() {
        ArrayList<Double> prices = new ArrayList<>();
        double price = 35.50;
        double highest;

        ParseTest.useRegexForPrices(stringBuilder2.toString(), prices);
        if (!prices.isEmpty()) {
            highest = ParseTest.returnHighestDouble(prices);

        } else {
            highest = 0;
        }

        assertEquals(price, highest, 0.001);

    }

    @Test
    public void Date_isCorrect2() {
        ArrayList<String> dates = new ArrayList<>();
        String strDate = "04/07/2017";
        String date = null;
        ParseTest.useRegexForDates(stringBuilder2.toString(), dates);
        if (!dates.isEmpty()) {
            date = dates.get(0);
            System.out.println("date: " + date);
        } else {
            System.out.println("date is empty");
        }
//        for (String i : dates) {
//            i = ParseTest.formatDate(i);
//        }
        assertEquals(strDate, date);
    }

    @Test
    public void Price_IsCorrect3() {
        ArrayList<Double> prices = new ArrayList<>();
        double price = 99.99;
        double highest;

        ParseTest.useRegexForPrices(stringBuilder3.toString(), prices);
        if (!prices.isEmpty()) {
            highest = ParseTest.returnHighestDouble(prices);

        } else {
            highest = 0;
        }

        assertEquals(price, highest, 0.001);

    }

    @Test
    public void Date_isCorrect3() {
        ArrayList<String> dates = new ArrayList<>();
        String strDate = "04/07/2017";
        String date = null;
        ParseTest.useRegexForDates(stringBuilder3.toString(), dates);
        if (!dates.isEmpty()) {
            date = dates.get(0);
            System.out.println("date: " + date);
        } else {
            System.out.println("date is empty");
        }
//        for (String i : dates) {
//            i = ParseTest.formatDate(i);
//        }
        assertEquals(strDate, date);
    }



}
