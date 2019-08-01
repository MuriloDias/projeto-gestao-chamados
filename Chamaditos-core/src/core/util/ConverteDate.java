package core.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ConverteDate {

	public static String converteDateString(Date dtData) {
		SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy");
		return (formatBra.format(dtData));
	}
	public static Date converteStringDate(String data) {
		if(data == null || data.contentEquals(""))
			return null;
		
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date)formatter.parse(data);
			return date;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String converteDateStringHora(Date dtData) {
		SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return (formatBra.format(dtData));
	}
	public static Date somaMinutosDate(Integer min, Date data) {
		GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.add(Calendar.MINUTE, min);
		return gc.getTime();
	}
	
	public static Date converteStringDateAnalise(String data)
	{
		if(data == null || data.contentEquals(""))
			return null;
		
		Date date = null;
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = (java.util.Date)formatter.parse(data);
			return date;
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		/*
		 * 
		 * //Primeiro converte de String para Date
			DateFormat formatUS = new SimpleDateFormat("yyyy-mm-dd");
			Date date = formatUS.parse(SuaStringComAData);
			
			//Depois formata data
			DateFormat formatBR = new SimpleDateFormat("dd-mm-yyyy");
			String dateFormated = formatBR.format(date);
		 */
		 
	}
}
