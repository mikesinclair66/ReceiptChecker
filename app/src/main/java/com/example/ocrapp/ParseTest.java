package com.example.ocrapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTest
{
    public static void useRegexForPrices(final String input, ArrayList<Double> prices) {
        final String regex = "[0-9]*\\.[0-9]+";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));
            prices.add(Double.parseDouble(matcher.group(0)));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void useRegexForDates(final String input, ArrayList<String> dates) {
        String[] regexes = {
                "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$",
                "^.*([0-9]+(/[0-9]+)+)*.,.*$",
                "(0[1-9]|1[012])[- \\/.](0[1-9]|[12][0-9]|3[01])[- \\/.](19|20)\\d\\d",
                "^.*([0-9]+(/[0-9]+)+)*.([0-9]+(/[0-9]+)+)*.([0-9]+(-[0-9]+)+)*.([0-9]+(/[0-9]+)+)*.([0-9]+(-[0-9]+)+)*.([0-9]+(-[0-9]+)+).*$",

        };
        int iterator = 0;
        for (String i : regexes) {
            try {
                Pattern pattern = Pattern.compile(i, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(input);
                if(matcher.find()){
                    System.out.println("Date Match Found using regex: "
                            + iterator + " result: " + matcher);
                    dates.add(matcher.group(0));
                    return;
                }
                iterator++;
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatDate(String str) {
        String[] patterns = {"d-M-yyyy", "MMM d, yyyy", "d/M/yyyy"};
        DateTimeFormatter formatter;
        LocalDate date = null;
        for (String i : patterns) {
            try {
                formatter = DateTimeFormatter.ofPattern(i, Locale.ENGLISH);
                date = LocalDate.parse(str, formatter);
                System.out.println("Formatted: " + date);
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return (date != null) ? date.toString() : str;
    }

    public static double returnHighestDouble(ArrayList<Double> prices){
        Double highestDouble;
        Collections.sort(prices);
        Collections.reverse(prices);
        highestDouble = prices.get(0);
        return highestDouble;
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void main(String[] argv) throws Exception
//    {
//        StringBuilder str = new StringBuilder(
//                "Main Street Restaurant\n" +
//                "6332 Business Drive\n" +
//                "Suite 528\n" +
//                "Palo Alto California 94301\n" +
//                "575-1628095\n" +
//                "Fri 04/07/2017 11:36 AM\n" +
//                "Something 04/07/2017 Something \n" +
//                "Something Jan 17, 2017 Something \n" +
//                "Something 01-02-2018 Something \n" +
//                "04/07/2017\n" +
//                "Jan 17, 2017\n" +
//                "01-02-2018\n" +
//                "Merchant ID:\n" +
//                "Terminal ID:\n" +
//                "9hqjxvufdr\n" +
//                "11111\n" +
//                "Transaction ID: #e6d598ef\n" +
//                "Type:\n" +
//                "CREDIT\n" +
//                "PURCHASE\n" +
//                "XXXXXXXXXXXXO041\n" +
//                "Number\n" +
//                "Entry Mode:\n" +
//                "Card Type:\n" +
//                "Swiped\n" +
//                "DISCOVER\n" +
//                "Response:\n" +
//                "Approval Code:\n" +
//                "APPROVED\n" +
//                "819543\n" +
//                "Sub Total\n" +
//                "USD $25.23\n" +
//                "3.78\n" +
//                "Tip:\n" +
//                "Total\n" +
//                "USD$ 29.01\n" +
//                "Thanks for supporting\n" +
//                "local business!\n" +
//                "THANK YOU"
//        );
//
//        ArrayList<Double> prices = new ArrayList<Double>();
//        useRegexForPrices(str.toString(), prices);
//        System.out.println(prices);
//        System.out.println(returnHighestDouble(prices));
//
//        ArrayList<String> dates = new ArrayList<String>();
//        useRegexForDates(str.toString(), dates);
//        System.out.println(dates);
//    }

}
