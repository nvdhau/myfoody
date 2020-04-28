package com.douglas.myfoody.core.utilities;

import com.douglas.myfoody.core.models.User;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Utils {
    private static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat df = new SimpleDateFormat(STANDARD_DATE_FORMAT);

    public static Date getToday() {
        return new Date(System.currentTimeMillis());
    }

    public static String formatDate(Date d) {
        return df.format(d);
    }

    public static boolean checkExpired(Date d) {
        Date today = getToday();
        if(today.after(d))
            return true;
        else
            return false;
    }

    public static boolean checkExpired(String strD) {
        try {
            Date input = df.parse(strD);
            return checkExpired(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
}
