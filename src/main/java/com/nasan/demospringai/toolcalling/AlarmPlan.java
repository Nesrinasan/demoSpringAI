package com.nasan.demospringai.toolcalling;

import java.util.List;

public record AlarmPlan(List<AlarmAssignment> scheduled, List<String> notScheduled) {}

