package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CMSuTest {

    @Test
    void testHasConflict() {
        // Typical case: student has at least one overlap (more than one in this case)
        {
            CMSu cms = new CMSu();

            Student s = new Student("Cam", "Brady");
            if(cms.canAddStudent()) { cms.addStudent(s); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }
            cs2110.enrollStudent(s);

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }
            cs2800.enrollStudent(s);

            Course cam3000 = new Course("cam3000", 4, "Brady", "Eddygate",
                    10, 30, 120);
            if(cms.canAddCourse()) { cms.addCourse(cam3000); }
            cam3000.enrollStudent(s);

            assertTrue(cms.hasConflict(s));
        }
        // Typical case: student does not have any overlaps
        {
            CMSu cms = new CMSu();

            Student s = new Student("Cam", "Brady");
            if(cms.canAddStudent()) { cms.addStudent(s); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }
            cs2110.enrollStudent(s);

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }
            cs2800.enrollStudent(s);

            Course cam3000 = new Course("cam3000", 4, "Brady", "Eddygate",
                    12, 30, 120);
            if(cms.canAddCourse()) { cms.addCourse(cam3000); }
            cam3000.enrollStudent(s);

            assertFalse(cms.hasConflict(s));
        }
        // Edge case: student not enrolled in any courses when courses in CMSu overlap
        {
            CMSu cms = new CMSu();

            Student s = new Student("Cam", "Brady");
            if(cms.canAddStudent()) { cms.addStudent(s); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }

            assertFalse(cms.hasConflict(s));
        }
    }

    @Test
    void testAuditCredits() {
        // Typical case: no students tracked by CMSu over the credit limit
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Student[] s = new Student[] {cam, ally, liam};

            for (Student student : s) {
                if (cms.canAddStudent()) {
                    cms.addStudent(student);

                    aem3020.enrollStudent(student);
                }
            }

            // Set of students with credits over 4 is empty (size of 0)
            assertEquals(0, cms.auditCredits(4).size());
        }
        // Typical case: there are students tracked by CMSu over the credit limit (5)
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }

            if(cms.canAddStudent()) {
                cms.addStudent(cam);
                aem3020.enrollStudent(cam);
                cs2110.enrollStudent(cam);
            }
            if(cms.canAddStudent()) {
                cms.addStudent(ally);
                aem3020.enrollStudent(ally);
                cs2110.enrollStudent(ally);
            }

            if(cms.canAddStudent()) { cms.addStudent(liam); }
            cs2800.enrollStudent(liam);

            StudentSet s = new StudentSet();
            s.add(cam);
            s.add(ally);

            // {Cam Brady, Ally Brady} == {Cam Brady, Ally Brady}
            assertEquals(s.toString(), cms.auditCredits(5).toString());
        }
        // Edge case: no students tracked by CMSu
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Student[] s = new Student[] {cam, ally, liam};

            for (Student student : s) {
                aem3020.enrollStudent(student);
            }

            // Set of students with credits over 4 is empty (size of 0)
            assertEquals(0, cms.auditCredits(4).size());
        }
        // Edge case: some students at limit
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }

            Course aew2800 = new Course("aew2800", 1, "Brady", "Cornell University",
                    10, 10, 20);
            if(cms.canAddCourse()) { cms.addCourse(aew2800); }

            if(cms.canAddStudent()) {
                cms.addStudent(cam);
                aem3020.enrollStudent(cam);
                cs2110.enrollStudent(cam);
            }
            if(cms.canAddStudent()) {
                cms.addStudent(ally);
                aem3020.enrollStudent(ally);
                cs2110.enrollStudent(ally);
                aew2800.enrollStudent(ally);
            }

            if(cms.canAddStudent()) { cms.addStudent(liam); }
            cs2800.enrollStudent(liam);
            aew2800.enrollStudent(liam);

            StudentSet s = new StudentSet();
            s.add(cam);
            s.add(ally);

            // {Cam Brady, Ally Brady} == {Cam Brady, Ally Brady}
            assertEquals(s.toString(), cms.auditCredits(5).toString());
        }
    }

    @Test
    void testCheckCredit() {
        // Typical case: CMSu tracks multiple students and classes accurately
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }

            Student[] s = new Student[] {cam, ally, liam};

            for (Student student : s) {
                if (cms.canAddStudent()) {
                    cms.addStudent(student);

                    aem3020.enrollStudent(student);
                    cs2110.enrollStudent(student);
                    cs2800.enrollStudent(student);
                }
            }

            assertTrue(cms.checkCreditConsistency());
        }
        // Edge case: course not added to CMSu that students are enrolled in
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);

            Student[] s = new Student[] {cam, ally, liam};

            for (Student student : s) {
                if (cms.canAddStudent()) {
                    cms.addStudent(student);

                    aem3020.enrollStudent(student);
                    cs2110.enrollStudent(student);
                    cs2800.enrollStudent(student);
                }
            }

            // Set of students with credits over 4 is empty (size of 0)
            assertFalse(cms.checkCreditConsistency());
        }
        // Edge case: CMSu does not track students enrolled in tracked courses
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);
            if(cms.canAddCourse()) { cms.addCourse(aem3020); }

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);
            if(cms.canAddCourse()) { cms.addCourse(cs2110); }

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);
            if(cms.canAddCourse()) { cms.addCourse(cs2800); }

            Student[] s = new Student[] {cam, ally, liam};

            for (Student student : s) {
                aem3020.enrollStudent(student);
                cs2110.enrollStudent(student);
                cs2800.enrollStudent(student);
            }

            // Students not tracked by CMSu, so case is "vacuously true"
            assertTrue(cms.checkCreditConsistency());
        }
        // Edge case: CMSu does not track courses students are enrolled in
        {
            CMSu cms = new CMSu();

            Student cam = new Student("Cam", "Brady");
            Student ally = new Student("Ally", "Brady");
            Student liam = new Student("Liam", "Muecke");

            Course aem3020 = new Course("aem3020", 3, "Knoblauch", "Warren Hall",
                    9, 5, 50);

            Course cs2110 = new Course("cs2110", 3, "Muhlberger", "Bailey Hall",
                    10, 10, 75);

            Course cs2800 = new Course("cs2800", 4, "Eichorn", "Statler Hall 185",
                    11, 15, 50);

            Student[] s = new Student[] {cam, ally, liam};

            for (Student student : s) {
                if (cms.canAddStudent()) {
                    cms.addStudent(student);

                    aem3020.enrollStudent(student);
                    cs2110.enrollStudent(student);
                    cs2800.enrollStudent(student);
                }
            }

            // No courses tracked while students are (relaxed precondition)
            assertFalse(cms.checkCreditConsistency());
        }
    }
}
