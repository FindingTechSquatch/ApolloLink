/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static java.util.Objects.hash;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.catalina.realm.SecretKeyCredentialHandler;

/**
 *
 * @author tyleryork
 */
public class ec {

    public static String EC_dpw(String ec) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // digest() method called  
            // to calculate message digest of an input  
            // and return array of byte 
            byte[] me = md.digest((ec + "YvqlQH7CwKgbK4VbsaK2atDvXZ6L").getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, me);

            // Convert message digest into hex value  
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros 
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            String h = hexString.toString();

            return h;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ec.class.getName()).log(Level.SEVERE, null, ex);
            //DO SOMETHING HERE
            return null;
        } catch (Exception ex) {
            //DO SOMETHING HERE
            return null;
        }
    }


    public static String EC_dus(String ec) {
       
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            ec = ec.toUpperCase();
            // digest() method called  
            // to calculate message digest of an input  
            // and return array of byte 
            byte[] me = md.digest((ec + "gC3cQIsGyeC3eVosz9S8qrAWrWa9").getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, me);

            // Convert message digest into hex value  
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros 
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            String h = hexString.toString();

            return h;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ec.class.getName()).log(Level.SEVERE, null, ex);
            //DO SOMETHING HERE
            return null;
        } catch (Exception ex) {
            //DO SOMETHING HERE
            return null;
        }
    }

    public static String EC_epw(String ec) {
            
            
            try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method called  
            // to calculate message digest of an input  
            // and return array of byte 
            byte[] me = md.digest((ec + "PuPs8QA20h6splz6BuUoR6h6MR1g").getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, me);

            // Convert message digest into hex value  
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros 
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            String h = hexString.toString();

            return h;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ec.class.getName()).log(Level.SEVERE, null, ex);
            //DO SOMETHING HERE
            return null;
        } catch (Exception ex) {
            //DO SOMETHING HERE
            return null;
        }
    }

    public static String EC_eus(String ec) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            ec = ec.toUpperCase();
            // digest() method called  
            // to calculate message digest of an input  
            // and return array of byte 
            byte[] me = md.digest((ec + "tZC5EPXI404t5VxfeEB6z0nb4Iic").getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, me);

            // Convert message digest into hex value  
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros 
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            String h = hexString.toString();

            return h;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ec.class.getName()).log(Level.SEVERE, null, ex);
            //DO SOMETHING HERE
            return null;
        } catch (Exception ex) {
            //DO SOMETHING HERE
            return null;
        }
    }
}
