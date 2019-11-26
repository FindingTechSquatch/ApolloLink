/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.math.*;

/**
 *
 * @author tyleryork
 */
public class Validation {
    
    


public class ValidityTests
{

//*****************************************
/*this method tests to see if the user entered data into a String field.
You pass this method a String, then call a method from the String class called isEmpty()
if nothing is in the field, it will display an error message and return false, if the field is not empty
it will return a true
*/
   public boolean isStringPresent(String stringData, String title)
   {
   	
	if(stringData.isEmpty())
	{
            System.out.println(title + " must be present, please re-enter: \n");
            return false;
	}
		
        return true;
   }

//******************************************
/*This method tests to make sure that no characters are entered into a numeric field.
 * The method accepts the data as a String, passes that data along with the field name to this method.
 * Within the try statement, the String is parsed(changed to an integer).  If there are no characters
 * in the field, it returns a true(the data is good).  
 * If there are characters in the field, it drops to the catch statement, displays an error message,
 * and returns a false.
 */
    public boolean isInteger(String intData, String title)
    {
	try
	{
            int i = Integer.parseInt(intData);
            return true;
	}
        catch (NumberFormatException e)
        {
            System.out.println(title + " must be an integer. Please re-enter.");
            return false;
        }
    }
	
//******************************************
/*This method tests to make sure that the user entered a valid range into the field.
 * For example if you wanted to test an age, and the requirement was a minimum of 18 years old,
 * that would be your min, and the maximum age couldn't be more than 120, that would be your max.
 * This method accepts what the user entered as a String, then parses it to an Integer.  
 * You pass the min and max values that you want tested and a String that explains what is being tested.
 * In my example above, the String that you pass would probably hold "Age", so that the error message
 * would say "Age must be between 18 and 120, Please re-enter"
 * The integer is tested to make sure that it falls with the ranges, if it does, it returns a true, if 
 * it does not, it returns a false.
 */
    public boolean isWithinRangeInteger(String intData, int min, int max, String title)
    {
	int i = Integer.parseInt(intData);
	if (i < min | i > max)
	{
            System.out.println(title + " must be between " + min + " and " + max + ". " + " Please re-enter.");
            return false;
	}
	return true;
    }
  
   //************************************************** 
	//method that tests for numeric data for a long integer
	public boolean isLong(String longData, String title)
	{
		try
		{
			long i = Long.parseLong(longData);
			return true;
		}
		catch (NumberFormatException e)
		{
		    System.out.println(title + " must be an integer. Please re-enter.");
			return false;
		}
	}
	//method that tests a range using a long integer
	public boolean isWithinRangeLong(String longData, long min, long max, String title)
	{
		long i = Long.parseLong(longData);
		if (i < min | i > max)
		{
			System.out.println(title + " must be between " + min + " and " + max + ". " + " Please re-enter.");
			return false;
		}
		return true;
	}
	
//This method is the same as isInteger, but tests a double
	public boolean isDouble(String doubleData, String title)
	{
		try
		{
			double d = Double.parseDouble(doubleData);
			return true;
		}
		catch (NumberFormatException e)
		{
		    System.out.println(title + " must be a numeric value. Please re-enter.");
			return false;
		}
	}
	
//******************************************
//This method is the same as isWithinRangeInteger, but tests a double
	public boolean isWithinRangeDouble(String doubleData, double min, double max, String title)
	{
		double d = Double.parseDouble(doubleData);
		if (d < min | d > max)
		{
			System.out.println(title + " must be between " + min + " and " + max + ". " + " Please re-enter.");
			return false;
		}
		return true;
	}
//******************************************
//This method accepts a String argument, then tests it for a valid date
	public boolean isDateValid(String date)
	{		
		try
		{
			
			LocalDate parsedDate = LocalDate.parse(date);//parses the String into a LocalDate object
			
						
			return true;
		}
		catch (DateTimeParseException e)
		{
			System.out.println("Date must be in this format: yyyy-mm-dd & must be a valid date " + ".\n" + "Please re-enter.");
		
			return false;
		}
	}

	 
 }
    
}
