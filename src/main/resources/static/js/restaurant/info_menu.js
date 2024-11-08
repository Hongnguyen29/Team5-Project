// Lấy token từ localStorage
const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";

// Lấy ID nhà hàng từ URL
function getRestaurantId() {
  const url = window.location.href;
  const parts = url.split("/");
  return parts[parts.length - 1]; // Lấy phần cuối cùng là ID
}

const restId = getRestaurantId(); // Xác định restId ngay khi script chạy

document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant();
  fetchProfile(); // Fetch thông tin profile sau khi DOM đã tải xong
});

// Hàm fetch thông tin nhà hàng
function fetchRestaurant() {
  fetch(`/restaurant/${restId}`) // Thay đổi URL này thành URL API thực tế
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
      document.getElementById("description").textContent =
        restaurant.description;
      document.getElementById("address").textContent = restaurant.address;
      document.getElementById("phone").textContent = restaurant.phone;
      document.getElementById("openTime").textContent = restaurant.openTime;
      document.getElementById("closeTime").textContent = restaurant.closeTime;
      document.getElementById("category").textContent = restaurant.category;
      document.getElementById("status").textContent = restaurant.status;
    })
    .catch((error) => {
      console.error("Lỗi khi lấy thông tin nhà hàng:", error);
    });
}

// Hàm lấy thông tin profile từ API
function fetchProfile() {
  fetch("/auth/profile", {
    headers: {
      Authorization: `Bearer ${jwt}`,
    },
  })
    .then((response) => {
      if (!response.ok) {
        localStorage.removeItem("token");
        location.href = "/view/login";
      }
      return response.json();
    })
    .then((profile) => {
      // Sau khi lấy thông tin profile, tạo các liên kết điều hướng
      createNavigationLinks(profile.role);
    })
    .catch((error) => {
      console.error("Lỗi khi lấy thông tin profile:", error);
    });
}

// Hàm tạo các liên kết điều hướng dựa trên vai trò
function createNavigationLinks(role) {
  const actionLinksContainer = document.getElementById("action-links");
  actionLinksContainer.innerHTML = ""; // Xóa nội dung cũ

  // Tạo liên kết "Reviews" (luôn luôn có)
  const reviewsLink = document.createElement("a");
  reviewsLink.href = `/view/restaurant/${restId}/review`;
  reviewsLink.textContent = "Reviews";
  reviewsLink.classList.add("reviews-link"); // Thêm class
  reviewsLink.style.marginRight = "15px"; // Thêm khoảng cách giữa các liên kết

  // Nếu role là "USER", thêm liên kết "Reservation"
  if (role === "USER") {
    const reservationLink = document.createElement("a");
    reservationLink.href = `/view/restaurant/${restId}/reservation`;
    reservationLink.textContent = "Reservation";
    reservationLink.classList.add("reservation-link"); // Thêm class
    actionLinksContainer.appendChild(reservationLink); // Thêm vào container
  }

  // Thêm liên kết "Reviews" vào container
  actionLinksContainer.appendChild(reviewsLink);
}

const menuContainer = document.getElementById("list-menu");

fetch(`/restaurant/${restId}/menu`)
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then((data) => {
    // Duyệt qua dữ liệu và tạo HTML
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
            </div>
        `
      )
      .join("");

    // Thêm vào container
    menuContainer.innerHTML = menuItems;
  })
  .catch((error) => {
    console.error("There was a problem with the fetch operation:", error);
    menuContainer.innerHTML = "<p>Failed to load menu items.</p>";
  });
