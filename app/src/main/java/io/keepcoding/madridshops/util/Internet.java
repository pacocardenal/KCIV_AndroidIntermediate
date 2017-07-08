package io.keepcoding.madridshops.util;

import java.io.IOException;

public class Internet {
    public static boolean isInternetAvailable() {
        String command = "ping -c 1 google.com";
        try {
            return (Runtime.getRuntime().exec (command).waitFor() == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
