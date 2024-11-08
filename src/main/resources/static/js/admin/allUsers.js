const jwt = localStorage.getItem("token"); // Assuming JWT is stored in localStorage

// Function to fetch users and display them in the table
document.addEventListener("DOMContentLoaded", () => {
  fetchUsers(); // Call the function to fetch users on page load
});

// Fetch users from the server
function fetchUsers() {
  if (!jwt) {
    alert("You must be logged in to view user data.");
    return;
  }

  // Fetch user data from /admin/users
  fetch("/admin/users", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwt}`, // Add JWT token to the request header
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Error fetching data! Status code: ${response.status}`);
      }
      return response.json();
    })
    .then((users) => {
      displayUsers(users); // Call function to display the user data in the table
    })
    .catch((error) => {
      console.error("Error fetching users:", error);
      alert("An error occurred while fetching the user data.");
    });
}

// Function to display users in the table
function displayUsers(users) {
  const tableBody = document
    .getElementById("user-table")
    .getElementsByTagName("tbody")[0];
  tableBody.innerHTML = ""; // Clear the table before adding new data

  // Loop through users and create rows for the table
  users.forEach((user) => {
    const row = document.createElement("tr");
    row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.phone}</td>
            <td>${user.role}</td>
        `;
    tableBody.appendChild(row); // Append the row to the table body
  });
}
