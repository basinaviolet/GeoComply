package utils;

import java.util.concurrent.TimeUnit;

public class Waiter {

    public static void waitFor(int millis, String reason){
        try{
            String message = String.format("Waiting %d ms for %s", millis, reason);
            TimeUnit.MILLISECONDS.sleep(millis);
            System.out.println(message);
        } catch(InterruptedException ex){
            Thread.currentThread().interrupt();
            ex.printStackTrace();
        }
    }
}
