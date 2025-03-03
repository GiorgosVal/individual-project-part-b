CREATE SCHEMA school DEFAULT CHARACTER SET utf8;
use school;

CREATE TABLE students (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
sfname VARCHAR(15) NOT NULL,
slname VARCHAR(25) NOT NULL,
sdob DATE NOT NULL,
sfees DECIMAL(10,2) UNSIGNED NOT NULL DEFAULT 0,
PRIMARY KEY(id),
CONSTRAINT studentUnique UNIQUE(sfname, slname, sdob)
);

INSERT INTO students (sfname, slname, sdob, sfees)
values('Harold','Graham','1987/11/14', 250),('Frank','Hillman','1990/10/26', 350),('Kevin','Chase','1994/09/04', 400),('Marie','Lombardo','1995/05/16', 500),('Rebecca','Marten','2000/01/05', 275),('Adrian','Fortier','1998/12/23', 500),('Erica','Neal','1990/10/26', 600),('Marjorie','Ward','1987/11/14', 400),('Patricia','Good','1994/09/04', 800),('Diego','McDermott','1995/05/16', 750),('Ruth','Ho','2000/01/05', 350),('Patricia','Moorehead','1987/11/14', 560),('Margaret','Patterson','1990/10/26', 400),('Victoria','Martinez','1987/11/14',250),('Kimberly','Ochoa','1994/09/04',550),('Miriam','Madsen','1995/05/16', 600),('John','Vaillancourt','2000/01/05',400),('Sharron','Guertin','1987/11/14',375),('Jacob','Dunn','1990/10/26',600),('Matt','McBride','1987/11/14', 400);

CREATE TABLE courses (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
ctitle VARCHAR(40) NOT NULL,
cstream VARCHAR(20) NOT NULL,
ctype VARCHAR(30) NOT NULL,
cstart DATE NOT NULL,							-- δεν είναι αναγκαίο το start date να αρχίζει απο τη σημερινή ημερομηνία
cend DATE NOT NULL,								-- πρέπει το end date να είναι μετά το start
PRIMARY KEY(id),
CONSTRAINT courseUnique UNIQUE(ctitle, cstream, ctype, cstart, cend)
);

INSERT INTO courses(ctitle, cstream, ctype, cstart, cend)
values('Coding Bootcamp','Full-time','Java','2018/09/15','2018/12/31'),('Robotics','Full-time','Robot Modeling','2018/09/15','2018/12/31'),('Applied Mathematics','Part-time','MatLab','2018/09/15','2018/12/31'),('Embedeed Systems','Part-time','Arduino','2018/09/15','2018/12/31'),('CAD Design','Part-time','AutoCAD 3D','2018/09/15','2018/12/31');


CREATE TABLE assignments (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
atitle VARCHAR(100) NOT NULL,
adescr VARCHAR(100) NOT NULL,
asubDate DATE,						-- η subDate κατά τη δημιουργία του assignment θα είναι null. Θα γεμίζει όταν αντιστοιχείται σε course
co_id INT UNSIGNED,
PRIMARY KEY(id),
CONSTRAINT assignmentUnique UNIQUE(atitle),
CONSTRAINT coFK_assignments FOREIGN KEY (co_id) REFERENCES courses(id)
);

