import { API_BASE_URL } from "../config/config.js";


const DOCTOR_API = API_BASE_URL + '/doctor';


/*
  Import the base API URL from the config file
  Define a constant DOCTOR_API to hold the full endpoint for doctor-related actions


  Function: getDoctors
  Purpose: Fetch the list of all doctors from the API

   Use fetch() to send a GET request to the DOCTOR_API endpoint
   Convert the response to JSON
   Return the 'doctors' array from the response
   If there's an error (e.g., network issue), log it and return an empty array
*/
async function getDoctors() {

    try {
        const response = await fetch(DOCTOR_API)
        if (response.ok) {
            return await response.json();
        }
        //alert("The data could not be obtained");
        console.log("The data could not be obtained");
        return [];
    } catch (error) {
        console.error(" error:", error);
        // alert("Something went wrong. Please try again.");
        return [];
    }
}
/*
  Function: deleteDoctor
  Purpose: Delete a specific doctor using their ID and an authentication token

   Use fetch() with the DELETE method
    - The URL includes the doctor ID and token as path parameters
   Convert the response to JSON
   Return an object with:
    - success: true if deletion was successful
    - message: message from the server
   If an error occurs, log it and return a default failure response
   */
export async function deleteDoctor(id, token) {

    try {
        const response = await fetch(`${DOCTOR_API}/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer${token}`,
                'Content-type': 'application/json'
            }
        });

        if (response.ok) {
            const data = await response.json();
            // alert("Delete doctor successfuly: ", data.message);
            return { 'success': true, 'message': data.message };
        } else {
            //alert("The record could not be deleted");
            return { 'success': false, 'message': data.message };;
        }

    } catch (error) {
        console.error(" error:", error);
        // alert("Something went wrong. Please try again.");
        return error;
    }

}
/*
Function: saveDoctor
Purpose: Save (create) a new doctor using a POST request

 Use fetch() with the POST method
  - URL includes the token in the path
  - Set headers to specify JSON content type
  - Convert the doctor object to JSON in the request body

 Parse the JSON response and return:
  - success: whether the request succeeded
  - message: from the server

 Catch and log errors
  - Return a failure response if an error occurs
  */

async function saveDoctor(doctor, token) {

    try {
        const response = await fetch(`${DOCTOR_API}/${token}`, {
            method: 'POST',
            headers: {
                //'Authorization':`Bearer ${token}`,
                'Content-type': 'application/json'
            },
            body: JSON.stringify(doctor)
        });
        if (response.ok) {
            const data = await response.json();
            if (response.status === 200) {
                // alert("Doctor saving  successfuly: ", data.message);
                return { 'success': true, 'message': data.success };

            } else {
                //alert("Error saving the doctor");
                return { 'success': false, 'message': data.error };;
            }

        }
    } catch (error) {
        console.error(" error:", error.message);
        //alert("Something went wrong. Please try again.");
        return error;
    }
}
/*
Function: filterDoctors
Purpose: Fetch doctors based on filtering criteria (name, time, and specialty)

Use fetch() with the GET method
 - Include the name, time, and specialty as URL path parameters
Check if the response is OK
 - If yes, parse and return the doctor data
 - If no, log the error and return an object with an empty 'doctors' array

Catch any other errors, alert the user, and return a default empty result
*/
async function filterDoctors(filters = {}, token) {

    const params = new URLSearchParams(filters).toString();
    const urlWithParams = params ? `${DOCTOR_API}?${params}` : DOCTOR_API;

    try {
        const response = await fetch(urlWithParams, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-type': 'application/json'
            }
        });
        if (response.ok) {
            return await response.json();
        }

        alert("The data could not be obtained");
        return [];
    } catch (error) {
        console.error(" error:", error);
        //alert("Something went wrong. Please try again.");
        return [];

    }


}

export {
    saveDoctor,
    getDoctors,
    filterDoctors
};
