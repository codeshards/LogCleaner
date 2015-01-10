package io.northernlights.logcleaner;

import java.io.DataOutputStream;
import java.io.IOException;

public class StorageManager {

    public static Boolean performCleaningAtBoot() {
        Boolean result = false;

        try {
            Process suProcess = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

            // let's destroy everything yaaaaiiiii!!!!!
            os.writeBytes("rm -f /data/log_other_mode/*\n");
            os.flush();

            os.writeBytes("exit\n");
            os.flush();

            try {
                int suProcessRetval = suProcess.waitFor();
                if ( suProcessRetval != 255) {
                    // Root Access granted
                    result = true;
                } else {
                    // Root Access denied
                    result = false;
                }
            } catch (Exception ex) {

            }

        } catch (IOException ex) {

        } catch (SecurityException ex) {

        } catch (Exception ex) {

        }

        return result;
    }

}
