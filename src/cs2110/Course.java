package cs2110;

/**
 * A course managed by the CMSμ course management system.  Courses are assumed to meet every day of
 * the week.
 */
public class Course {

    /**
     * Set of all students enrolled in this Course.
     */
    private StudentSet students;

    /**
     * The title of this course (e.g. Object-Oriented Programming and Data Structures).  Must be
     * non-empty.
     */
    private String title;

    /**
     * Number of credit hours students will receive upon completing this course.  Must be
     * non-negative.
     */
    private int credits;

    /**
     * The last name of the professor of this course (e.g. Myers).  Must be non-empty.
     */
    private String prof;

    /**
     * The location of lectures at this course (e.g. Statler Hall room 185).  Must be non-empty.
     */
    private String location;

    /**
     * The start time of this course's daily meetings, expressed as the number of minutes after
     * midnight.  Must be between 0 and 1439, inclusive.
     */
    private int startTimeMin;

    /**
     * The duration of this course's daily meetings, in minutes.  Must be positive, and
     * `startTimeMin + durationMin` must be no greater than 1440.
     */
    private int durationMin;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        // non-empty title
        assert title != "" && title != null;

        // non-negative credit hours
        assert credits > 0;

        // non-empty Professor (last) name
        assert prof != "" && prof != null;

        // non-empty location for course
        assert location != "" && location != null;

        // `startTimeMin` in range [0, 1439]
        assert startTimeMin >= 0 && startTimeMin <= 1439;

        // non-negative class duration and added to the start time cannot exceed 1440 minutes
        assert durationMin > 0 && startTimeMin + durationMin <= 1440;
    }

    /**
     * Create a course with title `title`, taught by a professor with last name `profName`, where
     * lectures of duration `duration` minutes start at local time `startHr`:`startMin` and take
     * place in a location described by `location`.  The course counts for `credits` credit hours
     * and initially has no students. Requires `title`, `profName`, and `location` are non-empty,
     * `startHr` is between 0 and 23 (inclusive), `startMin` is between 0 and 59 (inclusive), and
     * `credits` is non-negative. `duration` must be positive and must imply an end time no later
     * than midnight.
     */
    public Course(String title, int credits, String profName, String location,
            int startHr, int startMin, int duration) {
        this.title = title;
        this.credits = credits;
        prof = profName; // address if a full name is passed in
        this.location = location;
        startTimeMin = startMin + startHr * 60;
        durationMin = duration;

        students = new StudentSet();

        assertInv();
    }

    /**
     * Return the title of this course.
     */
    public String title() {
        return title;
    }

    /**
     * Return the number of credit hours awarded for completing this course.  Will not be negative.
     */
    public int credits() {
        return credits;
    }

    /**
     * Return the location of lectures in this course.
     */
    public String location() {
        return location;
    }

    /**
     * Return the last name of the instructor teaching the course, prefixed by the title "Professor"
     * (separated by a space).
     */
    public String instructor() {
        return "Professor " + prof.strip();
    }

    /**
     * Return the time at which lectures are held for this course in the format hour:min AM/PM using
     * 12-hour time. For example, "11:15 AM", "1:35 PM". Add leading zeros to the minutes if
     * necessary.
     */
    public String formatStartTime() {
        assertInv();

        String ampm;
        if (startTimeMin >= 720) { ampm = "PM"; }
        else { ampm = "AM"; }

        int min = startTimeMin % 60;
        int hr = (startTimeMin - min) / 60;

        if (hr > 12) { hr -= 12; }
        else if (hr == 0) { hr = 12; }

        // adds a 0 before a single digit minute in the return time
        String z = "";
        if (min / 10 < 1) { z = "0"; }

        assertInv();
        return hr + ":" + z + min + " " + ampm;
    }

    /**
     * Return whether this course's daily meetings overlap with those of `course` by at least 1
     * minute.  For example:
     * <ul>
     *   <li>A course that starts at 10:00 AM and has a duration of 60 minutes does **not** overlap
     *       with a course that starts at 11:00 AM and has a duration of 60 minutes.
     *   <li>A course that starts at 10:00 AM and has a duration of 61 minutes **does** overlap with
     *       a course that starts at 11:00 AM and has a duration of 60 minutes.
     * </ul>
     */
    public boolean overlaps(Course course) {
        assertInv();
        boolean overlap = false;

        // `course` passed in before main course
        if (startTimeMin > course.startTimeMin) {
            // overlap when other starting course time + duration > main course starting time
            if (course.startTimeMin + course.durationMin > startTimeMin) { overlap = true; }
        } // courses start at the same time
        else if (startTimeMin == course.startTimeMin) {
            // overlap when any duration
            if (durationMin > 0 || course.durationMin > 0 ) { overlap = true; }
        } // `course` passed in after main course
        else {
            // overlap when main start time + duration > other starting time
            if (startTimeMin + durationMin > course.startTimeMin) { overlap = true; }
        }

        assertInv();
        return overlap;
    }

    /**
     * Return whether `student` is enrolled in this course.
     */
    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    /**
     * Enroll `student` in this course if they were not enrolled already, adjusting their credit
     * count accordingly.  Return whether this causes a change in the enrollment of the course.
     */
    public boolean enrollStudent(Student student) {
        assertInv();
        boolean change = false;

        if (!hasStudent(student)) {
            students.add(student);
            change = true;
            student.adjustCredits(credits);
        }

        assertInv();
        return change;
    }

    /**
     * Drop Student `student` from this course if they are currently enrolled, adjusting their
     * credit count accordingly.  Return whether this causes a change in the enrollment of the
     * course.
     */
    public boolean dropStudent(Student s) {
        assertInv();
        boolean change = students.remove(s);

        if (change) {
            s.adjustCredits(-1 * credits);
        }

        assertInv();
        return change;
    }

    /**
     * Return the String representation of the list of students enrolled in this course
     */
    public String formatStudents() {
        return students.toString();
    }
}
