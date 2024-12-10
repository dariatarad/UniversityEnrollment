============================================
University Enrollment Project
============================================
  University Enrollment Project is the program is designed to 
collect data from user through forms (Add Student, Add Course,
Add Enrollment) and store it in designated files. When data is
collected&stored user can search and view specific information
(View Student, View Course, View(/Edit) Enrollment) and edit
data (Edit Student, Edit Course, View/Edit Enrollment).
  When Enrollment(s) has been created user can generate a 
Report for all Students enrolled for specific Course and their
grades. 

============================================
First Look
============================================
  When you run a program it opens GUI window on a main page of 
the program that includes Welcome message and Menu bar(File,
Student, Course, Enrollment, Report).

============================================
File Menu
============================================
  + README file information
  + Exit  

============================================
Student Menu
============================================
  + Add Student
  + View Student
  + Edit Student
____________________________________________________________
Add Student Form 
____________________________________________________________
  1. Student ID text field flags if user's input is not an
integer/empty field & if student with this ID already exists.
  2. State drop box has 50 states to choose from.
  3. Create Student button:
     a. Error message if ID exists/not an integer.
     b. Error message if any of the fields are empty.
     c. If no issues detected data from text fields is 
     collected and stored in students txt file. Success 
     message is displayed and all text field cleared.
____________________________________________________________
View Student Form 
____________________________________________________________
  1. User can search for student by ID.
  2. Search button:
     a. Error message if ID doesn't exist/not an integer.
     b. Error message if ID field is empty.
     c. If no issues detected loads data from student text 
     file and displays in text fields. Text fields can't be
     edited.
  3. Reset button: clears all fields and user can search for
next student.
____________________________________________________________
Edit Student Form 
____________________________________________________________
  1. View Student Form steps 1&2.
  2. All fields can be edited except ID.
  3. Update button:
     a. Error message if fields are empty.
     b. If no issues detected data from text fields is 
     collected and edited in students txt file. Success 
     message is displayed and all text field cleared.
*If enrollment for this student exists - student information
is updated in enrollment file too. 

============================================
Course Menu
============================================
  + Add Course
  + View Course
  + Edit Course
____________________________________________________________
Add Course Form 
____________________________________________________________
  1. Course ID text field flags if user's input is not an
integer/empty field & if course with this ID already exists.
  2. Department drop box has options to choose from.
  3. Create Course button:
     a. Error message if ID exists/not an integer.
     b. Error message if any of the fields are empty.
     c. If no issues detected data from text fields is 
     collected and stored in course txt file. Success 
     message is displayed and all text field cleared.
____________________________________________________________
View Course Form 
____________________________________________________________
  1. User can search for course by ID.
  2. Search button:
     a. Error message if ID doesn't exist/not an integer.
     b. Error message if ID field is empty.
     c. If no issues detected loads data from course text 
     file and displays in text fields. Text fields can't be
     edited.
  3. Reset button: clears all fields and user can search for
next course.
____________________________________________________________
Edit Course Form 
____________________________________________________________
  1. View Course Form steps 1&2.
  2. All fields can be edited except ID.
  3. Update button:
     a. Error message if fields are empty.
     b. If no issues detected data from text fields is 
     collected and edited in course txt file. Success 
     message is displayed and all text field cleared.
*If enrollment for this course exists - course information
is updated in enrollment file too.

============================================
Enrollment Menu
============================================
  + Add Enrollment 
  + View/Edit Enrollment 
____________________________________________________________
Add Enrollment Form 
____________________________________________________________
  1. Search for student -> View Student Form steps 1&2.
  2. Search for course -> View Course Form steps 1&2.
  3. Enrollment ID text field flags if user's input is not an
integer/empty field & if enrollment with this ID already 
exists.
  4. Create Enrollment button:
     a. Error message if ID exists/not an integer.
     b. Error message if any of the fields are empty.
     c. If no issues detected data from text fields is 
     collected and stored in enrollment txt file. Success 
     message is displayed and all text field cleared.
  5. Reset button: clears all fields.
____________________________________________________________
View/Edit Enrollment Form 
____________________________________________________________
  1. User can search for enrollment by ID.
  2. Search button:
     a. Error message if ID doesn't exist/not an integer.
     b. Error message if ID field is empty.
     c. If no issues detected loads data from enrollment text 
     file and displays in text fields.
   *Only Grade can be edited.
  3. Update Grade button:
     a. Error message if Grade field is empty.
     c. If no issues detected data from Grade field is 
     collected and updated in enrollment txt file. Success 
     message is displayed and all text field cleared.
  5. Reset button: clears all fields.

============================================
Report Menu
============================================
  + Generate Report
____________________________________________________________
Generate Report Form 
____________________________________________________________
  1. View Course Form steps 1&2.
  2. Generate Report button:
     a. Error if any of the fields are empty.
     b. Error if no students are enrolled for the course.
     c. If no issues detected loads data from enrollment text 
     file and displays in text area ->
     - Course number, name
     - All students that are enrolled in this course and their
     grade. 
  *Text area can't edited, but allows to copy text. 
  3. Clear button: clears all fields.
**If Clear button wasn't used new reports will be displayed
in text area one after another. 
============================================