package cl.holapancho.clijv.util;

public class StringUtil {
    private StringUtil(){
        //empty private constructor
    }
    public static boolean isNotBlank(String str){
        return str != null && !str.trim().isEmpty();
    }
}