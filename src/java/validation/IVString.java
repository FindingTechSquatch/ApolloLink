/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tyleryork
 */
public class IVString {

    public static boolean ContainsText(String s) {
        boolean b;

        b = !(s == null || s.trim().isEmpty());

        return b;
    }

    public static boolean LengthGTET(String s, int min) { //String length greater than equal to
        boolean b;

        b = (s.trim().length() >= min);

        return b;
    }

    public static boolean LengthLTET(String s, int min) { //String length less than equal to
        boolean b;

        b = (s.trim().length() <= min);

        return b;
    }

    public static boolean IsNumeric(String s) {
        boolean b = false;

        try {
            Double.parseDouble(s);
            b = true;
        } catch (NumberFormatException nex) {
            b = false;
        } catch (Exception ex) {
            b = false;
        }

        return b;
    }
    
    public static boolean ContainsSpecials(String s) {
        int errCount = 0;
        
        ArrayList<String> specialArray = new ArrayList<String>(Arrays.asList("~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
            "+", "=", "{", "}", "[", "]", ":", ";", "'", "<", ">", ",", ".", "?", "/", "\\", "|"));
           for(int i = 0; i < specialArray.size(); i++) {
               if(s.contains(specialArray.get(i))){
                   errCount++;
               }
           }
    
        
        return (errCount>0);
    }

    public static boolean MatchesRegex(String s, String r) {
        boolean b;

        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(s);
        b = m.matches();

        return b;
    }

    public static String dateReformatter(String s) {
        Pattern p1 = Pattern.compile("^(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])$"); // yyyy-mm-dd
        Pattern p2 = Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$"); // mm/dd/yyyy
        Pattern p3 = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$"); // dd-mm-yyyy

        Matcher m1 = p1.matcher(s);
        Matcher m2 = p2.matcher(s);
        Matcher m3 = p3.matcher(s);

        if (m1.matches()) {

        } else if (m2.matches()) {
            s = s.substring(6, 10) + "-" + s.substring(0, 2) + "-" + s.substring(3, 5);
        } else if (m3.matches()) {
            s = s.substring(6, 10) + "-" + s.substring(3, 5) + "-" + s.substring(0, 2);
        } else if (s.length() < 10) {
                s = s.replace("/", "-");
                s = s.substring(s.lastIndexOf("-")+1, s.length())+"-"+s.substring(0, s.indexOf("-"))+"-"
                        +s.substring(s.indexOf("-")+1,s.lastIndexOf("-"));
        }

        return s;
    }
    
    public static String regexLib(String typ){
        String rtn = null;
        String email = 
"(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    
        if(typ.equalsIgnoreCase("email")){
            rtn = email;
        }
        return rtn;
    }
}
