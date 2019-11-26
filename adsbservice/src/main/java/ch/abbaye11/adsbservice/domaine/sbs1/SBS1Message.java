package ch.abbaye11.adsbservice.domaine.sbs1;

import lombok.Getter;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

@Getter
@ToString
public class SBS1Message {

    // a degager a terme....
    private String rawMessage;

    private SBS1MessageType messageType;
    private String transmissionType;

    private LocalDateTime generatedDate;
    private Date loggedDate;

    private String latitude;
    private String longitude;

    private String sessionId;
    private String aircraftId;
    private String hexIdent;
    private String flightId;

    private String callsign;
    private String altitude;
    private String groundSpeed;
    private String track;

    private String verticalRate;
    private String squawk;
    private String alert;
    private String emergency;
    private String spi;
    private String isOnGround;

    private String test;



    private SBS1Message(String rawMessage,SBS1MessageType messageType, String transmissionType) {
        this.rawMessage = rawMessage;
        this.messageType = messageType;
        this.transmissionType = transmissionType;

    }

    private SBS1Message addDatesField(String generatedDate,String loggedDate,String generatedTime,String loggedTime)  {




        LocalDate logDate = LocalDate.parse(loggedDate.replace("/","-"));
        LocalTime logTime = LocalTime.parse(loggedTime);

        LocalDateTime logDateTime = LocalDateTime.of(logDate,logTime);

        ZonedDateTime zd = ZonedDateTime.of(logDateTime,ZoneOffset.UTC);


        LocalDateTime lt  = zd.toLocalDateTime();
        lt.atZone(ZoneOffset.ofHours(2));


        Date date= null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").parse(loggedDate + " " + loggedTime);

        } catch (ParseException e) {
            throw new SBS1MessageException(e.getMessage());
        }

        this.loggedDate = date;



        LocalDate genDate = LocalDate.parse(generatedDate.replace("/","-"));
        LocalTime genTime = LocalTime.parse(generatedTime);

        this.generatedDate = LocalDateTime.of(logDate,logTime);

        return this;
    }

    public static SBS1Message getInstanceFromRawMessage(final String rawMessage)  {

        String[] sbs1Elements = rawMessage.split(",");

        SBS1MessageType messageType = SBS1MessageType.fromString(sbs1Elements[0]);
        String transmissionType = sbs1Elements[1];

        String sessionId = sbs1Elements[2];
        String aircraftId = sbs1Elements[3];
        String hexIdent = sbs1Elements[4];
        String flightId = sbs1Elements[5];

        String generatedDate = sbs1Elements[6];
        String generatedTime = sbs1Elements[7];
        String loggedDate = sbs1Elements[8];
        String loggedTime = sbs1Elements[9];

        String callsign = sbs1Elements[10];
        String altitude = sbs1Elements[11];
        String groundSpeed = sbs1Elements[12];
        String track = sbs1Elements[13];

        String latitude = sbs1Elements[14];
        String longitude = sbs1Elements[15];

        String verticalRate = sbs1Elements[16];
        String squawk = sbs1Elements[17];
        String alert = sbs1Elements[18];
        String emergency = sbs1Elements[19];
        String spi = sbs1Elements[20];
        String isOnGround = sbs1Elements[21];



        return new SBS1Message(rawMessage,messageType,transmissionType)
                .addIdentitiesField(sessionId,aircraftId,hexIdent,flightId)
                .addDatesField(generatedDate,loggedDate,generatedTime,loggedTime)
                .addCoordinates(latitude,longitude)
                .addFlightData(callsign,altitude,groundSpeed,track)
                .addLastElements(verticalRate,squawk,alert,spi,isOnGround);

    }

    private SBS1Message addLastElements(String verticalRate, String squawk, String alert, String spi, String isOnGround) {
        this.verticalRate = verticalRate;
        this.squawk = squawk;
        this.alert = alert;
        this.spi = spi;
        this.isOnGround = isOnGround;
        return this;
    }

    private SBS1Message addFlightData(String callsign, String altitude, String groundSpeed, String track) {
        this.callsign = callsign;
        this.altitude = altitude;
        this.groundSpeed = groundSpeed;
        this.track = track;
        return this;
    }

    private SBS1Message addIdentitiesField(String sessionId, String aircraftId, String hexIdent, String flightId) {

        this.sessionId = sessionId;
        this.aircraftId = aircraftId;
        this.hexIdent = hexIdent;
        this.flightId = flightId;

        return this;
    }

    private SBS1Message addCoordinates(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

}
