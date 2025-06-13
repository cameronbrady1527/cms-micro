# CS2110 Assignment 2 - CMSμ (Course Management System)

## Overview
A micro course management system (CMSμ) that manages student enrollments in courses. This project implements fundamental data structures including a resizable array-based set, demonstrates defensive programming with assertions, and practices test-driven development. The system supports operations like enrollment management, schedule conflict detection, and credit limit validation.

## Learning Objectives
- Implement object behaviors while maintaining class invariants
- Build a resizable array data structure from scratch
- Practice defensive programming with precondition and invariant assertions
- Apply test-driven development (TDD) methodology
- Design interacting classes for a complete application

## Features

### Core Functionality
- **Student Management**: Track student information and course enrollments
- **Course Management**: Manage course rosters and credit hours
- **Enrollment Operations**: Add/drop students from courses with consistency checks
- **Set Operations**: Custom StudentSet implementation using resizable arrays

### Advanced Features
- **Schedule Conflict Detection**: Identify overlapping course times
- **Credit Limit Validation**: Find students exceeding credit limits
- **Data Consistency Auditing**: Verify credit totals match enrollments

## System Architecture

### Class Structure
```
CMSμ System
├── Student          # Student information and enrollments
├── StudentSet       # Resizable array-based set of students
├── Course          # Course information and roster
├── CMSu            # Main system coordinating all entities
└── Main            # Command-line interface
```

### Key Design Patterns
- **Defensive Programming**: Assertions for preconditions and invariants
- **Encapsulation**: Private fields with public interfaces
- **ID-based References**: Students and courses identified by unique IDs
- **"Ask Permission" Pattern**: Check capacity before adding entities

## Implementation Details

### Student Class (TODOs 1-7)
- Maintains student information (first name, last name)
- Tracks credit count and course enrollments
- Enforces invariants on credit limits and data consistency

### StudentSet Class (TODOs 8-13)
**Resizable Array Implementation:**
- Dynamic capacity management (doubles when full)
- Maintains uniqueness constraint (no duplicates)
- Supports add, remove, contains, and size operations
- Efficient O(1) append, O(n) search/remove

**Key Invariants:**
- No null elements in valid range
- No duplicates allowed
- Size ≤ Capacity
- Backing array never null

### Course Class (TODOs 14-22)
- Manages course information and student roster
- Handles enrollment/drop with credit updates
- Validates time conflicts and prerequisites
- Uses StudentSet for roster management

### CMSu Class (TODOs 23-26)
- Central coordinator for all students and courses
- Fixed-capacity arrays indexed by ID numbers
- Advanced query operations:
    - TODO 24: Detect schedule conflicts
    - TODO 25: Find over-enrolled students
    - TODO 26: Audit credit consistency

## Project Structure
```
a2/
├── src/cs2110/
│   ├── Student.java      # Student entity (YOUR CODE)
│   ├── StudentSet.java   # Set data structure (YOUR CODE)
│   ├── Course.java       # Course entity (YOUR CODE)
│   ├── CMSu.java         # Management system (YOUR CODE)
│   └── Main.java         # CLI interface (provided)
├── tests/cs2110/
│   ├── StudentTest.java      # Student tests (YOUR CODE)
│   ├── StudentSetTest.java   # Set tests (YOUR CODE)
│   ├── CourseTest.java       # Course tests (YOUR CODE)
│   ├── CMSuTest.java         # System tests (YOUR CODE)
│   ├── test-script.txt       # Sample commands
│   └── expected-output.txt   # Expected results
```

## Building and Running

### Prerequisites
- Java 11 or higher
- JUnit 5 for testing
- Assertions enabled (`-ea` VM option)

### Compilation
```bash
javac -d out src/cs2110/*.java
javac -cp "out:junit-platform-console-standalone-1.10.0.jar" -d out tests/cs2110/*.java
```

### Running Tests
```bash
# Individual test suites
java -cp "out:junit-platform-console-standalone-1.10.0.jar" org.junit.runner.JUnitCore cs2110.StudentTest
java -cp "out:junit-platform-console-standalone-1.10.0.jar" org.junit.runner.JUnitCore cs2110.StudentSetTest
java -cp "out:junit-platform-console-standalone-1.10.0.jar" org.junit.runner.JUnitCore cs2110.CourseTest
java -cp "out:junit-platform-console-standalone-1.10.0.jar" org.junit.runner.JUnitCore cs2110.CMSuTest
```

### Running the Application
```bash
# Interactive mode
java -ea -cp out cs2110.Main

# Script mode
java -ea -cp out cs2110.Main test-script.txt
```

## Usage Examples

### Command Line Interface
```
> help
Available commands:
- student <firstName> <lastName>
- course <credits> <name> <startHour> <duration> <daysOfWeek>
- enroll <studentId> <courseId>
- drop <studentId> <courseId>
- students
- courses
- roster <courseId>
- credits <studentId>
- conflicts <studentId>
- overloaded <creditLimit>
- audit
- exit

> student John Doe
Added student 0: John Doe

> course 3 CS2110 10 2 MWF
Added course 0: CS2110

> enroll 0 0
Enrolled student 0 in course 0
```

## Testing Strategy

### Test-Driven Development (TDD)
1. Read method specification
2. Write test cases before implementation
3. Implement method to pass tests
4. Refactor while keeping tests green

### Coverage Goals
- Every public method tested
- Both typical and edge cases
- Glass-box testing for resizing operations
- Integration testing via Main

### Assertion Strategy
```java
// Check preconditions
assert firstName != null && !firstName.isBlank();

// Check invariants
assert credits >= 0 && credits <= MAX_CREDITS;

// Call assertInv() at method boundaries
assertInv();
```

## Common Pitfalls
- Forgetting to update size when modifying StudentSet
- Not handling backing array resize correctly
- Using `==` instead of `.equals()` for object comparison
- Missing invariant checks after mutations
- Not updating credits when enrolling/dropping

## Implementation Tips

### Resizable Array Pattern
```java
// When adding an element
if (size == students.length) {
    // Double capacity
    Student[] bigger = new Student[2 * students.length];
    // Copy existing elements
    for (int i = 0; i < size; i++) {
        bigger[i] = students[i];
    }
    students = bigger;
}
```

### Defensive Programming
```java
public void addStudent(Student s) {
    assert s != null;
    assert !contains(s);
    
    // Method implementation
    
    assertInv();  // Check invariant at exit
}
```

## Grading Rubric
- Submitted and compiles: 25%
- Core classes meet specifications: 31%
- CMSu functionality: 12%
- Implementation constraints: 15%
- Test coverage and validity: 10%
- Code style: 5%
- Metadata (header comment): 2%

## Academic Integrity
This is a CS2110 assignment at Cornell University. Students must complete this assignment individually, following the course collaboration policy.

## Author
Cameron Brady

## Time Spent
40

## Acknowledgments
Assignment created by the CS2110 course staff at Cornell University.