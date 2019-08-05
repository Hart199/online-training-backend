package com.future.onlinetraining.entity.projection;

import com.future.onlinetraining.entity.Classroom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDetailData {
    Classroom classroom;
    Long memberCount;
}
