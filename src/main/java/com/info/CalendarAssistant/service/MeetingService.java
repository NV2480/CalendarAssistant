package com.info.CalendarAssistant.service;

import com.info.CalendarAssistant.entity.Employee;
import com.info.CalendarAssistant.entity.Meeting;
import com.info.CalendarAssistant.repository.EmployeeRepository;
import com.info.CalendarAssistant.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final EmployeeRepository employeeRepository;


    public MeetingService(MeetingRepository meetingRepository, EmployeeRepository employeeRepository) {
        this.meetingRepository = meetingRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Meeting> findConflictingMeetings(Meeting meeting) {
        return meetingRepository.findAllConflictingMeetings(
                meeting.getStartTime(), meeting.getEndTime());
    }


    public List<LocalDateTime[]> findFreeSlots(Long employeeId1, Long employeeId2, Duration meetingDuration) {
        Employee employee1 = employeeRepository.findById(employeeId1)
                .orElseThrow(() -> new IllegalArgumentException("Employee with id " + employeeId1 + " not found"));
        Employee employee2 = employeeRepository.findById(employeeId2)
                .orElseThrow(() -> new IllegalArgumentException("Employee with id " + employeeId2 + " not found"));

        List<Meeting> allMeetings = meetingRepository.findAll();

        // Find all booked slots
        Set<LocalDateTime> bookedSlots = new HashSet<>();
        for (Meeting meeting : allMeetings) {
            if (meeting.getParticipants().contains(employee1) || meeting.getParticipants().contains(employee2)) {
                LocalDateTime startTime = meeting.getStartTime();
                while (startTime.isBefore(meeting.getEndTime())) {
                    bookedSlots.add(startTime);
                    startTime = startTime.plus(meetingDuration); // Use meeting duration for slot increments
                }
            }
        }

        // Find all free slots
        LocalDateTime startDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDateTime = startDateTime.plusDays(7); // Search for free slots for the next 7 days
        List<LocalDateTime[]> freeSlots = new ArrayList<>();
        while (startDateTime.isBefore(endDateTime)) {
            LocalDateTime endTime = startDateTime.plus(meetingDuration);
            if (!bookedSlots.contains(startDateTime)) {
                LocalDateTime[] slot = {startDateTime, endTime};
                freeSlots.add(slot);
            }
            startDateTime = startDateTime.plus(meetingDuration); // Move to the next meeting slot
        }

        return freeSlots;
    }



    public Meeting bookMeeting(Meeting meeting) {
        // Validate participants exist
        Set<Employee> participants = new HashSet<>();
        for (Employee participant : meeting.getParticipants()) {
            Employee existingParticipant = employeeRepository.findById(participant.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Participant with id " + participant.getId() + " not found"));
            participants.add(existingParticipant);
        }
        meeting.setParticipants(participants);

        // Check if the organizer exists, or add them if they do not exist
        Employee organizer = meeting.getOrganizer();
        if (organizer.getId() == null) {
            organizer = employeeRepository.save(organizer);
        } else {
            Employee finalOrganizer = organizer;
            organizer = employeeRepository.findById(organizer.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Organizer with id " + finalOrganizer.getId() + " not found"));
        }

        meeting.setOrganizer(organizer);

        // Check for conflicting meetings
        List<Meeting> conflictingMeetings = findConflictingMeetings(meeting);
        if (!conflictingMeetings.isEmpty()) {
            throw new IllegalArgumentException("Conflicting meetings found");
        }

        return meetingRepository.save(meeting);
    }

}
