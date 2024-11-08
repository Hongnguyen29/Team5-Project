// Kiểm tra token JWT khi tải trang
const jwt = localStorage.getItem("token");
if (!jwt) {
  location.href = "/view/login"; // Nếu không có token, chuyển hướng đến trang đăng nhập
}

document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant(); // Gọi hàm để lấy thông tin nhà hàng khi trang tải xong
});

let restId;

// Hàm để lấy thông tin nhà hàng
function fetchRestaurant() {
  const jwt = localStorage.getItem("token"); // Lấy JWT từ localStorage

  if (!jwt) {
    console.error("Token not found. You need to log in first.");
    return; // Nếu không có JWT, không thực hiện yêu cầu API
  }

  fetch(`/myRestaurant`, {
    method: "GET", // Đảm bảo phương thức là GET
    headers: {
      Authorization: `Bearer ${jwt}`, // Thêm JWT vào header
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`); // Nếu phản hồi không thành công, ném lỗi
      }
      return response.json(); // Chuyển phản hồi thành JSON
    })
    .then((restaurant) => {
      if (!restaurant) {
        throw new Error("Invalid restaurant data.");
      }
      restId = restaurant.id; // Lưu lại ID của nhà hàng
      fetchMenus(); // Sau khi lấy thông tin nhà hàng, gọi hàm lấy menu
    })
    .catch((error) => {
      console.error("Error fetching restaurant info:", error);
      alert("Error fetching restaurant info. Please try again.");
    });
}

// Hàm để lấy và hiển thị menu nhà hàng
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
      return response.json(); // Chuyển phản hồi thành JSON
    })
    .then((data) => {
      const menuContainer = document.getElementById("menuContainer"); // Lấy container menu

      // Kiểm tra nếu không có menu
      if (!data || data.length === 0) {
        menuContainer.innerHTML =
          "<p>No menu items available at the moment.</p>";
        return;
      }

      // Tạo HTML cho các món trong menu
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
                <button onclick="updateMenu(${item.id})">Update</button> <!-- Nút Update -->
                <button onclick="deleteMenu(${item.id})">Delete</button> <!-- Nút Delete -->
            </div>
        `
        )
        .join(""); // Dùng join để nối tất cả HTML của các món

      // Thêm vào container
      menuContainer.innerHTML = menuItems; // Hiển thị menu trong container
    })
    .catch((error) => {
      console.error("There was an issue fetching the menu:", error);
      const menuContainer = document.getElementById("menuContainer");
      menuContainer.innerHTML = "<p>Unable to load the menu list.</p>";
    });
}

// Hàm để chuyển hướng tới trang cập nhật menu
function updateMenu(menuId) {
  // Chuyển hướng tới trang cập nhật menu với menuId
  window.location.href = `/view/myRestaurant/menu/${menuId}`;
}

// Hàm để xóa món ăn
function deleteMenu(menuId) {
  // Hiển thị thông báo xác nhận trước khi xóa
  const confirmation = confirm(
    "Are you sure you want to delete this menu item?"
  );

  if (!confirmation) {
    return; // Nếu người dùng không xác nhận, không làm gì cả
  }

  const jwt = localStorage.getItem("token");
  if (!jwt) {
    console.error("Token not found. You need to log in first.");
    return;
  }

  // Gửi yêu cầu DELETE tới API
  fetch(`/rest/menu/${menuId}`, {
    method: "DELETE", // Phương thức DELETE
    headers: {
      Authorization: `Bearer ${jwt}`, // Thêm JWT vào header Authorization
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.text(); // Đọc phản hồi dưới dạng text (thông báo từ server)
      } else {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
    })
    .then((data) => {
      console.log("Server Response:", data); // In ra phản hồi từ server
      if (data === "Deleted successfully.") {
        alert("Menu item deleted successfully.");
        fetchMenus(); // Tải lại menu để hiển thị kết quả sau khi xóa
      } else {
        alert("Failed to delete menu item.");
      }
    })
    .catch((error) => {
      console.error("Error deleting menu item:", error);
      alert("An error occurred while deleting the menu item.");
    });
}
