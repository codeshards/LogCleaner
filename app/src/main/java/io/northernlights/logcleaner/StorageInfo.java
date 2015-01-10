package io.northernlights.logcleaner;

/**
 * Created by alessandro on 09/01/15.
 */

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.text.DecimalFormat;

public class StorageInfo {
    /*************************************************************************************************
     Returns size in bytes.

     If you need calculate external memory, change this:
     StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
     to this:
     StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
     **************************************************************************************************/
    public static long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());

        long   Total  = ( (long) statFs.getBlockCount()  * (long) statFs.getBlockSize());

        return Total;
    }

    public static long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());

        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());

        return Free;
    }

    public static long UsedMemory()
    {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());

        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;

        return Busy;
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

        return "???";
    }
}
