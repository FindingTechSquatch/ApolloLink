/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tyleryork
 */
public class IVDateTime {
    
    public static DateTimeFormatter dtFormatPicker (String s) {
        DateTimeFormatter dtf = null;
        Pattern p1 = Pattern.compile("^(19|20)\\d\\d([- /.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])$"); // yyyy-mm-dd
        Pattern p2 = Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$"); // mm/dd/yyyy
        Pattern p3 = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$"); // dd-mm-yyyy
        
        Matcher m1 = p1.matcher(s);
        Matcher m2 = p2.matcher(s);
        Matcher m3 = p3.matcher(s);
        
        if(m1.matches()) {
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else if (m2.matches()) {
            dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        } else if (m3.matches()) {
            dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        } 
        
        return dtf;
    }
    
}
