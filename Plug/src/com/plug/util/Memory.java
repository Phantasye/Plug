package com.plug.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Memory {
	
	public static void handleMemoryUsage() {
        DecimalFormat decimalFormat = new DecimalFormat("0.0#%");
        NumberFormat memoryFormat = NumberFormat.getInstance();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        Log.printInfo("Total Used Memory: "
                + memoryFormat.format(usedMemory / 1024)
                + "/"
                + memoryFormat.format(totalMemory / 1024)
                + " MB, "
                + decimalFormat
                        .format(((double) usedMemory / (double) totalMemory)));
        Log.printInfo("Free Memory: "
                + memoryFormat.format(freeMemory / 1024) + " MB, " + decimalFormat.format((double) freeMemory / (double) totalMemory));
    }

}
