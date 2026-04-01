## MYSQL Database Design
- Patients
- Doctors
- Persons
- Appoiments
- Users
- Roles
- Clinic_location
- payments
- Admin

### Table: patients
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- grupo_sanguineo: VARCHAR, NOT NULL
- alergias: VARCHAR, NOT NULL
- enfermedades_cronicas: VARCHAR
- medicacion_actual: VARCHAR
