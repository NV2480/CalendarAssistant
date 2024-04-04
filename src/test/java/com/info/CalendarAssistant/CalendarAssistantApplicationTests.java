package com.info.CalendarAssistant;

import com.info.CalendarAssistant.controller.MeetingController;
import com.info.CalendarAssistant.entity.Employee;
import com.info.CalendarAssistant.entity.Meeting;
import com.info.CalendarAssistant.repository.EmployeeRepository;
import com.info.CalendarAssistant.repository.MeetingRepository;
import com.info.CalendarAssistant.service.MeetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalendarAssistantApplicationTests {

	@Mock
	private MeetingService meetingService;

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private MeetingController meetingController;

	@Test
	public void contextLoads() {
		// Test that the Spring context loads successfully
	}

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setName("John Doe");
		when(employeeRepository.save(employee)).thenReturn(employee);

		ResponseEntity<Employee> response = meetingController.createEmployee(employee);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Employee savedEmployee = response.getBody();
		assertNotNull(savedEmployee);
		assertEquals("John Doe", savedEmployee.getName());
	}


	@Test
	public void testBookMeeting() {
		Employee organizer = new Employee();
		organizer.setId(1L);
		Meeting meeting = new Meeting();
		meeting.setOrganizer(organizer);
		when(meetingService.bookMeeting(meeting)).thenReturn(meeting);

		ResponseEntity<Meeting> response = meetingController.bookMeeting(meeting);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		Meeting bookedMeeting = response.getBody();
		assertNotNull(bookedMeeting);
		assertEquals(organizer.getId(), bookedMeeting.getOrganizer().getId());
	}

	@Test
	void testFindConflictingMeetings_NoConflicts() {

		MeetingRepository meetingRepository = mock(MeetingRepository.class);
		Meeting meeting = new Meeting();
		meeting.setStartTime(LocalDateTime.of(2022, 1, 1, 9, 0));
		meeting.setEndTime(LocalDateTime.of(2022, 1, 1, 10, 0));

		when(meetingRepository.findAll()).thenReturn(Arrays.asList(meeting));

		// Test
		List<Meeting> conflicts = meetingService.findConflictingMeetings(meeting);

		// Verify
		assertTrue(conflicts.isEmpty());
	}


}
