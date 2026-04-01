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
- doctor_schedule

### Table: patients
- id_persona: INT, PRIMARY KEY
- grupo_sanguineo: VARCHAR, NOT NULL
- alergias: VARCHAR, NOT NULL
- enfermedades_cronicas: VARCHAR
- medicacion_actual: VARCHAR
- id_estado: INT, NOT NULL
- FOREIGN KEY(id_persona) REFERENCES persons(id)
- FOREIGN KEY(id_estado) REFERENCES status(id)
  
### Table: doctors
- id_persona: INT, PRIMARY KEY
- numero_matricula: VARCHAR, UNIQUE, NOT NULL
- especialidad_id: INT, NOT NULL
- anio_graduacion: INT, NOT NULL
- telefono_profesional VARCHAR
- email_profesional VARCHAR
- FOREIGN KEY(id_persona) REFERENCES persons(id)
- FOREIGN KEY(id_estado) REFERENCES status(id)
- FOREIGN KEY(especialidad_id) REFERENCES especiality(id)

### Table: persons
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- nombre: VARCHAR, NOT NULL
- apellido: VARCHAR, NOT NULL
- direccion: VARCHAR, NOT NULL
- email: VARCHAR, NOT NULL
- telefono: VARCHAR, NOT NULL
- cedula: VARCHAR, NOT NULL
- ruc: VARCHAR
  
### Table: appoiments
- id: INT, Primary Key, Auto Increment
- doctor_id: INT, Foreign Key → doctors(id)
- patient_id: INT, Foreign Key → patients(id)
- appointment_time: DATETIME, Not Null
- status: INT (0 = Scheduled, 1 = Completed, 2 = Cancelled)


## MongoDB Collection Design

### Colección: medical_notes
{
  "_id": "note_001",
  "appointment_id": 1203,
  "patient_id": 45,
  "doctor_id": 12,
  "date": "2026-04-01T09:30:00Z",
  "notes": "Paciente presenta dolor abdominal leve. Se recomienda dieta blanda.",
  "symptoms": ["dolor abdominal", "náuseas,
  "diagnosis": "Gastritis leve",
  "tags": ["gastroenterology", "follow-up" ],
  "attachments": [
    {
      "type": "image",
      "url": "https://storage/app/xray1.png"
    }
  ],
  "metadata": {
    "created_by": "doctor",
    "version": 1
  }
}

### Colección: prescriptions
{
  "_id": "presc_001",
  "patient_id": 45,
  "doctor_id": 12,
  "appointment_id": 1203,
  "date": "2026-04-01",
  "medications": [
    {
      "name": "Omeprazole",
      "dose": "20mg",
      "frequency": "1 vez al día",
      "duration": "14 días"
    },
    {
      "name": "Paracetamol",
      "dose": "500mg",
      "frequency": "cada 8 horas",
      "duration": "5 días"
    }
  ],
  "instructions": "Tomar antes de las comidas",
  "tags": ["gastritis"],
  "metadata": {
    "pharmacy_suggested": true
  }
}

### Coleccion: message
{
  "_id": "msg_001",
  "conversation_id": "conv_45_12",
  "participants": [
    {
      "id": 45,
      "role": "patient"
    },
    {
      "id": 12,
      "role": "doctor"
    }
  ],
  "messages": [
    {
      "sender_id": 45,
      "text": "Doctor, sigo con dolor.",
      "timestamp": "2026-04-01T10:00:00Z"
    },
    {
      "sender_id": 12,
      "text": "Continúe con la medicación y avíseme.",
      "timestamp": "2026-04-01T10:05:00Z"
    }
  ],
  "metadata": {
    "last_updated": "2026-04-01T10:05:00Z"
  }
}

### Colección: activity_logs
{
  "_id": "log_001",
  "user_id": 45,
  "action": "REGISTER",
  "description": "Paciente se registró en el sistema",
  "timestamp": "2026-04-01T08:00:00Z",
  "ip_address": "192.168.1.1",
  "device": {
    "browser": "Chrome",
    "os": "Windows"
  },
  "metadata": {
    "source": "web"
  }
}
