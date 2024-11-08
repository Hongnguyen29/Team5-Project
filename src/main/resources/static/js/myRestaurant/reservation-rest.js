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
  fetch("/rest/reservation", {
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
    .map((reservation) => {
      // Get the current time
      const currentTime = new Date();

      // Lấy thời gian đặt chỗ
      const reservationTime = new Date(reservation.time);
      console.log(reservation.time);

      // Tính toán thời gian 1 giờ sau thời gian đặt chỗ
      const oneHourAfterReservation = new Date(reservationTime);
      oneHourAfterReservation.setHours(reservationTime.getHours() + 1);

      // Kiểm tra xem thời gian hiện tại đã qua 1 giờ kể từ thời gian đặt chỗ chưa
      const isPastReservationTimeByOneHour =
        currentTime > oneHourAfterReservation;

      return `
    <div class="reservation-item" id="reservation-${reservation.id}">
      <h4><strong>Restaurant:</strong> ${reservation.restaurantName}</h4>
      <p><strong>Address:</strong> ${reservation.restaurantAddress}</p>
      <p><strong>Customer Name:</strong> ${reservation.nameCustom}</p>
      <p><strong>Time:</strong> ${reservationTime.toLocaleString()}</p>
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

      <!-- Show action buttons if status is ACCEPTED and if more than 30 minutes have passed since reservation time -->
      ${
        reservation.status === "PENDING"
          ? `
        <button onclick="handleReservationResponse(${reservation.id}, true)">Accept</button>
        <button onclick="handleReservationResponse(${reservation.id}, false)">Reject</button>
        `
          : reservation.status === "ACCEPTED" && isPastReservationTimeByOneHour
          ? `
        <button onclick="handleCompleteReservation(${reservation.id}, 'USED')">Used</button>
        <button onclick="handleCompleteReservation(${reservation.id}, 'NO_SHOW')">No Show</button>
        `
          : ""
      }
    </div>
  `;
    })
    .join("");

  // Display reservation items in the container
  container.innerHTML = reservationItems;
}

// Function to handle accept or reject response
function handleReservationResponse(reservationId, isApproved) {
  let data = { approved: isApproved };

  // If rejected, ask for a reason
  if (!isApproved) {
    const reason = prompt(
      "Please enter the reason for rejecting this reservation:"
    );
    if (reason) {
      data.reason = reason; // Add reason to the data if rejected
    } else {
      alert("You must provide a reason to reject the reservation.");
      return;
    }
  }

  // Send accept or reject request
  fetch(`/rest/reservation/${reservationId}`, {
    method: "PUT", // Use PUT method to accept or reject the reservation
    headers: {
      "Content-Type": "application/json", // Specify content type as JSON
      Authorization: `Bearer ${jwt}`, // Add token to the Authorization header
    },
    body: JSON.stringify(data), // Send the approval/rejection data
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error while processing the reservation.");
      }
      return response.json();
    })
    .then(() => {
      // After successful response, refresh the reservation list
      const action = isApproved ? "accepted" : "rejected";
      alert(`The reservation has been ${action}.`);
      fetchReservations(); // Reload the reservation list
    })
    .catch((error) => {
      console.error(
        "An error occurred while processing the reservation:",
        error
      );
      alert(
        `An error occurred while processing the reservation. Please try again.`
      );
    });
}

// Function to handle "Used" or "No Show" actions for accepted reservations
function handleCompleteReservation(reservationId, status) {
  const data = { status: status };

  // Send "Used" or "No Show" request
  fetch(`/rest/complete/${reservationId}`, {
    method: "PUT", // Use PUT method to update reservation status
    headers: {
      "Content-Type": "application/json", // Specify content type as JSON
      Authorization: `Bearer ${jwt}`, // Add token to the Authorization header
    },
    body: JSON.stringify(data), // Send the status update data
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Error while completing the reservation.");
      }
      return response.json();
    })
    .then(() => {
      // After successful response, refresh the reservation list
      const action = status === "USED" ? "marked as used" : "marked as no-show";
      alert(`The reservation has been ${action}.`);
      fetchReservations(); // Reload the reservation list
    })
    .catch((error) => {
      console.error(
        "An error occurred while completing the reservation:",
        error
      );
      alert(
        `An error occurred while completing the reservation. Please try again.`
      );
    });
}
