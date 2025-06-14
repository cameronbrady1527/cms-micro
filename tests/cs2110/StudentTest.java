package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void testConstructorAndObservers() {
        // Typical case
        {
            Student s = new Student("first", "last");
            assertEquals("first", s.firstName());
            assertEquals("last", s.lastName());
            assertEquals(0, s.credits());
        }

        // Short names
        {
            Student s = new Student("f", "l");
            assertEquals("f", s.firstName());
            assertEquals("l", s.lastName());
        }
    }

    @Test
    void testFullName() {
        // Typical case
        {
            Student s = new Student("Cameron", "Brady");
            assertEquals("Cameron Brady", s.fullName());
        }
        // Short names
        {
            Student s = new Student("c", "b");
            assertEquals("c b", s.fullName());
        }
        // Non-letters in names
        {
            Student s = new Student("Alexandra!","Brady-Good");
            assertEquals("Alexandra! Brady-Good", s.fullName());
        }
        // Entire name contains exclusively numbers
        {
            Student s = new Student("12", "34");
            assertEquals("12 34", s.fullName());
        }
    }

    @Test
    void testAdjustCredits() {
        Student s = new Student("first", "last");
        s.adjustCredits(3);
        assertEquals(3, s.credits());

        // A second adjustment should be cumulative
        s.adjustCredits(4);
        assertEquals(7, s.credits());

        // Negative adjustments
        s.adjustCredits(-3);
        assertEquals(4, s.credits());

        // Back to zero
        s.adjustCredits(-4);
        assertEquals(0, s.credits());
    }
}
