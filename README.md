# UniversityInformationNetworkManagement-
University Information Network Management Application of Nonlinear Data Structures and Object-Oriented Programming (OOP)

R1. UML model the classes and necessary data associations to represent the network
and respective connections, as well as all meta-information associated with the
previously identified entities: students, teachers, classrooms, schedules, curricular
units, etc. The data model should take from basis some pre-defined data structures
(e.g. graph, tree, hashmap, etc.) and their respective operations (c.f. properties,
searches, etc.).

R2. The application should support values (generic Java Value class) for the elements of
the various symbol tables considered in this project. Functions and hash tables
should be used in, at least, one ST whose key does not have to be orderly. Balanced
BSTs (redblacks) should be used in STs whose key is orderly (e.g., times, order,
etc.).

R3. Functions to insert, remove, edit and list all the information, should be created, for
each of the several STs considered in the database.

R4. The consistency of all information should be validated. For instance, every teacher
mentioned in one of the multiple STs should exist in teachers ST.

R5. Deleted or edited information from storage structures (e.g. ST, BST, etc.) should be
archived. For instance, when removing a classroom, the object should be totally
deleted from the system and archived.

R6. The various STs of the application should be populated with the content of input text
files (upload information from file). 

R7. The application should be able to output (dump) all the information to text files, in
order to allow data restoration.
R8. Various information-based surveys should be implemented, such as:
a) Free classrooms at a given date;
b) Teachers teaching a given class;
c) Classes of a given teacher;
d) All the available slots for attendance schedule between a given student and
teacher;
e) Classroom occupation between two given dates;
f) Available classrooms based on multiple criterion (occupation, number of power
plugs, floor, etc.);

R9. Every system entity should have associated a schedule. With all the available
schedules, the system should construct a global overview of the institution at a given
point in time. For instance, the method now() could retrieve the state of an entity at
the moment: classroom occupation, curricular units being taught, available/occupied
teachers, etc.

R10. Static functions should be created to test the required functionalities.

R11. Javadoc documentation of all the methods should be generated 

R12. Represent the graph of the central building of UFP, including the exterior areas of
access. Each node / vertex of the graph corresponds to a generic point on the map of
building, for example, an entrance to a room, the central point of a hall, a
building entrance, the top / bottom of a stairway or a Waypoint (PdP)
somewhere in the building. Each node / vertex (i.e. generic point on the map) should have
associated with a set of attributes (e.g. coordinates related to a given
referential, point type, description, indoor / outdoor, etc.) and other attributes that
may be needed; The nodes / vertices of the graph are connected by arcs / branches,
corresponding to the pedestrian paths connecting the PdP represented by these
knots / vertices; The arcs / branches can have several weights, representing the physical connection
between nodes / vertices (e.g. distance in meters from the link, average time in seconds
the route, etc.);

R13.The data model should provide for the use of generic management and
graph checking, namely:
a) Calculation algorithms: the shortest path between two rooms (based on the
distance between the entrance nodes in the rooms); the fastest way (based on
the temporal cost between nodes); the shortest path avoiding a particular
set of points; the shortest path to the outer point (automatic
evacuation through emergency exits);
i) Note: the point of origin must always be the vertex closest to the
coordinated by the user, for example the student.
b) It should be possible to verify that the point graph (or any subgraph,
corresponding, for example, to a floor of the global graph) is connected;
c) Select a subgraph and apply the same algorithms or features to it
previously described;

R14. It must be possible to create a set of vertices manually or based on
attributes. This set of vertices must be used as restrictions in the searches
shortest paths. (e.g. vertices 12, 13 and 14 belong to a corridor that
is in temporary maintenance, these same vertices must be present in a
set of vertices to be avoided by the shortest path algorithms

R15. A graphical interface should be created for:
a) View the graph of the UFP building and its connections, with graphic distinction
different types of nodes (using, for example, different colors for each type
node);
b) Manage the graph by adding and removing nodes / vertices and arcs / branches, as well as
how to allow editing of your attributes;
R16. The manipulation and management of all information and the respective researches should
be also supported by the graphical interface;


R17. All data related to the application and the respective researches must be able to be
recorded in text files for later consultation by users;

R18. It should also be possible to import and export data from data models (cf.
Graph and related STs) for binary and text files.
