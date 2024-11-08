// Fetch function to get the category list from the API and display it in the dropdown
function fetchCategories() {
  fetch("/category")
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((categories) => {
      console.log("Categories:", categories);

      const categorySelect = document.getElementById("category-select");
      categories.forEach((category) => {
        const option = document.createElement("option");
        option.value = category; // Adjust if category is an object
        option.textContent = category.replace("_", " ").toLowerCase();
        categorySelect.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Error fetching categories:", error);
    });
}

// Fetch function to search for restaurants from the API and display the results
function searchRestaurants() {
  const category = document.getElementById("category-select").value;
  const address = document.getElementById("address-input").value.trim();
  const name = document.getElementById("name-input").value.trim();

  // Create query string based on search conditions
  let query = "/search?";
  let hasConditions = false; // Variable to check if there are any conditions

  if (category) {
    query += `category=${category}&`;
    hasConditions = true;
  }
  if (address) {
    query += `address=${encodeURIComponent(address)}&`;
    hasConditions = true;
  }
  if (name) {
    query += `name=${encodeURIComponent(name)}&`;
    hasConditions = true;
  }

  // If no conditions, call API to get all restaurants
  if (!hasConditions) {
    query = "/search"; // Just call the API without conditions
  } else {
    // If there are conditions, remove the last "&" if it exists
    query = query.endsWith("&") ? query.slice(0, -1) : query;
  }

  // Send search request to the API
  fetch(query)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((restaurants) => {
      console.log("Restaurants:", restaurants);

      // Display the list of restaurants
      const resList = document.querySelector(".res-list");
      resList.innerHTML = ""; // Clear previous results

      // Loop through the list and create HTML elements to display the results
      restaurants.forEach((restaurant) => {
        const resItem = document.createElement("div");
        resItem.classList.add("res-item");
        resItem.innerHTML = `
            <img src="${restaurant.restImage}" alt="" class="res-img">
            <div class="res-body">
              <h3>${restaurant.nameRestaurant}</h3>
              <p>Category: ${restaurant.category
                .replace("_", " ")
                .toLowerCase()}</p>
              <button class="js-reservation" onclick="window.location.href='/view/restaurant/${
                restaurant.id
              }'">View Details</button>
            </div>
          `;
        resList.appendChild(resItem);
      });
    })
    .catch((error) => {
      console.error("Error searching for restaurants:", error);
    });
}

// Call the fetchCategories function when the page loads to get the category list
document.addEventListener("DOMContentLoaded", () => {
  fetchCategories();
  document
    .getElementById("search-btn")
    .addEventListener("click", searchRestaurants);
  searchRestaurants();
});

{
  /* <p>Address: ${restaurant.address}</p>
<p>Phone: ${restaurant.phone}</p>
<p>Opening Hours: ${restaurant.openTime} - ${
restaurant.closeTime
}</p> */
}
