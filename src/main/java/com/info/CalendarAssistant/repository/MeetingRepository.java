package com.info.CalendarAssistant.repository;

import com.info.CalendarAssistant.entity.Employee;
import com.info.CalendarAssistant.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m " +
            "WHERE (m.startTime BETWEEN :startTime AND :endTime) OR " +
            "(m.endTime BETWEEN :startTime AND :endTime)")
    List<Meeting> findAllConflictingMeetings(@Param("startTime") LocalDateTime startTime,
                                             @Param("endTime") LocalDateTime endTime);
}


