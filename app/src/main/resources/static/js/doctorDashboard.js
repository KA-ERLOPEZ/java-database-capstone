/*
  Import getAllAppointments to fetch appointments from the backend
  Import createPatientRow to generate a table row for each patient appointment
*/
import { getAllAppointments } from './services/appointmentRecordService.js';
import { createPatientRow } from './components/patientRows.js';
/*
  Get the table body where patient rows will be added
  Initialize selectedDate with today's date in 'YYYY-MM-DD' format
  Get the saved token from localStorage (used for authenticated API calls)
  Initialize patientName to null (used for filtering by name)
*/
const patientTableBody = document.getElementById('patientTableBody');
let selectedDate = null; //new Date().toISOString().split('T')[0];
let patientName = null;
const token = localStorage.getItem("token");
/*

  Add an 'input' event listener to the search bar
  On each keystroke:
    - Trim and check the input value
    - If not empty, use it as the patientName for filtering
    - Else, reset patientName to "null" (as expected by backend)
    - Reload the appointments list with the updated filter
*/
const searchBar = document.getElementById('searchBar');
searchBar.addEventListener('input', (event) => {
    const value = event.target.value.trim();
    patientName = value || null;
    loadAppointments();
});
/*
  Add a click listener to the "Today" button
  When clicked:
    - Set selectedDate to today's date
    - Update the date picker UI to match
    - Reload the appointments for today
*/
const datePicker = document.getElementById('datePicker');
const todayButton = document.getElementById('todayButton');
todayButton.addEventListener('click', () => {
    selectedDate = new Date().toISOString().split('T')[0];
    datePicker.value = selectedDate;
    loadAppointments();
})
/*

  Add a change event listener to the date picker
  When the date changes:
    - Update selectedDate with the new value
    - Reload the appointments for that specific date
*/
datePicker.addEventListener('change', () => {
    if (!datePicker.value) {
        console.log("Fecha vacía, cancelando");
        return;
    }
    selectedDate = datePicker.value;
    console.log(selectedDate);
    loadAppointments();
})
/*
  Function: loadAppointments
  Purpose: Fetch and display appointments based on selected date and optional patient name

  Step 1: Call getAllAppointments with selectedDate, patientName, and token
  Step 2: Clear the table body content before rendering new rows
*/
async function loadAppointments() {
    try {
        console.log("loadAppointments -> selectedDate:", selectedDate);
        console.trace();
        const response = await getAllAppointments(selectedDate, patientName, token);
        patientTableBody.innerHTML = "";

        console.log(response.appointments);
 /*
  Step 3: If no appointments are returned:
    - Display a message row: "No Appointments found for today."

 Step 4: If appointments exist:
    - Loop through each appointment and construct a 'patient' object with id, name, phone, and email
    - Call createPatientRow to generate a table row for the appointment
    - Append each row to the table body

Step 5: Catch and handle any errors during fetch:
    - Show a message row: "Error loading appointments. Try again later."
*/
if (response.appointments.length < 1) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
        <td colspan="4">No Appointments found for today.</td>
    `;
    patientTableBody.appendChild(tr);
    return;
}

    response.appointments.forEach(appointment => {

        const patient = {
            id: appointment.patient.id,
            name: appointment.patient.name,
            phone: appointment.patient.phone,
            email: appointment.patient.email
        }

        const row = createPatientRow(
            patient,
            appointment.id,
            appointment.doctor.id
        );

        patientTableBody.appendChild(row);
        
    });
    } catch (error) {
        console.log(error);

        patientTableBody.innerHTML = `
            <tr>
                <td colspan="4">Error loading appointments. Try again later.</td>
            </tr>
        `;
    }
}

 /*
When the page is fully loaded (DOMContentLoaded):
     - Call renderContent() (assumes it sets up the UI layout)
     - Call loadAppointments() to display today's appointments by default
*/
document.addEventListener('DOMContentLoaded', ()=>{
    renderContent();
    loadAppointments();
})