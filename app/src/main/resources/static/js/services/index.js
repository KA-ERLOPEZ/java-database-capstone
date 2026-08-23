

import { openModal } from '../components/modals.js';
import { API_BASE_URL } from '../config/config.js';

const ADMIN_API = API_BASE_URL + '/admin';
const DOCTOR_API = API_BASE_URL + '/doctor';
const PATIENT_API = API_BASE_URL + '/patient';

window.onload = function () {
    const adminBtn = document.getElementById('admin-btn');
    const doctorBtn = document.getElementById('doctor-btn');
    const patientBtn = document.getElementById('patient-btn');

    if (adminBtn) {
        adminBtn.addEventListener('click', () => {
            openModal('adminLogin');
        })
    }
    if (doctorBtn) {
        doctorBtn.addEventListener('click', () => {
            openModal('doctorLogin')
        })
    }

    if (patientBtn) {
        patientBtn.addEventListener('click', () => {
            openModal('patientLogin')
        })
    }
};

window.adminLoginHandler = async function () {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const admin = { username, password };

    try {
        const response = await fetch(ADMIN_API, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(admin)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token);
            localStorage.setItem('userRole', "admin");
            selectRole("admin");
        } else {
            alert("Invalid ADMIN credentials.")
        }
    } catch (error) {
        console.error("ADMIN login error:", error);
        alert("Something went wrong. Please try again.")

    }
};

window.doctorLoginHandler = async function () {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const doctorCredentials = { email, password };

    try {
        const response = await fetch(`${DOCTOR_API}/login`, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(doctorCredentials)
        });
        if (response.ok) {
            const data = await response.json();

            localStorage.setItem('token', data.token);
            localStorage.setItem('userRole', 'doctor');
            selectRole("doctor");
        } else {
            const data = await response.json();
            alert("Invalid doctor credentials:", data.message);
        }
    } catch (error) {
        console.error("Doctor login error:", error);
        alert("Something went wrong. Please try again.");
    }
}

window.patientLoginHandler = async function () {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const patientCredentials = { email, password };
    try {
        const response = await fetch(PATIENT_API, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(patientCredentials)
        })

        if (response.ok) {
            const data = await response.json();

            localStorage.setItem('token', data.token);
            localStorage.setItem('userRole', 'patient');
            selectRole('patient')

        } else {
            alert("Invalid doctor credentials.");
        }
    } catch (error) {
        console.error("Patient login error:", error);
        alert("Something went wrong. Please try again.");
    }
}



/*
  Import the openModal function to handle showing login popups/modals
  Import the base API URL from the config file
  Define constants for the admin and doctor login API endpoints using the base URL
  
  Use the window.onload event to ensure DOM elements are available after page load
  Inside this function:
    - Select the "adminLogin" and "doctorLogin" buttons using getElementById
    - If the admin login button exists:
        - Add a click event listener that calls openModal('adminLogin') to show the admin login modal
    - If the doctor login button exists:
        - Add a click event listener that calls openModal('doctorLogin') to show the doctor login modal


  Define a function named adminLoginHandler on the global window object
  This function will be triggered when the admin submits their login credentials

  Step 1: Get the entered username and password from the input fields
  Step 2: Create an admin object with these credentials

  Step 3: Use fetch() to send a POST request to the ADMIN_API endpoint
    - Set method to POST
    - Add headers with 'Content-Type: application/json'
    - Convert the admin object to JSON and send in the body

  Step 4: If the response is successful:
    - Parse the JSON response to get the token
    - Store the token in localStorage
    - Call selectRole('admin') to proceed with admin-specific behavior

  Step 5: If login fails or credentials are invalid:
    - Show an alert with an error message

  Step 6: Wrap everything in a try-catch to handle network or server errors
    - Show a generic error message if something goes wrong


  Define a function named doctorLoginHandler on the global window object
  This function will be triggered when a doctor submits their login credentials

  Step 1: Get the entered email and password from the input fields
  Step 2: Create a doctor object with these credentials

  Step 3: Use fetch() to send a POST request to the DOCTOR_API endpoint
    - Include headers and request body similar to admin login

  Step 4: If login is successful:
    - Parse the JSON response to get the token
    - Store the token in localStorage
    - Call selectRole('doctor') to proceed with doctor-specific behavior

  Step 5: If login fails:
    - Show an alert for invalid credentials

  Step 6: Wrap in a try-catch block to handle errors gracefully
    - Log the error to the console
    - Show a generic error message
*/
