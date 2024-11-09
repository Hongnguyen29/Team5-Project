const jwt = localStorage.getItem("token");

// If no token is found, redirect to the login page
if (!jwt) {
  location.href = "/view/login";
}

document.addEventListener("DOMContentLoaded", () => {
  fetchReservations(); // Call the function to fetch the reservation list when the page loads
});

// Function to fetch the reservation list by sending a GET request
function fetchReservations() {
  fetch("/user/reservation", {
    method: "GET", // Specify the method as GET
    headers: {
      "Content-Type": "application/json", // Specify content type as JSON
      Authorization: `Bearer ${jwt}`, // Add JWT token to the Authorization header
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json(); // Convert the response to JSON
    })
    .then((data) => {
      console.log(data); // Log the fetched data for debugging
      displayReservations(data); // Call function to display reservation data
    })
    .catch((error) => {
      console.error("An error occurred:", error);
      alert("An error occurred while fetching the data. Please try again.");
    });
}

// Function to display reservation data on the page
function displayReservations(reservations) {
  const container = document.getElementById("reservationContainer"); // The element to display the reservation list

  // If no reservations are found
  if (!reservations || reservations.length === 0) {
    container.innerHTML = "<p>No reservations found.</p>";
    return;
  }

  // Loop through reservations and create HTML to display
  const reservationItems = reservations
    .map(
      (reservation) => `
    <div class="reservation-item" id="reservation-${reservation.id}">
      <h4><strong>Restaurant:</strong> ${reservation.restaurantName}</h4>
      <p><strong>Address:</strong> ${reservation.restaurantAddress}</p>
            <p>${reservation.nameCustom}</p>
      <p><strong>Time:</strong> ${new Date(
        reservation.time
      ).toLocaleString()}</p>
      <p><strong>People:</strong> ${reservation.peopleNumber}</p>
      <p><strong>Note:</strong> ${reservation.note}</p>
      <p><strong>Status:</strong> ${reservation.status}</p>
      <p><strong>Created At:</strong> ${new Date(
        reservation.createdAt
      ).toLocaleString()}</p>
      <p><strong>Processed At:</strong> ${
        reservation.processedAt
          ? new Date(reservation.processedAt).toLocaleString()
          : "Not processed yet"
      }</p>
      ${
        reservation.status === "PENDING"
          ? ` 
        <button onclick="cancelReservation(${reservation.id})">Cancel Reservation</button>
      `
          : ""
      }

         ${
           reservation.status === "USED"
             ? `
        <button onclick="window.location.href='/view/reservation/${reservation.id}'">Create Review</button>
      `
             : ""
         }
    </div>
  `
    )
    .join("");

  // Display reservation items in the container
  container.innerHTML = reservationItems;
}

// Function to cancel the reservation
function cancelReservation(reservationId) {
  const jwt = localStorage.getItem("token");

  // Confirm cancellation
  if (!confirm("Are you sure you want to cancel this reservation?")) {
    return;
  }

  // Send cancel reservation request
  fetch(`/user/reservation/${reservationId}`, {
    method: "PUT", // Use PUT method to cancel
    headers: {
      "Content-Type": "application/json", // Specify content type as JSON
      Authorization: `Bearer ${jwt}`, // Add token to the Authorization header
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error while canceling the reservation.");
      }
      return response.json();
    })
    .then(() => {
      // After successful cancellation, refresh the reservation list
      alert("The reservation has been canceled.");
      fetchReservations(); // Reload the reservation list
    })
    .catch((error) => {
      console.error(
        "An error occurred while canceling the reservation:",
        error
      );
      alert(
        "An error occurred while canceling the reservation. Please try again."
      );
    });
}
