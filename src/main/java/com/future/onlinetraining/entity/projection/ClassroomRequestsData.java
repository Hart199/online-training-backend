package com.future.onlinetraining.entity.projection;

import com.future.onlinetraining.entity.ClassroomRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ClassroomRequestsData {
    String className;
    int classId;
    String trainerName;
    long requesterCount;
    Boolean hasVote;

    public ClassroomRequestsData(String className, int classId, String trainerName, long requesterCount) {
        this.className = className;
        this.classId = classId;
        this.trainerName = trainerName;
        this.requesterCount = requesterCount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public long getRequesterCount() {
        return requesterCount;
    }

    public void setRequesterCount(long requesterCount) {
        this.requesterCount = requesterCount;
    }

    public Boolean getHasVote() {
        return hasVote;
    }

    public void setHasVote(Boolean hasVote) {
        this.hasVote = hasVote;
    }

}
