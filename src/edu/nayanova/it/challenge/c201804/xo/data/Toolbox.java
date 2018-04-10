package edu.nayanova.it.challenge.c201804.xo.data;

public class Toolbox {
    public static String BytetoChar(byte sign)
    {
        switch (sign)
        {
            case 0:
                return "-";
            case 1:
                return "X";
            case 2:
                return "O";
            default:
                return "Error";
        }
    }
}
