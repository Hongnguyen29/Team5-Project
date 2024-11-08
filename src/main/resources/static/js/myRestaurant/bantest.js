const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";

document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant(); // Call the function to fetch the restaurant information when the page loads
});

let restId;

// Function to fetch restaurant information
function fetchRestaurant() {
  const jwt = localStorage.getItem("token"); // Get JWT from localStorage

  if (!jwt) {
    console.error("Token not found. You need to log in first.");
    return; // If no JWT, do not make the request
  }

  fetch(`/myRestaurant`, {
    method: "GET", // Ensure the method is GET
    headers: {
      Authorization: `Bearer ${jwt}`, // Add JWT to the Authorization header
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`); // If response is not ok, throw an error
      }
      return response.json(); // Convert the response to JSON
    })
    .then((restaurant) => {
      // Check if the returned data is valid
      if (!restaurant) {
        throw new Error("Invalid restaurant data.");
      }
      restId = restaurant.id; // Save the restaurant ID from the returned data
      // After successfully fetching restaurant info, call the fetchMenus function
      fetchMenus();
    })
    .catch((error) => {
      console.error("Error fetching restaurant info:", error);
      alert("Error fetching restaurant info. Please try again.");
    });
}

// Function to fetch and display the restaurant's menu
function fetchMenus() {
  if (!restId) {
    console.error("No restaurant ID, cannot fetch menu.");
    return;
  }

  fetch(`/restaurant/${restId}/menu`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      const menuContainer = document.getElementById("menuContainer"); // Declare the correct container variable

      // Check if menu is empty
      if (!data || data.length === 0) {
        menuContainer.innerHTML =
          "<p>No menu items available at the moment.</p>";
        return;
      }

      // Create HTML for the menu items
      const menuItems = data
        .map(
          (item) => `
            <div class="menu-item">
                <img src="${item.image}" alt="${item.nameFood}" class="menu-image">
                <div class="menu-info"> 
                  <h3>${item.nameFood}</h3>
                  <p>Price: $${item.price}</p>
                  <p>Status: ${item.status}</p>
                 </div>
                <button onclick="updateMenu(${item.id})">Update Menu</button> <!-- Add Update button -->
            </div>
        `
        )
        .join(""); // Use join to concatenate all HTML elements

      // Add to the container
      menuContainer.innerHTML = menuItems; // Display the menu in the container
    })
    .catch((error) => {
      console.error("There was an issue fetching the menu:", error);
      const menuContainer = document.getElementById("menuContainer");
      menuContainer.innerHTML = "<p>Unable to load the menu list.</p>";
    });
}

// Function to redirect to the menu update page
function updateMenu(menuId) {
  // Redirect to the menu update page for the given menu ID
  window.location.href = `/view/myRestaurant/menu/${menuId}`;
}
