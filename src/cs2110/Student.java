package cs2110;

/**
 * A student tracked by the CMSμ course management system.
 */
public class Student {
    /**
     * An immutable String holding a student's first name. Once an instance of a student is
     * created, `firstName` may not be empty ("") or null.
     */
    private final String firstName;

    /**
     * An immutable String holding a student's last name. Once an instance of a student is created,
     * `lastName` may not be empty ("") or null.
     */
    private final String lastName;

    /**
     * A mutable variable of type `int` that stores the number of credits the respective student
     * is enrolled in. A Student may not be enrolled in a negative amount of credits.
     */
    private int credits;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert firstName != null && !firstName.isEmpty();
        assert lastName != null && !lastName.isEmpty();

        // `credits != null` not checked given primitive type `int` cannot be null
        assert credits >= 0;
    }

    /**
     * Create a new Student with first name `firstName` and last name `lastName` who is not enrolled
     * for any credits.  Requires firstName and lastName are not empty.
     */
    public Student(String firstName, String lastName) {
        assert firstName != null && !firstName.isEmpty();
        assert lastName != null && !lastName.isEmpty();

        this.firstName = firstName;
        this.lastName = lastName;

        assertInv();
    }

    /**
     * Return the first name of this Student.  Will not be empty.
     */
    public String firstName() {
        assertInv();
        return firstName;
    }

    /**
     * Return the last name of this Student.  Will not be empty.
     */
    public String lastName() {
        assertInv();
        return lastName;
    }

    /**
     * Return the full name of this student, formed by joining their first and last names separated
     * by a space.
     */
    public String fullName() {
        // Observe that, by invoking methods instead of referencing this fields, this method was
        // implemented without knowing how you will name your fields.
        return firstName() + " " + lastName();
    }

    /**
     * Return the number of credits this student is currently enrolled in.  Will not be negative.
     */
    public int credits() {
        assertInv();
        return credits;
    }

    /**
     * Change the number of credits this student is enrolled in by `deltaCredits`. For example, if
     * this student were enrolled in 12 credits, then `this.adjustCredits(3)` would result in their
     * credits changing to 15, whereas `this.adjustCredits(-4)` would result in their credits
     * changing to 8.  Requires that the change would not cause the student's credits to become
     * negative.
     */
    void adjustCredits(int deltaCredits) {
        // Conditional checking if deltaCredits is negative
        assert deltaCredits >= 0 || (deltaCredits * -1) <= credits;

        credits += deltaCredits;

        assertInv();
    }

    /**
     * Return the full name of this student as its string representation.
     */
    @Override
    public String toString() {
        return fullName();
    }
}
