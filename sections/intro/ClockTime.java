// A ClockTime object represents
// an hour:minute time during
// the day or night, such as
// 10:45 AM or 6:27 PM.
public class ClockTime {
    private int hour;
    private int minute;
    private String amPm;

    // Constructs a new time for
    // the given hour/minute
    public ClockTime(int h, int m, String ap) {
        this.hour = h;
        this.minute = m;
        this.amPm = ap;
    }

    // returns the field values
    public int getHour() {
        return  hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getAmPm() {
        return amPm;
    }

    // returns String for time;
    // example: "6:27 PM"
    public String toString() {
        String timeString = hour + ":";
        if (minute < 10) {
            timeString += "0";
        }
        return timeString + minute + " " + amPm;
    }

    /* TODO */
    // implement the advance() method below



    public static void main(String[] args) {
        ClockTime time = new ClockTime(6, 27, "PM"); // 6:27 PM
        System.out.println(time);
        time.advance(1); // 6:28 PM
        System.out.println(time);
        time.advance(30); // 6:58 PM
        System.out.println(time);
        time.advance(5); // 7:03 PM
        System.out.println(time);
        time.advance(60); // 8:03 PM
        System.out.println(time);
        time.advance(128); // 10:11 PM
        System.out.println(time);
        time.advance(180); // 1:11 AM
        System.out.println(time);
        time.advance(1440); // 1:11 AM (1 day later)
        System.out.println(time);
        time.advance(21075); // 4:26 PM (2 weeks later)
        System.out.println(time);
    }

}
