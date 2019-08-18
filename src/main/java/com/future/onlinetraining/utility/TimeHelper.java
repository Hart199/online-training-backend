package com.future.onlinetraining.utility;

import com.future.onlinetraining.entity.ClassroomSession;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class TimeHelper {

    public boolean isClassroomSessionHasStarted(List<ClassroomSession> classroomSessionList) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        if (!classroomSessionList.isEmpty()) {
            Timestamp startTimestamp = classroomSessionList.get(0).getStartTime();

            if(timestamp.after(startTimestamp))
                return true;
        }
        return false;
    }

    public boolean isClassroomSessionHasEnded(List<ClassroomSession> classroomSessionList, int timePerSession) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        if (!classroomSessionList.isEmpty()) {
            long endTime = classroomSessionList.get(classroomSessionList.size() - 1).getStartTime().getTime()
                    + (timePerSession * 60);
            Timestamp endTimestamp = new Timestamp(endTime);

            if(timestamp.after(endTimestamp))
                return true;
        }
        return false;
    }
}
