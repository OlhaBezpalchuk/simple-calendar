import java.time.*;
import java.util.ArrayList;

public class SimpleCalendarTest {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {

        Month month;
        int year = LocalDate.now().getYear();

        switch (args.length){
            case 0:
                //if no parameters, generate default calendar (for current month and current year)
                month = LocalDate.now().getMonth();
                break;
            case 1:
                //if 1 parameter, generate calendar for given month in current year
                try {
                    month = Month.valueOf(args[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong argument. Try again.");
                    System.out.println("Available arguments: <none>; <month>; <month> <year>");
                    return;
                }
                break;
            case 2:
                //if 2 parameters, generate calendar for given month in given year
                try {
                    month = Month.valueOf(args[0].toUpperCase());
                    year = Integer.parseInt(args[1]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong argument. Try again.");
                    System.out.println("Available arguments: <none>; <month>; <month> <year>");
                    return;
                }
                break;
            default:
                System.out.println("Wrong number of arguments. Try again.");
                System.out.println("You can use 0, 1 or 2 arguments.");
                System.out.println("Available arguments: <none>; <month>; <month> <year>");
                return;
        }

        printCalendar(month, year);

    }

    static void printCalendar(Month month, int year) {

        System.out.print("MON\tTUE\tWED\tTHU\tFRI\t");
        System.out.println(ANSI_RED + "SAT\tSUN\n" + ANSI_RESET);

        Period period = Period.of(0, 0, 1);             //one day
        LocalDate date = LocalDate.of(year, month, 1);
        DayOfWeek firstDayOfWeek = date.getDayOfWeek();
        ArrayList<Integer> dates = new ArrayList<>();
        for(Month newMonth = month; newMonth.equals(month); newMonth = date.getMonth()) {
            dates.add(date.getDayOfMonth());
            date = date.plus(period);
        }

        //tabulations until first day
        for(int i = 1; i < firstDayOfWeek.getValue(); i++) {
            System.out.print("\t");
        }

        //calendar formatting
        int day = 0;
        for(int i = firstDayOfWeek.getValue(); day < dates.size(); i++) {
            //current day is green
            if(month == LocalDate.now().getMonth() && dates.get(day) == LocalDate.now().getDayOfMonth()) {
                System.out.print(ANSI_GREEN + dates.get(day) + "\t" + ANSI_RESET);
            //weekend is red
            } else if((i+1)%7 == 0 || i%7 == 0) {
                System.out.print(ANSI_RED + dates.get(day) + "\t" + ANSI_RESET);
            } else {
                System.out.print(dates.get(day) + "\t");
            }

            if(i%7 == 0) {
                System.out.println("\n");
            }

            day++;
        }

    }

}
