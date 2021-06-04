DROP TABLE technical_questions;

CREATE TABLE technical_questions (
id SERIAL,
response VARCHAR(200),
responselinks VARCHAR(200),
CONSTRAINT pk_technical_questions primary key (id)
);

INSERT INTO technical_questions (response, responselinks) VALUES ('What is a class?', 'https://www.interviewbit.com/java-interview-questions/');
INSERT INTO technical_questions (response, responselinks) VALUES ('What is an object?','https://www.edureka.co/blog/interview-questions/java-interview-questions/');
INSERT INTO technical_questions (response, responselinks) VALUES ('What is a method?','https://www.geeksforgeeks.org/commonly-asked-java-programming-interview-questions-set-2/');
INSERT INTO technical_questions (response, responselinks) VALUES ('What is the difference between reference and primitive datatypes','https://www.javatpoint.com/corejava-interview-questions');
INSERT INTO technical_questions (response) VALUES ('Can you explain the principles of OOP?');
INSERT INTO technical_questions (response) VALUES ('What is inheritence?');
INSERT INTO technical_questions (response) VALUES ('Can you explain encapsulation?');
INSERT INTO technical_questions (response) VALUES ('Describe normalization.');
INSERT INTO technical_questions (response) VALUES ('What is a primary key?');
INSERT INTO technical_questions (response) VALUES ('What is a foreign key?');
INSERT INTO technical_questions (response) VALUES ('Describe the difference between Java and JavaScript.');
INSERT INTO technical_questions (response) VALUES ('What is a relational database?');
INSERT INTO technical_questions (response) VALUES ('Can you give some common HTML tags?');
INSERT INTO technical_questions (response) VALUES ('Give an example of how you have used Spring Boot.');
INSERT INTO technical_questions (response) VALUES ('Explain the difference between HTML, CSS, and JavaScript');
INSERT INTO technical_questions (response) VALUES ('What is an SPA?');



CREATE TABLE behavioral_questions (
id SERIAL,
response VARCHAR(200),
responselinks VARCHAR(200),
CONSTRAINT pk_behavioral_questions primary key (id)
);

INSERT INTO behavioral_questions (response,responselinks) VALUES ('Tell me about yourself.','https://devskiller.com/45-behavioral-questions-to-use-during-non-technical-interview-with-developers/');
INSERT INTO behavioral_questions (response,responselinks) VALUES ('How do you handle conflicts at work?','https://www.themuse.com/advice/30-behavioral-interview-questions-you-should-be-ready-to-answer');
INSERT INTO behavioral_questions (response,responselinks) VALUES ('Tell me about your biggest failure at work.','https://www.thebalancecareers.com/top-behavioral-interview-questions-2059618');
INSERT INTO behavioral_questions (response,responselinks) VALUES ('Tell me about your biggest success at work.','https://www.indeed.com/career-advice/interviewing/most-common-behavioral-interview-questions-and-answers');
INSERT INTO behavioral_questions (response,responselinks) VALUES ('Tell me about a time that you worked with people from another team.','https://www.topechelon.com/blog/placement-process/top-behavioral-interview-questions-list-examples/');
INSERT INTO behavioral_questions (response) VALUES ('What is your learning style?');
INSERT INTO behavioral_questions (response) VALUES ('Tell me about a time you implemented a new technology.');
INSERT INTO behavioral_questions (response) VALUES ('Tell me about a time you improved an existing process.');
INSERT INTO behavioral_questions (response) VALUES ('Imagine that you and a team member have conflicting ideas over a project. How would you handle the situation?');
INSERT INTO behavioral_questions (response) VALUES ('What is your biggest strength?');
INSERT INTO behavioral_questions (response) VALUES ('What is your biggest weakness?');
INSERT INTO behavioral_questions (response) VALUES ('How do you stay up to date on changes in technology?');
INSERT INTO behavioral_questions (response) VALUES ('How do you deal with change in the workplace?');
INSERT INTO behavioral_questions (response) VALUES ('Describe a situation in which you made an effort to repair an important relationship. What made this relationship important to you and the business?');
INSERT INTO behavioral_questions (response) VALUES ('Tell me about a time you had to make a decision with limited information.');
INSERT INTO behavioral_questions (response) VALUES ('Describe the steps you took to identiy or understand a complicated problem you have had to deal with on your job.');
INSERT INTO behavioral_questions (response) VALUES ('Imagine you are on a tight deadline. How would you handle a team member not responding in a timely manner.');
INSERT INTO behavioral_questions (response) VALUES ('Tell me about a time you went beyond an external customers expecatations.');
INSERT INTO behavioral_questions (response) VALUES ('How do you set goals for yourself?');



DROP TABLE cover_letter;


CREATE TABLE cover_letter(
id SERIAL,
response VARCHAR(200),

CONSTRAINT pk_cover_letter primary key (id)

);

INSERT INTO cover_letter (response) VALUES ('https://novoresume.com/career-blog/how-to-write-a-cover-letter-guide');
INSERT INTO cover_letter (response) VALUES ('https://www.thebalancecareers.com/software-developer-cover-letter-example-2060144');
INSERT INTO cover_letter (response) VALUES ('https://www.freecodecamp.org/news/how-to-improve-your-cover-letter/');
INSERT INTO cover_letter (response) VALUES ('https://learnitmyway.medium.com/how-to-write-a-cover-letter-as-a-software-developer-cover-letter-and-cv-included-2190e0d23e97');
INSERT INTO cover_letter (response) VALUES ('https://www.jobhero.com/cover-letter/examples/computer-software/software-developer/junior');
INSERT INTO cover_letter (response) VALUES ('https://www.greatsampleresume.com/cover-letter/examples/computer-software/software-developer/junior');


CREATE TABLE business_attire(
id SERIAL,
response VARCHAR(200),

CONSTRAINT pk_business_attire primary key (id)

);

INSERT INTO business_attire (response) VALUES ('https://www.indeed.com/career-advice/starting-new-job/guide-to-business-attire');
INSERT INTO business_attire (response) VALUES ('https://www.thebalancecareers.com/what-is-business-attire-1918075');
INSERT INTO business_attire (response) VALUES ('https://balanceddeveloper.com/the-beginners-guide-to-the-software-engineer-dress-code');


CREATE TABLE pathway_general (
id SERIAL,
response VARCHAR(200),
CONSTRAINT pk_pathway_general primary key (id)
);

INSERT INTO pathway_general (response) VALUES ('https://sites.google.com/techelevator.com/nlrpathwayresourcepage/home')

ALTER TABLE TECHNICAL_QUESTIONS 
ADD RESPONSELINKS VARCHAR(200);


INSERT INTO technical_questions (responselinks) VALUES ('https://www.interviewbit.com/java-interview-questions/');
INSERT INTO technical_questions (responselinks) VALUES ('https://www.edureka.co/blog/interview-questions/java-interview-questions/');
INSERT INTO technical_questions (responselinks) VALUES ('https://www.geeksforgeeks.org/commonly-asked-java-programming-interview-questions-set-2/');
INSERT INTO technical_questions (responselinks) VALUES ('https://www.javatpoint.com/corejava-interview-questions');

SELECT response, responselinks FROM technical_questions;

UPDATE userdetails SET firstname = ?,lastname = ?, emailid = ?, contactnumber = ?, isstudent = ? WHERE user_id = ?; 



