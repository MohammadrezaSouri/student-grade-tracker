
A console-based grade management system built with **pure Java SE**.  
Student grades are stored in PostgreSQL, analyzed with Stream API, and exported to JSON.

---

## Features

- Add students, courses, and grades
- Average score per student
- Failed grades detection (score < 10)
- Top grade per course
- Overall statistics (count, average, max, min)
- Export results to JSON

---

## Tech Stack

| Topic | Implementation |
|---|---|
| Language | Java 25 (SE only) |
| Build Tool | Maven |
| Database | PostgreSQL + raw JDBC |
| Analytics | Stream API (`groupingBy`, `partitioningBy`, `summarizingDouble`) |
| Export | Jackson (JSON) |
| Boilerplate | Lombok |
