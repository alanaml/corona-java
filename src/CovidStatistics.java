import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.redischool.Covid19Cases;
import org.redischool.Covid19DataReader;
import org.redischool.CountryTimeseries;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek; 
import java.time.temporal.TemporalAdjusters;
import java.lang.Math; 

    
public class CovidStatistics{

 public static ArrayList<Double> averageRate(ArrayList<Covid19Cases> all_cases){

    
    //type formatter.
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");
    //changing to correct type data.
    LocalDate dateBefore = LocalDate.parse(all_cases.get(0).getDate(),fmt);
    LocalDate dateAfter = LocalDate.parse(all_cases.get(0).getDate(),fmt);

    //inicializing confirmed variables.
    int confirmedFirst=0;
    int confirmedLast=0;
    //inicializing Deaths variables.
    int deathsFirst=0;
    int deathLast=0; 
    //inicializing Recovered variables. 
    int recoveredFirst=0;
    int recoveredLast=0;

   //iteration in all_cases array
    for (Covid19Cases covidCase : all_cases){

      LocalDate dateCase = LocalDate.parse(covidCase.getDate(), fmt);

      int confirmed = covidCase.getConfirmed();
      int deaths = covidCase.getDeaths();
      int recovered = covidCase.getRecovered();

      //compare first date
      if(dateCase.compareTo(dateBefore) < 0){
        confirmedFirst = confirmed;
        deathsFirst = deaths;
        recoveredFirst = recovered;
        dateBefore = dateCase;
        
      }
      //compare last date
      if(dateCase.compareTo(dateAfter) > 0){
        deathLast = deaths;
        recoveredLast = recovered;
        confirmedLast = confirmed;
        dateAfter = dateCase;

      }
    }
    
   //calculate the number of days between dates.
    double noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);

    double deltaConfirmedF = confirmedLast - confirmedFirst;
    double deltaDeathsF = deathLast - deathsFirst;
    double deltaRecoveredF = recoveredLast - recoveredFirst;

    double deltaT =  noOfDaysBetween;

    double resultConfirmed = deltaConfirmedF/deltaT;
    double resultDeaths = deltaDeathsF/deltaT;
    double resultRecovered = deltaRecoveredF/deltaT;



   ArrayList<Double>  resultAverageRate = new ArrayList<>(Arrays.asList(resultConfirmed,resultDeaths, resultRecovered));

    return resultAverageRate;
  }

  public static ArrayList<Covid19Cases> rangeCases(ArrayList <Covid19Cases> all_cases, String date1, String date2){

    ArrayList <Covid19Cases> range = new ArrayList<Covid19Cases>();
    //type formatter.
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");

    LocalDate firstDate = LocalDate.parse(date1, fmt);
    LocalDate lastDate = LocalDate.parse(date2, fmt);
  
    //iteration in all_cases array
    for (Covid19Cases covidCase : all_cases){
    LocalDate dateCase = LocalDate.parse(covidCase.getDate(), fmt);
    LocalDate dateCase2 = LocalDate.parse(covidCase.getDate(), fmt);

      if(dateCase.isAfter(firstDate) && dateCase2.isBefore(lastDate)){
        range.add(covidCase);
      }
    }
     
   return range;
  }

  public static ArrayList <Covid19Cases> nonNullCases(ArrayList <Covid19Cases> all_cases){
    //inicializing variable.
    ArrayList <Covid19Cases> nonNullCases = new ArrayList <Covid19Cases>();
    
    //iteration in all_cases array
    for (Covid19Cases covidCase : all_cases){
      if(covidCase.getConfirmed() > 0){
        nonNullCases.add(covidCase);
      }
    }

   return nonNullCases;
  }

  public static ArrayList <Covid19Cases> weekStatistics(ArrayList <Covid19Cases> all_cases){
    
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");

    ArrayList <Covid19Cases> weekCases = new ArrayList <Covid19Cases>();
    
    for(Covid19Cases covidCase : all_cases){

      LocalDate dateCase = LocalDate.parse(covidCase.getDate(),fmt);
      DayOfWeek dayOfWeek = DayOfWeek.from(dateCase); 

        if(dayOfWeek.equals(DayOfWeek.SUNDAY))
         weekCases.add(covidCase);
    }

   return weekCases;
  }

  public static ArrayList <Covid19Cases> monthStatistics(ArrayList <Covid19Cases> all_cases){

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");

    ArrayList <Covid19Cases> monthCases = new ArrayList <Covid19Cases>();

    for(Covid19Cases covidCase : all_cases){

    LocalDate dateCase = LocalDate.parse(covidCase.getDate(),fmt);
    LocalDate firstDayOfmonth = dateCase.with(TemporalAdjusters.firstDayOfMonth());

      if(dateCase.equals(firstDayOfmonth))
      monthCases.add(covidCase);
    }

    return monthCases;
  }

  public static double growthRate(ArrayList <Covid19Cases> all_cases){

    double first = all_cases.get(0).getConfirmed();
    
    double last = all_cases.get(all_cases.size()-1).getConfirmed();

    double size = all_cases.size()-1;
   
    double result = Math.pow(last/first, 1/size);

    return result;
  }

  public static ArrayList <Covid19Cases> selectCountry(ArrayList<CountryTimeseries> countries, String country){

   ArrayList <Covid19Cases> country_cases = null;


    for(int i = 0; i < countries.size();i++){
      CountryTimeseries randCountry = countries.get(i); 
      if((randCountry.getCountry()).equals(country)){
         country_cases = randCountry.getDailyCases();
      }
    }

    return country_cases;
  }

  public static Covid19Cases totalCasesInfo (ArrayList <Covid19Cases> all_cases){

    Covid19Cases lastInfo = all_cases.get(all_cases.size()-1);

  
   return lastInfo;
  }
}







