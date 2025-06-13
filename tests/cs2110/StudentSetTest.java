package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentSetTest {
    @Test
    void testConstructorAndSize() {
        // Constructor should yield an empty set
        StudentSet students = new StudentSet();
        assertEquals(0, students.size());
    }

    @Test
    void testAdd() {
        // Empty set of students (size 0) should be size 1 after adding a student
        {
            StudentSet students = new StudentSet();
            Student s = new Student("Cam", "Brady");

            students.add(s);

            assertEquals(1, students.size());
        }
        // Add initial capacity
        {
            // `EXPECTED_SIZE = 10` tests that 10 students can be added (EXPECTED_SIZE == capacity)
            int EXPECTED_SIZE = 10;

            // Empty set of students (size 0) should be size 10 after adding 10 students
            StudentSet students = new StudentSet();

            for(int i = 0; i < EXPECTED_SIZE; i++) {
                // creates a student with number of student in last name
                Student s = new Student("test", "student: " + i);

                // add student to `students`
                students.add(s);
            }

            assertEquals(EXPECTED_SIZE, students.size());
        }
        // add over initial capacity
        {
            // `EXPECTED_SIZE = 20` tests if add() can update the array's capacity (once)
            int EXPECTED_SIZE = 20;

            // Empty set of students (size 0) should be size 20 after adding 20 students
            StudentSet students = new StudentSet();

            for(int i = 0; i < EXPECTED_SIZE; i++) {
                // creates a student with number of student in last name
                Student s = new Student("test", "student: " + i);

                // add student to `students`
                students.add(s);
            }

            assertEquals(EXPECTED_SIZE, students.size());
        }
        // add many students (capacity must update multiple times
        {
            // `EXPECTED_SIZE = 50` tests if add() can update the array's capacity more than once
            int EXPECTED_SIZE = 50;

            // Empty collection of students (size 0) should be size 50 after adding 50 students
            StudentSet students = new StudentSet();

            for(int i = 0; i < EXPECTED_SIZE; i++) {
                // creates a student with number of student in last name
                Student s = new Student("test", "student: " + i);

                // add student to `students`
                students.add(s);
            }

            assertEquals(EXPECTED_SIZE, students.size());
        }
    }

    @Test
    void testContains() {
        // Typical false case: student not in set
        {
            StudentSet students = new StudentSet();

            for (int i = 0; i < 20; i++) {
                Student s = new Student("test", "student: " + i);

                students.add(s);
            }

            Student genius = new Student("Cam", "Brady");

            assertFalse(students.contains(genius));
        }
        // Typical true case: student is in set
        {
            StudentSet students = new StudentSet();

            for (int i = 0; i < 20; i++) {
                Student s = new Student("test", "student: " + (i + 1));

                students.add(s);
            }

            Student doppelganger = new Student("test", "student: " + 5);

            assertTrue(students.contains(doppelganger));
        }
        // Set with students does not contain targeted student
        {
            StudentSet students = new StudentSet();

            Student s = new Student("cam", "brady");

            students.add(s);

            Student brother = new Student("liam", "brady");

            assertFalse(students.contains(brother));
        }
    }

    @Test
    void testRemove() {
        // Remove one student out of a set of only that student
        {
            StudentSet students = new StudentSet();

            Student s = new Student("Izzy", "Martz");

            students.add(s);

            assertTrue(students.remove(s));
        }
        // Remove a student in the middle (not at start or end) of set
        {
            StudentSet students = new StudentSet();

            for (int i = 0; i < 20; i++) {
                Student s = new Student("Cam", "Brady: " + (i + 1));

                students.add(s);
            }

            Student s = new Student("Cam", "Brady: " + 15);

            assertTrue(students.remove(s));
        }
        // Remove the first student in the set and check if size updated
        {
            StudentSet students = new StudentSet();

            for (int i = 0; i < 20; i++) {
                Student s = new Student("Cam", "Brady: " + (i + 1));

                students.add(s);
            }

            Student s = new Student("Cam", "Brady: " + 1);

            // check student set size = 20
            assertEquals(20, students.size());
            // remove student from set (and confirm they are removed)
            assertTrue(students.remove(s));
            // check student set size = 19 (20 - 1 = 19)
            assertEquals(19, students.size());
        }
        // Remove the last student in the set
        {
            StudentSet students = new StudentSet();

            for (int i = 0; i < 20; i++) {
                Student s = new Student("Cam", "Brady: " + (i + 1));

                students.add(s);
            }

            Student s = new Student("Cam", "Brady: " + 20);

            assertTrue(students.remove(s));
        }
        // try to remove a student not contained in the list
        {
            StudentSet students = new StudentSet();

            for (int i = 0; i < 20; i++) {
                Student s = new Student("Cam", "Brady: " + (i + 1));

                students.add(s);
            }

            Student s = new Student("Cam", "Brady: " + 100);

            assertFalse(students.remove(s));
        }
    }
}
