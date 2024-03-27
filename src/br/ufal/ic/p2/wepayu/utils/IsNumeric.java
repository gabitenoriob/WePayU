package br.ufal.ic.p2.wepayu.utils;

public class IsNumeric {
    public static boolean isNumeric(String str) {
        //System.out.println(Double.parseDouble(str));
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
