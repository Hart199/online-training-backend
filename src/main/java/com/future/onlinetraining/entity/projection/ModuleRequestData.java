package com.future.onlinetraining.entity.projection;

import com.future.onlinetraining.entity.ModuleRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ModuleRequestData {
    final ModuleRequest moduleRequest;
    Boolean hasVote;
}
