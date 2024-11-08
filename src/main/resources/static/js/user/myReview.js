// Get JWT token from localStorage
const jwt = localStorage.getItem("token");

// If there is no token, redirect the user to the login page
if (!jwt) {
  location.href = "/view/login"; // Redirect to login page if no token
}

document.addEventListener("DOMContentLoaded", () => {
  fetchReviews(); // Call the function to fetch reviews once the page has loaded
});

// Function to fetch the user's reviews from the API
function fetchReviews() {
  fetch("/user/review", {
    method: "GET",
    headers: {
      "Content-Type": "application/json", // Set the content type for the request
      Authorization: `Bearer ${jwt}`, // Add token to the authorization header
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json(); // Parse the response as JSON
    })
    .then((data) => {
      console.log("Received review data:", data); // Log the received data for testing
      displayReviews(data); // Display the reviews
    })
    .catch((error) => {
      console.error("Error fetching reviews:", error);
      alert("An error occurred, unable to load reviews.");
    });
}

// Function to display the reviews
function displayReviews(reviews) {
  const reviewContainer = document.getElementById("reviewContainer");

  // Check if reviewContainer exists
  if (!reviewContainer) {
    console.error("Could not find 'reviewContainer' element.");
    return;
  }

  // If there are no reviews
  if (!reviews || reviews.length === 0) {
    reviewContainer.innerHTML = "<p>No reviews available.</p>";
    return;
  }

  // Create HTML to display the reviews
  const reviewItems = reviews
    .map((review) => {
      // Generate the stars based on the 'star' value
      const stars = renderStars(review.star);

      return `
        <div class="review-item" id="review-${review.id}">
          <div class="review-info">
            <h3>${stars}</h3> <!-- Display stars -->
            <div style="display: flex; align-items: center;">
              <h4 class="user">${review.username}</h4>
              <p class="time">${new Date(
                review.timeCreate
              ).toLocaleString()}</p> <!-- Display time -->
            </div>
            <p class="restaurant-name">${
              review.restaurantName
            }</p> <!-- Display restaurant name -->
            <p class="status">${
              review.content
            }</p> <!-- Display review content -->
          </div>
          <img src="${
            review.image
          }" alt="Review Image" class="review-image" /> <!-- Display image -->
          <div class="review-actions">
            <button onclick="deleteReview(${review.id})">Delete</button>
            <a href="/view/review/${review.id}" class="update-btn">Update</a>
            <a href="/view/restaurant/${
              review.restaurantId
            }" class="update-btn">Restaurant View</a>
          </div>
        </div>
      `;
    })
    .join(""); // Join the review items into a single HTML string

  // Insert the reviews HTML into the review container
  reviewContainer.innerHTML = reviewItems;
}

// Function to render stars based on the star count
function renderStars(starCount) {
  let starsHTML = "";
  for (let i = 1; i <= 5; i++) {
    // Use solid star (★) or empty star (☆) for display
    starsHTML += i <= starCount ? "★" : "☆";
  }
  return starsHTML; // Return the star HTML string
}

// Function to handle the event of deleting a review
function deleteReview(reviewId) {
  const confirmation = confirm("Are you sure you want to delete this review?");
  if (!confirmation) return;

  fetch(`/user/review/${reviewId}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`, // Add token to the authorization header
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      alert("Review deleted successfully.");
      document.getElementById(`review-${reviewId}`).remove(); // Remove the review from the DOM
    })
    .catch((error) => {
      console.error("Error deleting review:", error);
      alert("An error occurred while deleting the review.");
    });
}

// Function to handle the event of updating a review
function updateReview(reviewId) {
  const newContent = prompt("Enter new review content:", "");
  if (!newContent) return;

  // Perform the review update via the API
  fetch(`/user/review/${reviewId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`, // Add token to the authorization header
    },
    body: JSON.stringify({
      content: newContent, // Update the review content
    }),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      alert("Review updated successfully.");
      location.reload(); // Reload the page to get the updated list of reviews
    })
    .catch((error) => {
      console.error("Error updating review:", error);
      alert("An error occurred while updating the review.");
    });
}
