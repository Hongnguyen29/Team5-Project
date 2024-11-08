// Lấy token JWT từ localStorage
const jwt = localStorage.getItem("token");

// Nếu không có token, điều hướng người dùng đến trang đăng nhập
if (!jwt) {
  location.href = "/view/login"; // Thay đổi URL đến trang login nếu không có token
}

// Gửi yêu cầu GET đến API để lấy danh sách nhà hàng mở (open)
fetch("/auth/opens/readAll", {
  method: "GET", // Phương thức GET để lấy dữ liệu
  headers: {
    Authorization: `Bearer ${jwt}`, // Thêm token vào header
    "Content-Type": "application/json", // Đảm bảo dữ liệu trả về là JSON
  },
})
  .then((response) => {
    if (!response.ok) {
      // Xử lý nếu server trả về lỗi
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json(); // Chuyển dữ liệu trả về thành JSON
  })
  .then((data) => {
    // Xử lý dữ liệu nhận được từ server
    displayOpenRestaurantList(data); // Hiển thị danh sách nhà hàng mở
  })
  .catch((error) => {
    console.error("Có lỗi khi tải danh sách nhà hàng mở:", error);
    alert("Có lỗi xảy ra khi tải danh sách nhà hàng mở.");
  });

// Gửi yêu cầu GET đến API để lấy danh sách nhà hàng đóng (close)
fetch("/auth/close/readAll", {
  method: "GET", // Phương thức GET để lấy dữ liệu
  headers: {
    Authorization: `Bearer ${jwt}`, // Thêm token vào header
    "Content-Type": "application/json", // Đảm bảo dữ liệu trả về là JSON
  },
})
  .then((response) => {
    if (!response.ok) {
      // Xử lý nếu server trả về lỗi
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json(); // Chuyển dữ liệu trả về thành JSON
  })
  .then((data) => {
    // Xử lý dữ liệu nhận được từ server
    displayCloseRestaurantList(data); // Hiển thị danh sách nhà hàng đóng
  })
  .catch((error) => {
    console.error("Có lỗi khi tải danh sách nhà hàng đóng:", error);
    alert("Có lỗi xảy ra khi tải danh sách nhà hàng đóng.");
  });

// Hàm hiển thị danh sách nhà hàng mở
function displayOpenRestaurantList(restaurants) {
  const containerOpen = document.getElementById("openRestaurantsContainer"); // Phần tử DOM chứa danh sách nhà hàng mở

  let htmlContent = "<h3>Restaurant Opening Request</h3>"; // Tiêu đề "Restaurant Opening Request"

  // Kiểm tra nếu không có nhà hàng nào
  if (restaurants.length === 0) {
    htmlContent += "<p>No requests available.</p>";
    containerOpen.innerHTML = htmlContent;
    return;
  }

  // Xây dựng danh sách HTML từ dữ liệu nhận được
  htmlContent += "<ul>";
  restaurants.forEach((restaurant) => {
    htmlContent += `
      <div class="restaurant-container">
        <ul id="restaurantList">
          <li class="restaurant-item">
            <div class="restaurant-info">
              <h3><strong>Restaurant Name:</strong> ${restaurant.nameRestaurant}</h3>
              <p><strong>Owner:</strong> ${restaurant.ownerName}</p>
              <p><strong>Business registration number:</strong> ${restaurant.restNumber}</p>
              <p><strong>ID card number:</strong> ${restaurant.ownerIdNo}</p>
              <p><strong>Status:</strong> ${restaurant.status}</p>
              <p><strong>Reason:</strong> ${restaurant.reason}</p>
              <p><strong>Created At:</strong> ${restaurant.createdAt}</p>
              <p><strong>Processed At:</strong> ${restaurant.processedAt}</p>
            </div>
            <div class="restaurant-images">
              <div class="image-container">
                <p><strong>Restaurant Number:</strong></p>
                <img src="${restaurant.imageRestNumber}" alt="Restaurant Number Image" class="restaurant-img" />
              </div>
              <div class="image-container">
                <p><strong>Owner ID:</strong></p>
                <img src="${restaurant.imageId}" alt="Owner ID Image" class="restaurant-img" />
              </div>
            </div>
          </li>
        </ul>
      </div>
    `;
  });
  htmlContent += "</ul>";

  // Chèn danh sách vào trong phần tử container
  containerOpen.innerHTML = htmlContent;
}

// Hàm hiển thị danh sách nhà hàng đóng
function displayCloseRestaurantList(restaurants) {
  const containerClose = document.getElementById("closeRestaurantsContainer"); // Phần tử DOM chứa danh sách nhà hàng đóng

  // Thêm tiêu đề bằng tiếng Anh vào phần trên của danh sách nhà hàng đóng
  let htmlContent = "<h3>Restaurant Closing Request</h3>"; // Tiêu đề "Restaurant Closing Request"

  // Kiểm tra nếu không có nhà hàng nào
  if (restaurants.length === 0) {
    htmlContent += "<p>No requests available</p>";
    containerClose.innerHTML = htmlContent;
    return;
  }

  // Xây dựng danh sách HTML từ dữ liệu nhận được
  htmlContent += "<ul>";
  restaurants.forEach((restaurant) => {
    htmlContent += `
      <div class="restaurant-container">
        <ul id="restaurantList">
          <li class="restaurant-item">
            <div class="restaurant-info">
              <h3><strong>Restaurant Name:</strong> ${restaurant.nameRestaurant}</h3>
              <p><strong>Owner:</strong> ${restaurant.ownerName}</p>
              <p><strong>Business registration number:</strong> ${restaurant.restNumber}</p>
              <p><strong>ID card number:</strong> ${restaurant.ownerIdNo}</p>
              <p><strong>Reason:</strong> ${restaurant.reason}</p>
              <p><strong>Status:</strong> ${restaurant.status}</p>
              <p><strong>Created At:</strong> ${restaurant.createdAt}</p>
              <p><strong>Processed At:</strong> ${restaurant.processedAt}</p>
            </div>
          </li>
        </ul>
      </div>
    `;
  });
  htmlContent += "</ul>";

  // Chèn danh sách vào trong phần tử container
  containerClose.innerHTML = htmlContent;
}
