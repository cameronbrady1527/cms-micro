package cs2110;

import java.util.Objects;

/**
 * A mutable set of students.
 */
public class StudentSet {
    // Implementation: the StudentSet is implemented as a resizable array data structure.
    // Implementation constraint: do not use any classes from java.util.
    // Implementation constraint: assert preconditions for all method parameters and assert that the
    //     invariant is satisfied at least at the end of every method that mutates any fields.

    /**
     * Array containing the students in the set.  Elements `store[0..size-1]` contain the distinct
     * students in this set, none of which are null.  All elements in `store[size..]` are null.  The
     * length of `store` is the current capacity of the data structure and is at least 1.  Two
     * students `s1` and `s2` are distinct if `s1.equals(s2)` is false.
     */
    private Student[] store;

    /**
     * The number of distinct students in this set.  Non-negative and no greater than
     * `store.length`.
     */
    private int size;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert store != null && store.length > 0;
        assert size >= 0 && size <= store.length;

        for (int i = 0; i < size; ++i) {
            // Check that elements in use are non-null
            assert store[i] != null;

            // Check that students are all distinct
            for (int j = i + 1; j < size; ++j) {
                assert !store[i].equals(store[j]);
            }
        }

        // Check that unused capacity is all null
        for (int i = size; i < store.length; ++i) {
            assert store[i] == null;
        }
    }

    /**
     * Create an empty set of students.
     */
    public StudentSet() {
        int capacity = 10;
        store = new Student[capacity];
        assertInv();
    }

    /**
     * Return the number of students in this set.
     */
    public int size() {
        return size;
    }

    /**
     * Effect: Add student `s` to the set.  Requires `s` is not already in the set.
     */
    public void add(Student s) {
        assertInv();

        // Defensive programming to make sure `s` cannot be added if already in the student set
        for (int i = 0; i < size; i++) {
            assert s != store[i];
        }


        if (size >= store.length) {
            updateSize(store.length);
        }

        size++;

        store[size - 1] = s;

        assertInv();
    }

    /**
     * Return whether this set contains student `s`.
     */
    public boolean contains(Student s) {
        boolean contains = false;

        for (int i = 0; i < size; i++) {
            if (s.fullName().equals(store[i].fullName())) {
                contains = true;
            }
        }

        return contains;
    }

    /**
     * Effect: If student `s` is in this set, remove `s` from the set and return true. Otherwise,
     * return false.
     */
    public boolean remove(Student s) {
        assertInv();

        boolean removed = false;

        if (contains(s)) {
            // loop starts at set index where student is to be removed
            for (int i = findStudentNumber(s); i < size; i++) {
                // when size == length, deal with out-of-bounds exception
                if (i == size - 1) {
                    store[i] = null;
                } else {
                    // move each student down an index starting with overriding `s`
                    store[i] = store[i + 1];
                }
            }

            // maintain accurate amount of students in set
            size--;

            // student has successfully been removed and size of set has been adjusted
            removed = true;
        }

        assertInv();

        return removed;
    }

    /**
     * Effect: Helper method to create a new array containing previous students, while doubling
     * the data structures capacity.
     */
    private void updateSize(int capacity) {
        assertInv();

        // Placeholder array to store data to be copied to new array with doubled capacity
        Student[] temp;
        temp = store;

        // `store` points to a new array with doubled capacity
        store = new Student[capacity * 2];

        // Each student from original array, stored in `temp`, added to new array
        for (int i = 0; i < temp.length; i++) {
            store[i] = temp[i];
        }

        assertInv();
    }

    /**
     * Effect: Helper method to get a student's number
     */
    private int findStudentNumber(Student s) {
        // Variable to keep track of position in set
        int position = 0;

        while (position < size) {
            // exit loop if/when student `s` full name matches one in student set
            if (s.fullName().equals(store[position].fullName())) { break; }

            position++;
        }

        return position;
    }

    /**
     * Return the String representation of this StudentSet.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(store[i]);
        }
        sb.append("}");
        return sb.toString();
    }
}
