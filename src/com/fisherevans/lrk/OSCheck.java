package com.fisherevans.lrk;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/6/13
 * Time: 6:59 PM
 */
public class OSCheck
{
    public static String OS = System.getProperty("os.name").toLowerCase();

    public enum OSType { Windows, Mac, Linux, Solaris }

    /**
     * @return the OS enum of the current OS
     */
    public static OSType getOSType()
    {
        if(OS.indexOf("win") >= 0) return OSType.Windows;
        if(OS.indexOf("mac") >= 0) return OSType.Mac;
        if(OS.indexOf("nix") >= 0) return OSType.Linux;
        if(OS.indexOf("sunos") >= 0) return OSType.Solaris;

        System.out.println("Waring! OS type is undetermined, defaulting to Linux!!!");
        return OSType.Linux;
    }
}
