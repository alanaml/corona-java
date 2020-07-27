import java.util.ArrayList;
import java.util.Arrays;
import org.redischool.Covid19DataReader;
import org.redischool.CountryTimeseries;
import org.redischool.Covid19Cases;
import java.time.LocalDate;
import java.text.DecimalFormat;
import java.util.Scanner;

/*
see also
https://redi-school.github.io/intro-java/projects/covid19/
 */
class Main {
    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);

        Covid19DataReader reader = new Covid19DataReader();

        ArrayList<CountryTimeseries> countries = reader.getCovid19Data();

    /*CountryTimeseries randCountry = countries.get(23);

    ArrayList<Covid19Cases> all_cases = randCountry.getDailyCases();

    System.out.println(randCountry.getCountry());
    Covid19Cases cases = randCountry.getDailyCases()
    .get(randCountry.getDailyCases().size() - 1);

    System.out.println(cases.getDate());
    System.out.println(cases.getConfirmed());
    System.out.println(cases.getDeaths());
    System.out.println(cases.getRecovered());
    System.out.println();
   */
        System.out.println("Please enter with a country name to check its info: ");
        String name = input.nextLine();
        infoCountry1Cases(countries, name);
        System.out.println();

        System.out.println("Please enter with other country name: ");
        String name2 = input.nextLine();
        infoCountry2Cases(countries, name2);
        System.out.println();

        comparationCountries(countries, name, name2);



    }

    public static void infoCountry1Cases(ArrayList<CountryTimeseries> countries, String name){


        DecimalFormat df = new DecimalFormat("#.##");

        Covid19Cases countryFirst = CovidStatistics.totalCasesInfo(CovidStatistics.selectCountry(countries, name));
        System.out.println("Last Date: "+ countryFirst.getDate());
        System.out.println("Total Confirmed: "+ countryFirst.getConfirmed());
        System.out.println("Total Deaths: "+ countryFirst.getDeaths());
        System.out.println("Total Recovered: "+ countryFirst.getRecovered());

        //average without 0s values(Checking range with values < 0).
        System.out.println();
        System.out.println("Average rate considering first Corona cases until last computed day. ");
        System.out.println("Confirmed = " + df.format((CovidStatistics.averageRate(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name)))).get(0)));
        System.out.println("Deaths = " + df.format((CovidStatistics.averageRate(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name)))).get(1)));
        System.out.println("Recovered = " + df.format((CovidStatistics.averageRate(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name)))).get(2)));

        //Weeks cases with non-Null values.
        System.out.println();
        System.out.println("Weeks Statistics: ");
        for(Covid19Cases i : CovidStatistics.weekStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries,name)))){
            //Can check Confirmed, Deaths or Recovered.
            System.out.println(i.getDate()+" => " + i.getConfirmed());
        }

        // Month's cases with non-Null values.
        System.out.println();
        System.out.println("Months Statistics: ");
        for(Covid19Cases j : CovidStatistics.monthStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name)))){
            //Can check Confirmed, Deaths or Recovered.
            System.out.println(j.getDate()+" => "+j.getConfirmed());
        }
    }

    public static void infoCountry2Cases(ArrayList<CountryTimeseries> countries, String name2){

        DecimalFormat df = new DecimalFormat("#.##");

        Covid19Cases countrySecond = CovidStatistics.totalCasesInfo(CovidStatistics.selectCountry(countries, name2));
        System.out.println("Last Date: "+countrySecond.getDate());
        System.out.println("Total Confirmed: "+countrySecond.getConfirmed());
        System.out.println("Deaths: "+countrySecond.getDeaths());
        System.out.println("Total Recovered: "+countrySecond.getRecovered());

        //average without 0s values(Checking range with values < 0).
        System.out.println();
        System.out.println("Average rate considering first Corona cases until last computed day. ");
        System.out.println("Confirmed = " + df.format((CovidStatistics.averageRate(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name2)))).get(0)));
        System.out.println("Deaths = " + df.format((CovidStatistics.averageRate(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name2)))).get(1)));
        System.out.println("Recovered = " + df.format((CovidStatistics.averageRate(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name2)))).get(2)));

        //Weeks cases with non-Null values.
        System.out.println();
        System.out.println("Weeks Statistics: ");
        for(Covid19Cases i : CovidStatistics.weekStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries,name2)))){
            //Can check Confirmed, Deaths or Recovered.
            System.out.println(i.getDate()+" => " + i.getConfirmed());
        }

        // Month's cases with non-Null values.
        System.out.println();
        System.out.println("Months Statistics: ");
        for(Covid19Cases j : CovidStatistics.monthStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name2)))){
            //Can check Confirmed, Deaths or Recovered.
            System.out.println(j.getDate()+" => "+j.getConfirmed());
        }

    }



    public static void comparationCountries(ArrayList<CountryTimeseries> countries, String name, String name2){

        Scanner input = new Scanner(System.in);

        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println();
        System.out.println("Comparing countries spread control");

        System.out.println();
        double country1 = CovidStatistics.growthRate(CovidStatistics.weekStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name))));

        double country2 = CovidStatistics.growthRate(CovidStatistics.weekStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name2))));

        if(country1<country2){
            System.out.println(name + " has a better week spread control than " + name2 + " (" + df.format(country1)+ " < "+ df.format(country2) + ")");
        }
        else{
            System.out.println(name2 + " has a better week spread control than " + name + " (" + df.format(country2) +" < " + df.format(country1)+ ")");
        }

        //Comparing months Statistics.
        System.out.println();
        double country1Month = CovidStatistics.growthRate(CovidStatistics.monthStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name))));

        double country2Month = CovidStatistics.growthRate(CovidStatistics.monthStatistics(CovidStatistics.nonNullCases(CovidStatistics.selectCountry(countries, name2))));

        if(country1Month<country2Month){
            System.out.println(name + " has a better month spread control than " + name2 + " (" + df.format(country1Month)+ " < "+ df.format(country2Month)+")" );
        }
        else{
            System.out.println(name2 + " has a better month spread control than " + name + " (" + df.format(country2Month) +" < " + df.format(country1Month)+")");
        }
        //Comparing countries in rage.
        //Comparing transmission rate between countries (R0, R naught).
        System.out.println();
        System.out.println("To compare countries in range, please enter with two dates. ");
        System.out.println("First date: ");
        String Date1RangeCompare = input.nextLine();
        System.out.println();
        System.out.println("Last date: ");
        String Date2RangeCompare = input.nextLine();

        double firstCountryCompare = CovidStatistics.growthRate(CovidStatistics.nonNullCases(CovidStatistics.rangeCases((CovidStatistics.selectCountry(countries, name)), Date1RangeCompare, Date2RangeCompare)));

        double SecondCountryCompare = CovidStatistics.growthRate(CovidStatistics.nonNullCases(CovidStatistics.rangeCases((CovidStatistics.selectCountry(countries, name2)), Date1RangeCompare, Date2RangeCompare)));

        System.out.println();
        if(firstCountryCompare<SecondCountryCompare){
            System.out.println("In this range selected " +name +  " has a better transmission rate than " + name2 + " " + df.format(firstCountryCompare)+ " < "+ df.format(SecondCountryCompare) );
        }
        else{
            System.out.println("In this range selected " +name2 + " has a better rate transmission than " + name + " (" + df.format(SecondCountryCompare) +" < " + df.format(firstCountryCompare)+") ");
        }



    }

}
