package io.northernlights.logcleaner;

/*
 * LogCleaner - Helps you to save Internal Storage space deleting log folders
 * Copyright (C) 2014 Alessandro Garzaro
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
