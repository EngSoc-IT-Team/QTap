# Database

In this package and sub-packages the SQLite database is defined and worked with.

## DbHelper

DbHelper is used to manage creation/deletion of tables and the overall database itself. It uses the SQLite library to manage the database in the phone's memory.

## DatabaseAccessor

DatabaseAccessor is used to ensure that only one instance of DbHelper can be made at once, and thus only one connection to the SQLite database can be made at once. This avoids errors with memory management.

## Courses

This sub-package holds the schema defining the current Courses table, and holds the manager controlling the data within that table.

The schema is defined by having `public static final` strings that represent column tables, and corresponding integers that show the order of those columns. Then, there are class attributes that hold the information of each entry's field, when a query is made.

The manager class inserts, retrieves, updates and deletes rows, as well as retrieving or deleting whole table in a useful format. More functionality can be built as needed. Specific rows are found with SQLite queries
that include a String array that defines what columns are being returned as well as a value to match. Individual rows are retrieved and returned in the schema class form, with the class attributes holding the data.
Updates/insertions are sent using a schema class parameter to hold information.

The courses table also references the OneClass table, as defined by the OneClass class, with it's own manager. This is treated as another ID field in the parent table, Courses, and that ID is used to lookup a specific class in the OneClass table.

## SqlStringStatements

This class simply holds a bunch of `public static final` strings that are used in SQLite queries to create or delete tables in the database.

