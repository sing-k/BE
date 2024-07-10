package com.project.singk.domain.activity.controller;

import com.project.singk.domain.activity.controller.port.ActivityHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
public class ActivityHistoryController {
    private final ActivityHistoryService activityHistoryService;

}
