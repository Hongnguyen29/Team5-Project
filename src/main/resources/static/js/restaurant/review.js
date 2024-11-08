const jwt = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant();
});

function getRestaurantId() {
  const url = window.location.href; // Lấy URL hiện tại
  const parts = url.split("/"); // Tách URL theo dấu "/"
  return parts[parts.length - 2]; // Lấy phần cuối cùng là ID
}

function fetchRestaurant() {
  const id = getRestaurantId();

  fetch(`/restaurant/${id}`)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((restaurant) => {
      // Hiển thị thông tin nhà hàng
      document.getElementById("restImage").src = restaurant.restImage;
      document.getElementById("nameRestaurant").textContent =
        restaurant.nameRestaurant;
      document.getElementById("address").textContent = restaurant.address;
      document.getElementById("phone").textContent = restaurant.phone;
      document.getElementById("category").textContent = restaurant.category;
    })
    .catch((error) => {
      console.error("Lỗi khi lấy thông tin nhà hàng:", error);
    });
}
const id = getRestaurantId();

fetch(`/restaurant/${id}/star`)
  .then((response) => {
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json();
  })
  .then((data) => {
    const starCount = parseFloat(data); // Chuyển đổi giá trị sang số thực
    document.getElementById("star-num").textContent = starCount.toFixed(1); // Hiển thị với một chữ số thập phân
    displayStars(starCount);
  })
  .catch((error) => {
    console.error("Lỗi khi lấy thông tin sao:", error);
  });

function displayStars(starCount) {
  const starContainer = document.getElementById("star");
  starContainer.innerHTML = ""; // Xóa nội dung cũ

  for (let i = 1; i <= 5; i++) {
    const starElement = document.createElement("span");
    starElement.className = "star"; // Thêm lớp để định dạng

    starElement.innerHTML = i <= Math.floor(starCount) ? "★" : "☆"; // Hiển thị sao đầy hoặc rỗng

    starContainer.appendChild(starElement);
  }
}

// Lấy ID nhà hàng
const restId = getRestaurantId();

// Lấy phần tử chứa danh sách đánh giá
const reviewContainer = document.getElementById("list-review");

fetch(`/restaurant/${restId}/review`)
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then((data) => {
    const reviews = data
      .map(
        (review) => `
        <div class="review-item">
          <div class="review-info"> 
            <h3>${renderStars(review.star)}</h3>
            <div style="display: flex; align-items: center;">
              <h4 class="user">${review.username}</h4>
              <p class="time">${new Date(
                review.timeCreate
              ).toLocaleString()}</p>
            </div>
            <p class="status">${review.content}</p>
          </div>
          <img src="${review.image}" alt="${
          review.username
        }" class="review-image">
        </div>
      `
      )
      .join("");

    // Thêm vào container
    reviewContainer.innerHTML = reviews;
  })
  .catch((error) => {
    console.error("Có vấn đề với việc lấy đánh giá:", error);
    reviewContainer.innerHTML = "<p>Không thể tải danh sách đánh giá.</p>";
  });

// Hàm hiển thị sao
function renderStars(star) {
  let starsHTML = "";
  for (let i = 1; i <= 5; i++) {
    starsHTML += `<span class="star">${i <= star ? "★" : "☆"}</span>`;
  }
  return starsHTML; // Trả về HTML cho các sao
}
