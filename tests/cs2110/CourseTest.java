package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void testConstructorAndObservers () {
        // create `cs2110` Course
        Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                                10, 10, 75);

        assertEquals("cs2110", cs2110.title());
        assertEquals(3, cs2110.credits());
        assertEquals("Bailey Hall", cs2110.location());
    }

    @Test
    void testInstructor() {
        // Typical case: last name given for professor
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            assertEquals("Professor Muhlberger", cs2110.instructor());
        }
        // Edge case: Given professor name includes trailing and leading spaces
        {
            Course cs2110 = new Course("cs2110", 3, "   Muhlberger       ", "Bailey Hall",
                    10, 10, 75);

            assertEquals("Professor Muhlberger", cs2110.instructor());
        }
        // Edge case: Given professor name includes a space within the name (assume intentional)
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    10, 10, 75);

            assertEquals("Professor Muhl berger", cs2110.instructor());
        }

    }

    @Test
    void testFormatStartTime() {
        // Typical case: course in AM hours
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    10, 10, 75);

            assertEquals("10:10 AM", cs2110.formatStartTime());
        }
        // Typical case: course in PM hours
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    20, 10, 75);

            assertEquals("8:10 PM", cs2110.formatStartTime());
        }
        // Edge case: course starts at midnight
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    0, 0, 75);

            assertEquals("12:00 AM", cs2110.formatStartTime());
        }
        // Edge case: course starts at noon
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    12, 0, 75);

            assertEquals("12:00 PM", cs2110.formatStartTime());
        }
        // Edge case: course start time contains a single digit minute value
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    12, 5, 75);

            assertEquals("12:05 PM", cs2110.formatStartTime());
        }
        // Edge Case: course start time contains a single digit hour value
        {
            Course cs2110 = new Course("cs2110", 3, "Muhl berger", "Bailey Hall",
                    8, 5, 75);

            assertEquals("8:05 AM", cs2110.formatStartTime());
        }
    }

    @Test
    void testOverlaps() {
        // Typical case: overlapping courses in the morning (AM)
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);

            assertTrue(cs2110.overlaps(cs2800));
        }
        // Typical case: overlapping courses in the evening/afternoon (PM)
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    14, 10, 75);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    15, 15, 50);

            assertTrue(cs2110.overlaps(cs2800));
        }
        // Typical case: course passed into method starts before
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    8, 5, 50);

            assertFalse(cs2110.overlaps(cs2800));
        }
        // Typical case: course passed in starts after
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    8, 5, 50);

            assertFalse(cs2800.overlaps(cs2110));
        }
        // Edge case: course finishes when other starts
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 25, 50);

            assertFalse(cs2110.overlaps(cs2800));
        }
        // Edge case: courses start at same time and have duration
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    10, 10, 50);

            assertTrue(cs2110.overlaps(cs2800));
        }
        // Edge case: same course passed into method and called on
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            assertTrue(cs2110.overlaps(cs2110));
        }
        // Typical case: AM course overlaps with a PM course
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 180);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    13, 5, 50);

            assertTrue(cs2110.overlaps(cs2800));
        }
        // Edge case: courses have the same start time and duration
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 180);
            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    10, 10, 180);

            assertTrue(cs2110.overlaps(cs2800));
        }
    }

    @Test
    void testHasStudent() {
        // Typical case: student not enrolled in course
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            Student s = new Student("Jude", "Sangria");

            assertFalse(cs2110.hasStudent(s));
        }
        // Typical case: student enrolled in course
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            Student s = new Student("Clay", "Wheeler");
            cs2110.enrollStudent(s);

            assertTrue(cs2110.hasStudent(s));
        }
        // Edge case: student dropped from course
        {
            Course aem3249 = new Course("aem3249", 3, "Treat", "Statler Hall", 14, 55, 50);
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            Student s = new Student("James", "Warren");
            aem3249.enrollStudent(s);
            cs2110.enrollStudent(s);
            aem3249.dropStudent(s);

            assertFalse(aem3249.hasStudent(s));
        }
    }

    @Test
    void testEnrollStudent() {
        // Typical case: enroll student into a course they are not already in
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            Student s = new Student("Ally", "Brady");

            assertTrue(cs2110.enrollStudent(s));
            assertTrue(cs2110.hasStudent(s));
        }
        // Edge case: enroll student twice
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            Student s = new Student("Ally", "Brady");

            cs2110.enrollStudent(s);

            // enrollStudent(s) will return false because Ally is already enrolled
            assertFalse(cs2110.enrollStudent(s));
            assertTrue(cs2110.hasStudent(s));
        }
        // Typical case: enroll student already in course
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            for (int i = 0; i < 20; i ++) {
                Student s  = new Student("Test", "Student: " + i);
                cs2110.enrollStudent(s);
            }

            Student test = new Student("Test", "Student: " + 15);

            assertFalse(cs2110.enrollStudent(test));
            assertTrue(cs2110.hasStudent(test));
        }
        // Typical case: enroll student in course (CHECKING CREDIT UPDATE)
        {
            int credits = 3;
            Course cs2110 = new Course("cs2110", credits, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            // Creation of new student... assume 0 credits
            Student cam = new Student("Cam", "Brady");

            // Enroll Cam in cs2110... should now have 3 credits
            cs2110.enrollStudent(cam);

            assertEquals(credits, cam.credits());
        }

    }

    @Test
    void testDropStudent() {
        // Typical case: drop student from course in which they are enrolled
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            // Creation of new student... assume 0 credits
            Student cam = new Student("Cam", "Brady");

            // Enroll Cam in cs2110... should now have 3 credits
            cs2110.enrollStudent(cam);

            assertEquals(3, cam.credits());
            assertTrue(cs2110.dropStudent(cam));
            assertEquals(0, cam.credits());
        }
        // Typical case: Attempt to drop student from a course they are not enrolled in
        {
            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            // Creation of new student... assume 0 credits
            Student cam = new Student("Cam", "Brady");

            assertEquals(0, cam.credits());
            assertFalse(cs2110.dropStudent(cam));
            assertEquals(0, cam.credits());
        }
    }
}