INSERT INTO assignments (atitle, adescr, asubDate, co_id)
values
('Lorem ipsum dolor sit amet','Praesent id sem convallis, auctor nunc vel, mattis nunc.','2018/10/05',1),
('Cras vitae est finibus','porta velit non, gravida e','2018/10/10',1),
('Maecenas at risus euismod, auctor erat sit amet','Mauris vel arcu consectetu','2018/10/16',1),
('Maecenas id risus efficitur','pulvinar arcu quis, malesuada magna','2018/10/23',1),
('Sed a arcu sed ligula interdum','Fusce eu mi vitae leo','2018/11/20',1),
('Praesent ornare purus','Sed porttitor mauris eget nibh placerat commodo','2018/12/28',1),
('In id urna ac ipsum luctus','Aenean elementum purus a dictum condimentum.','2018/11/13',2),
('Donec eget turpis vel felis','Morbi vitae erat at ex blandit fermentum','2018/11/20',2),
('Fusce a nulla quis','Donec quis odio consequat, ornare nulla nec, placerat mi','2018/11/30',2),
('Cras lobortis urna placerat','Maecenas consequat ligula eget lacus tincidunt varius','2018/11/29',2),
('Ut sodales metus ut','Ut sodales metus ut eleifend malesuada','2018/11/14',2),
('Nunc luctus turpis vitae','In id urna ac ipsum luctus rhoncus sed vitae metus','2018/12/21',2),
('Fusce a nulla quis metus','Nunc luctus turpis vitae vestibulum ultrices','2018/10/17',3),
('Nulam volutpat sem','Nullam volutpat sem a elementum iaculis','2018/10/25',3),
('Donec vitae massae','Duis porta nibh et purus laoree','2018/11/01',3),
('Nullae non felis iaculis','Donec quis odio consequat, ornare nulla nec, placerat mi','2018/11/06',3),
('Aenean vel elit posuere','Morbi non est finibus','2018/11/20',3),
('Donec vitae massa','Morbi non est finibus','2018/12/14',3),
('Duis portae nibh et purus laoree','Donec quis odio consequat, ornare nulla nec, placerat mi.','2018/11/27',4),
('Donec vitae masssa','Aenean vel elit posuere','2018/12/03',4),
('Nullami volutpat sem','Nullam volutpat sem a elementum iaculis','2018/10/02',4),
('Nulli non felis iaculis','Pellentesque ornare ipsum nec turpis','2018/11/01',4),
('Integer egest nibh ac neque','Morbi non est finibus','2018/10/16',4),
('Morbi non est finibus','Donec quis odio consequat, ornare nulla nec, placerat mi','2018/12/05',4),
('Duis porta nibh et purus laoree','Pellentesque ornare ipsum nec turpis','2018/10/30',5),
('Null volutpat sem','Nullam volutpat sem a elementum iaculis','2018/11/08',5),
('Nulla non felis iaculis','Mauris tempus velit in tempus gravida','2018/11/21',5),
('Pellentesque ornare ipsum nec turpis','Donec quis odio consequat, ornare nulla nec, placerat mi','2018/10/23',5),
('Nullamae volutpat sem','Duis porta nibh et purus laoree','2018/11/26',5),
('Integer egestas nibh ac neque','Nullam volutpat sem a elementum iaculis','2018/11/29',5);




CREATE TABLE trainers (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
tfname VARCHAR(15) NOT NULL,
tlname VARCHAR(25) NOT NULL,
tsubject VARCHAR(40) NOT NULL,
PRIMARY KEY(id),
CONSTRAINT trainerUnique UNIQUE(tfname, tlname)
);

INSERT INTO trainers(tfname, tlname, tsubject)
VALUES('William','Arnold','Front-end'),('Vernice','McEvoy','Databases'),('Ella','Trent','Algorithms'),('Michael','Ortiz','Electronics'),('Rikki','Jose','Technical Drawing'),('Nathan','Dufresne','Backend'),('Mary','Warner','Servo motors'),('Kimberly','Graham','R language'),('Travis','Giannini','Sensors'),('Don','Brown','HMI');



