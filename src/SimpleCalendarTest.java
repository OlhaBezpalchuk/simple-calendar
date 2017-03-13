import java.text.DateFormatSymbols;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class SimpleCalendarTest {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static void main(String[] args) {

        Month month;
        int year = LocalDate.now().getYear();

        switch (args.length) {
            case 0:
                //if no parameters, generate default calendar (for current month and current year)
                month = LocalDate.now().getMonth();
                break;
            case 1:
                //if 1 parameter, generate calendar for given month in current year
                try {
                    month = Month.valueOf(args[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    printMessage();
                    return;
                }
                break;
            case 2:
                //if 2 parameters, generate calendar for given month in given year
                try {
                    month = Month.valueOf(args[0].toUpperCase());
                    year = Integer.parseInt(args[1]);
                } catch (IllegalArgumentException e) {
                    printMessage();
                    return;
                }
                break;
            default:
                printMessage();
                return;
        }

        printCalendar(month, year);

    }

    static void printCalendar(Month month, int year) {
        //display weekday names
        for(DayOfWeek dayOfWeek : DayOfWeek.values()) {
            System.out.print(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase() + "\t");
        }
        System.out.println("\n");

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate firstDayOfNextMonth = firstDayOfMonth.with(TemporalAdjusters.firstDayOfNextMonth());

        //tabulations until first day
        for(int i = 1; i < firstDayOfMonth.getDayOfWeek().getValue(); i++) {
            System.out.print("\t");
        }

        //calendar formatting
        LocalDate date = LocalDate.from(firstDayOfMonth);
        String color;

        while(!firstDayOfNextMonth.isEqual(date)) {
            colorfulPrint(date.getDayOfMonth() + "\t", dateColor(date));

            if(date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                System.out.println("\n");
            }

            date = date.plusDays(1);
        }

    }

    static void printMessage() {
        System.out.println("Wrong arguments. Try again.");
        System.out.println("You can use 0, 1 or 2 arguments.");
        System.out.println("Available arguments: <none>; <month>; <month> <year>");
    }

    static void colorfulPrint(String string, final String color) {
        System.out.print(color + string + ANSI_RESET);
    }

    static String dateColor(LocalDate date) {
        //current day is green
        if (date.equals(LocalDate.now())) {
            return ANSI_GREEN;
            //weekend is red
        } else if(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return ANSI_RED;
            //other days are black
        } else {
            return ANSI_BLACK;
        }
    }

}
