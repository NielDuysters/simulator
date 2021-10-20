package be.kdg.sa.simulator.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// TODO: Spring dingen doen met die NoArgs etc.

public class CSVLine {

    @CsvBindByName
    private int rowId;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String aircraftType;
    @CsvCustomBindByName(column = "type", converter = LineTypeConverter.class)
    private LineType type;                  // Determines if line should be interpreted as FlightInfo or ULD
    @CsvBindByName
    @CsvDate(value = "dd-MM-yyyy")
    private LocalDate startDate;            // FlightInfo and ULD
    @CsvBindByName
    @CsvDate(value = "dd-MM-yyyy")
    private LocalDate endDate;              // FlightInfo Only
    @CsvBindByName
    private String days;                    // FlightInfo Only
    @CsvBindByName
    private Time departureTime;             // FlightInfo Only
    @CsvBindByName
    private Time arrivalTime;               // FlightInfo Only
    @CsvBindByName
    private String flightNr;                // FlightInfo and ULD
    @CsvBindByName
    private String startingAirport;         // FlightInfo and ULD
    @CsvBindByName
    private String destinationAirport;      // FlightInfo and ULD
    @CsvBindByName
    private String uldCode;                 // ULD Only
    @CsvBindByName
    private float uldWeight;                // ULD Only

    public CSVLine() {

    }

    public CSVLine(int rowId, String name, String aircraftType, LineType type, LocalDate startDate, LocalDate endDate, String days, Time departureTime, Time arrivalTime, String flightNr, String startingAirport, String destinationAirport, String uldCode, float uldWeight) {
        this.rowId = rowId;
        this.name = name;
        this.aircraftType = aircraftType;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightNr = flightNr;
        this.startingAirport = startingAirport;
        this.destinationAirport = destinationAirport;
        this.uldCode = uldCode;
        this.uldWeight = uldWeight;
    }


    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {this.name = name;}

    public String getAircraftType() {
        return this.aircraftType;
    }
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFlightNr() {
        return flightNr;
    }

    public void setFlightNr(String flightNr) {
        this.flightNr = flightNr;
    }

    public String getStartingAirport() {
        return startingAirport;
    }

    public void setStartingAirport(String startingAirport) {
        this.startingAirport = startingAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getUldCode() {
        return uldCode;
    }

    public void setUldCode(String uldCode) {
        this.uldCode = uldCode;
    }

    public float getUldWeight() {
        return uldWeight;
    }

    public void setUldWeight(float uldWeight) {
        this.uldWeight = uldWeight;
    }

    public String[] getDaysStringAsList() {
        return this.days.split(":");
    }

    @Override
    public String toString() {
        return this.rowId + " " + this.type + " " + this.startDate + " " + this.endDate + " " + this.days + " " + this.departureTime + " " + this.arrivalTime + " " + this.flightNr + " " + this.startingAirport + " " + this.destinationAirport + " " + this.uldCode + " " + this.uldWeight;
    }

    public Flight toFlight() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date startDate = java.util.Date.from(this.startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date departureTime = formatter.parse(this.startDate + " " + this.departureTime);
        Date arrivalTime = formatter.parse(this.endDate + " " + this.arrivalTime);


        Flight flight =  new Flight(UUID.randomUUID(), "aircraftType", arrivalTime, departureTime, startDate, arrivalTime, departureTime, "name", arrivalTime, departureTime, this.getDestinationAirport(), this.getDestinationAirport());

        System.out.println("OUTPUT");
        System.out.println(flight.toString());

        return flight;
    }
}
