package ch.example;

import org.junit.Test;
import org.springframework.integration.json.JsonPathUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class DateTest {

    @Test
    public void changeDateZone() throws ParseException {

        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");

       // isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date test = isoFormat.parse("2012-01-01");

        System.out.println(test);


    }
}
