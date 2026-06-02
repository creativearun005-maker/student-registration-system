package com.example.student_registration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository; // Simulates the database repository

    @InjectMocks
    private StudentService studentService; // Inject the mock repository into your service

    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        // Initialize a clean sample student instance before every single test run
        sampleStudent = new Student("Amit", "Sharma", "amit@xyz.com");
        sampleStudent.setStudentId(101);
    }

    @Test
    void testSaveStudent_Success() {
        // Arrange: Tell the mock repository exactly what to return when called
        when(studentRepository.save(any(Student.class))).thenReturn(sampleStudent);

        // Act: Execute the service layer method we want to test
        Student savedStudent = studentService.saveStudent(new Student("Amit", "Sharma", "amit@xyz.com"));

        // Assert: Verify the data matches perfectly
        assertNotNull(savedStudent, "The saved student should not be null.");
        assertEquals(101, savedStudent.getStudentId());
        assertEquals("Amit", savedStudent.getFirstName());
        assertEquals("amit@xyz.com", savedStudent.getEmail());

        // Verify the repository's save method was actually triggered exactly 1 time
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetAllStudents_Success() {
        // Arrange: Build a dummy list for our mock setup to return
        List<Student> dummyList = new ArrayList<>();
        dummyList.add(sampleStudent);
        dummyList.add(new Student("Priya", "Patel", "priya@xyz.com"));

        when(studentRepository.findAll()).thenReturn(dummyList);

        // Act: Run the lookup method
        List<Student> resultList = studentService.getAllStudents();

        // Assert: Make sure the array sizes and content check out perfectly
        assertEquals(2, resultList.size(), "The registry list size should match the mocked source.");
        assertEquals("Amit", resultList.get(0).getFirstName());
        assertEquals("Priya", resultList.get(1).getFirstName());

        verify(studentRepository, times(1)).findAll();
    }
    @Test
    void testSaveStudent_DatabaseException() {
        // Arrange: Force the repository mock to deliberately throw a database crash
        when(studentRepository.save(any(Student.class)))
                .thenThrow(new RuntimeException("MySQL Database Connection Lost"));

        // Act & Assert: Verify that the service correctly passes the error upward to our global handler
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.saveStudent(new Student("Faulty", "Data", "faulty@xyz.com"));
        });

        // Double check that our custom error message is preserved cleanly
        assertEquals("MySQL Database Connection Lost", exception.getMessage());
        verify(studentRepository, times(1)).save(any(Student.class));
    }
}