CREATE TABLE enrollments (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
st_id INT UNSIGNED NOT NULL,
co_id INT UNSIGNED NOT NULL,
PRIMARY KEY(id),
CONSTRAINT stFK_enrollments FOREIGN KEY (st_id) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT coFK_enrollments FOREIGN KEY (co_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT enrollUnique UNIQUE(st_id, co_id)
);

INSERT INTO enrollments(co_id, st_id)
VALUES(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),
(2,9),(2,10),(2,11),(2,12),(2,13),
(3,14),(3,15),(3,16),(3,17),(3,18),(3,19),(3,20),(3,1),(3,2),(3,3),(3,4),
(4,20),(4,17),(4,16),(4,15),(4,14),(4,13),(4,10),(4,6),(4,3),(4,2),(4,7),(4,9),
(5,11),(5,12),(5,13),(5,14),(5,17),(5,19);



CREATE TABLE homeworks (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
somark INT(3) UNSIGNED,
stmark INT(3) UNSIGNED,
st_id INT UNSIGNED NOT NULL,
as_id INT UNSIGNED NOT NULL,
PRIMARY KEY(id),
CONSTRAINT st_idFK_homeworks FOREIGN KEY (st_id) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT as_idFK_homeworks FOREIGN KEY (as_id) REFERENCES assignments(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT homeworkUnique UNIQUE(st_id, as_id)
);


INSERT INTO homeworks(st_id, as_id, somark, stmark)
VALUES(1,1,50,90),(2,1,50,90),(3,1,50,90),(4,1,50,90),(5,1,50,90),(6,1,50,90),(7,1,50,90),(8,1,50,90),
(1,2,50,90),(2,2,50,90),(3,2,50,90),(4,2,50,90),(5,2,50,90),(6,2,50,90),(7,2,50,90),(8,2,50,90),
(1,3,50,90),(2,3,50,90),(3,3,50,90),(4,3,50,90),(5,3,50,90),(6,3,50,90),(7,3,50,90),(8,3,50,90),
(1,4,50,90),(2,4,50,90),(3,4,50,90),(4,4,50,90),(5,4,50,90),(6,4,50,90),(7,4,50,90),(8,4,50,90),
(1,5,50,90),(2,5,50,90),(3,5,50,90),(4,5,50,90),(5,5,50,90),(6,5,50,90),(7,5,50,90),(8,5,50,90),
(1,6,50,90),(2,6,50,90),(3,6,50,90),(4,6,50,90),(5,6,50,90),(6,6,50,90),(7,6,50,90),(8,6,50,90),
(9,7,50,90),(10,7,50,90),(11,7,50,90),(12,7,50,90),(13,7,50,90),
(9,8,50,90),(10,8,50,90),(11,8,50,90),(12,8,50,90),(13,8,50,90),
(9,9,50,90),(10,9,50,90),(11,9,50,90),(12,9,50,90),(13,9,50,90),
(9,10,50,90),(10,10,50,90),(11,10,50,90),(12,10,50,90),(13,10,50,90),
(9,11,50,90),(10,11,50,90),(11,11,50,90),(12,11,50,90),(13,11,50,90),
(9,12,50,90),(10,12,50,90),(11,12,50,90),(12,12,50,90),(13,12,50,90),
(14,13,50,90),(15,13,50,90),(16,13,50,90),(17,13,50,90),(18,13,50,90),(19,13,50,90),(20,13,50,90),(1,13,50,90),(2,13,50,90),(3,13,50,90),(4,13,50,90),
(14,14,50,90),(15,14,50,90),(16,14,50,90),(17,14,50,90),(18,14,50,90),(19,14,50,90),(20,14,50,90),(1,14,50,90),(2,14,50,90),(3,14,50,90),(4,14,50,90),
(14,15,50,90),(15,15,50,90),(16,15,50,90),(17,15,50,90),(18,15,50,90),(19,15,50,90),(20,15,50,90),(1,15,50,90),(2,15,50,90),(3,15,50,90),(4,15,50,90),
(14,16,50,90),(15,16,50,90),(16,16,50,90),(17,16,50,90),(18,16,50,90),(19,16,50,90),(20,16,50,90),(1,16,50,90),(2,16,50,90),(3,16,50,90),(4,16,50,90),
(14,17,50,90),(15,17,50,90),(16,17,50,90),(17,17,50,90),(18,17,50,90),(19,17,50,90),(20,17,50,90),(1,17,50,90),(2,17,50,90),(3,17,50,90),(4,17,50,90),
(14,18,50,90),(15,18,50,90),(16,18,50,90),(17,18,50,90),(18,18,50,90),(19,18,50,90),(20,18,50,90),(1,18,50,90),(2,18,50,90),(3,18,50,90),(4,18,50,90),
(20,19,50,90),(17,19,50,90),(16,19,50,90),(15,19,50,90),(14,19,50,90),(13,19,50,90),(10,19,50,90),(6,19,50,90),(3,19,50,90),(2,19,50,90),(7,19,50,90),(9,19,50,90),
(20,20,50,90),(17,20,50,90),(16,20,50,90),(15,20,50,90),(14,20,50,90),(13,20,50,90),(10,20,50,90),(6,20,50,90),(3,20,50,90),(2,20,50,90),(7,20,50,90),(9,20,50,90),
(20,21,50,90),(17,21,50,90),(16,21,50,90),(15,21,50,90),(14,21,50,90),(13,21,50,90),(10,21,50,90),(6,21,50,90),(3,21,50,90),(2,21,50,90),(7,21,50,90),(9,21,50,90),
(20,22,50,90),(17,22,50,90),(16,22,50,90),(15,22,50,90),(14,22,50,90),(13,22,50,90),(10,22,50,90),(6,22,50,90),(3,22,50,90),(2,22,50,90),(7,22,50,90),(9,22,50,90),
(20,23,50,90),(17,23,50,90),(16,23,50,90),(15,23,50,90),(14,23,50,90),(13,23,50,90),(10,23,50,90),(6,23,50,90),(3,23,50,90),(2,23,50,90),(7,23,50,90),(9,23,50,90),
(20,24,50,90),(17,24,50,90),(16,24,50,90),(15,24,50,90),(14,24,50,90),(13,24,50,90),(10,24,50,90),(6,24,50,90),(3,24,50,90),(2,24,50,90),(7,24,50,90),(9,24,50,90),
(11,25,50,90),(12,25,50,90),(13,25,50,90),(14,25,50,90),(17,25,50,90),(19,25,50,90),
(11,26,50,90),(12,26,50,90),(13,26,50,90),(14,26,50,90),(17,26,50,90),(19,26,50,90),
(11,27,50,90),(12,27,50,90),(13,27,50,90),(14,27,50,90),(17,27,50,90),(19,27,50,90),
(11,28,50,90),(12,28,50,90),(13,28,50,90),(14,28,50,90),(17,28,50,90),(19,28,50,90),
(11,29,50,90),(12,29,50,90),(13,29,50,90),(14,29,50,90),(17,29,50,90),(19,29,50,90),
(11,30,50,90),(12,30,50,90),(13,30,50,90),(14,30,50,90),(17,30,50,90),(19,30,50,90);



CREATE TABLE personnel (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
co_id INT UNSIGNED NOT NULL,
tr_id INT UNSIGNED NOT NULL,
PRIMARY KEY(id),
CONSTRAINT coFK_personnel FOREIGN KEY (co_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT trFK_personnel FOREIGN KEY (tr_id) REFERENCES trainers(id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT personnelUnique UNIQUE(co_id, tr_id)
);

INSERT INTO personnel (co_id, tr_id)
VALUES(1,1),(1,2),(1,3),(1,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(3,9),(3,10),(3,1),(3,2),(4,3),(4,4),(4,5),(4,6),(4,7),(4,8),(4,9),(4,10),(5,7),(5,8),(5,9),(5,10);

CREATE TABLE users (
username VARCHAR(10) NOT NULL,
pass VARCHAR(20) NOT NULL,
PRIMARY KEY(username, pass)
);

INSERT INTO users(username, pass) VALUES('admin', '1234');
INSERT INTO users(username, pass) VALUES('val', 'val1');



-- ----------------------------------------------------------------
-- SQL QUERIES
-- ----------------------------------------------------------------
/*

SELECT * FROM students;

SELECT * FROM trainers;

SELECT * FROM assignments;

SELECT * FROM courses;

-- Students per Course
SELECT c.ctitle as Course, s.sfname as 'First Name', s.slname as 'Last Name'
FROM enrollments as e, students as s, courses as c
WHERE e.st_id=s.id
AND e.co_id=c.id
GROUP BY c.id, s.id;

-- Trainers per Course
SELECT c.ctitle as Course, t.tfname as 'First Name', t.tlname as 'Last Name'
FROM personnel as p, trainers as t, courses as c
WHERE p.co_id=c.id AND p.tr_id=t.id
GROUP BY c.id, t.id;

-- Assignments per Course
SELECT c.ctitle as Course, a.atitle as 'Assignment Title', a.adescr as 'Assignment Description', a.asubDate as 'Submission Date'
FROM assignments as a, courses as c
WHERE a.co_id=c.id
GROUP BY c.id, a.id;

-- Assignments per Course per Student;
SELECT s.sfname as 'First Name', s.slname as 'Last Name', c.ctitle as Course, a.atitle as 'Assignment', a.asubDate as 'Submission Date'
FROM students as s, homeworks as h, assignments as a, courses as c
WHERE s.id=h.st_id AND h.as_id=a.id AND a.co_id=c.id
GROUP BY s.id, c.id, a.id;

-- List of Students who belong to more than one Courses.
SELECT s.id as id, s.sfname as 'First Name', s.slname as 'Last Name', count(st_id) as Enrollments
FROM enrollments as e, students as s
WHERE e.st_id=s.id
GROUP BY s.id
HAVING Enrollments > 1;

*/

-- ----------------------------------------------------------------------------------------------------------------
