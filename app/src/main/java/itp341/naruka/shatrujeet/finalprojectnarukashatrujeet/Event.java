package itp341.naruka.shatrujeet.finalprojectnarukashatrujeet;

import java.sql.Time;
import java.util.Date;

/**
 * Created by shatrujeet lawa on 11/20/2017.
 */

public class Event {


    String name;
    String id;
    String location;
    String date;
    String time;
    //String uniqueID;


    public Event()
    {

    }

    public Event(String nam,String i,String loc,String dat,String tim,int attend)
    {
       // uniqueID=uCode;
        name=nam;
        id=i;
        location=loc;
        date=dat;
        time=tim;
        attendees=attend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    int attendees;

//    public String getUniqueID() {
//        return uniqueID;
//    }
//
//    public void setUniqueID(String uniqueID) {
//        this.uniqueID = uniqueID;
//    }
}
