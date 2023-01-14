package shipley.c195;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Creates the TimeValues Class and creates a list of LocalTimes for every hour of the day
 */
public class TimeValues {
    private static ObservableList<LocalTime> timeList= FXCollections.observableArrayList();
    public static ObservableList<LocalTime> getTimes(){
        return timeList;
    }

    public static ObservableList<LocalTime> insertAllTime() {
        for (int i = 0; i<24; i++){
            timeList.add(LocalTime.of(i, 0));

        }

        return timeList;

    }
}
