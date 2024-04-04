package com.info.CalendarAssistant.controller;

import com.info.CalendarAssistant.entity.Employee;
import com.info.CalendarAssistant.entity.Meeting;
import com.info.CalendarAssistant.repository.EmployeeRepository;
import com.info.CalendarAssistant.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public MeetingController(MeetingService meetingService, EmployeeRepository employeeRepository) {
        this.meetingService = meetingService;
        this.employeeRepository = employeeRepository;
    }

    // Endpoint to create a new employee
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // Endpoint to find conflicting meetings
    @GetMapping("/conflicts")
    public ResponseEntity<List<Meeting>> findConflictingMeetings(@RequestBody Meeting meeting) {
        List<Meeting> conflictingMeetings = meetingService.findConflictingMeetings(meeting);
        return ResponseEntity.ok(conflictingMeetings);
    }

    // Endpoint to find free slots for a meeting between two employees
    @GetMapping("/freeslots")
    public ResponseEntity<List<LocalDateTime[]>> findFreeSlots(@RequestParam Long employeeId1,
                                                               @RequestParam Long employeeId2,
                                                               @RequestParam Duration meetingDuration) {
        List<LocalDateTime[]> freeSlots = meetingService.findFreeSlots(employeeId1, employeeId2, meetingDuration);
        return ResponseEntity.ok(freeSlots);
    }

    // Endpoint to book a meeting
    @PostMapping("/book")
    public ResponseEntity<Meeting> bookMeeting(@RequestBody Meeting meeting) {
        Meeting bookedMeeting = meetingService.bookMeeting(meeting);
        return ResponseEntity.ok(bookedMeeting);
    }
}
