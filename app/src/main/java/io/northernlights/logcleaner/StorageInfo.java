package io.northernlights.logcleaner;

/*
 * LogCleaner - Helps you to save Internal Storage space deleting log folders
 * Copyright (C) 2014 Alessandro Garzaro
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.

 * You should have received a copy of the GNU General Public License along with
 * this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.text.DecimalFormat;

public class StorageInfo {
    public static long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());

        long   Total  = (long)statFs.getBlockCount()  * (long)statFs.getBlockSize();

        return Total;
    }

    public static long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());

        long   Free   = statFs.getAvailableBlocks() * (long) statFs.getBlockSize();

        return Free;
    }

    public static long UsedMemory()
    {
        return TotalMemory() - FreeMemory();
    }

    public static long folderSize(File directory) {
        long size = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile())
                size += file.length();
            else
                size += folderSize(file);
        }

        return size;
    }


    private static String floatForm (double d)
    {
        return new DecimalFormat("#.##").format(d);
    }


    public static String printHuman (long size, String format)
    {
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (format == "auto") {
            if (size < Kb)               return floatForm(size) + " Byte";
            if (size >= Kb && size < Mb) return floatForm((double) size / Kb) + " KB";
            if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + " MB";
            if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + " GB";
            if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + " TB";
            if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + " PB";
            if (size >= Eb)              return floatForm((double) size / Eb) + " EB";
        }
        if ( format == "none" ) {
            return floatForm(size);
        }
        if ( format == "MB") {
            return floatForm((double) size / Mb) + " MB";
        }
        if ( format == "GB") {
            return floatForm((double) size / Gb) + " GB";
        }

        return "N/A";
    }
}
