package com.example.togethercalendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Event
{
    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDateDB(LocalDate localDate,List<Event> allEvents){
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : allEvents)
        {
            if(event.getDate().equals(localDate)){
                events.add(event);
            }
        }
        return events;
    }

    public static ArrayList<Event> eventsForDateAndTimeFromDB(LocalDate localDate, LocalTime time, List<Event> allEvents)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : allEvents)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if(event.getDate().equals(localDate) && eventHour == cellHour)
                events.add(event);
        }
        return events;
    }


    private String name;
    private LocalDate date;
    private LocalTime time;

    public Event(String name, LocalDate date, LocalTime time)
    {
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Event(String eventTitle, String eventDateString, String eventTimeString) {
        this.name = eventTitle;
        this.date = LocalDate.parse(eventDateString);
        this.time = LocalTime.parse(eventTimeString);
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public LocalTime getTime()
    {
        return time;
    }

    public void setTime(LocalTime time)
    {
        this.time = time;
    }
}
