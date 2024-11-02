// Hàm fetch để lấy danh sách category từ API và hiển thị vào dropdown
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
        option.value = category; // Điều chỉnh nếu category là đối tượng
        option.textContent = category.replace("_", " ").toLowerCase();
        categorySelect.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Lỗi khi lấy danh sách category:", error);
    });
}

// Hàm fetch để tìm kiếm nhà hàng từ API và hiển thị kết quả
function searchRestaurants() {
  const category = document.getElementById("category-select").value;
  const address = document.getElementById("address-input").value.trim();
  const name = document.getElementById("name-input").value.trim();

  // Tạo query string dựa trên các điều kiện tìm kiếm
  let query = "/search?";
  let hasConditions = false; // Biến kiểm tra xem có điều kiện nào hay không

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

  // Nếu không có điều kiện nào, gọi API để lấy tất cả nhà hàng
  if (!hasConditions) {
    query = "/search"; // Chỉ gọi API mà không có điều kiện
  } else {
    // Nếu có điều kiện, loại bỏ dấu "&" cuối cùng nếu có
    query = query.endsWith("&") ? query.slice(0, -1) : query;
  }

  // Gửi yêu cầu tìm kiếm đến API
  fetch(query)
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then((restaurants) => {
      console.log("Restaurants:", restaurants);

      // Hiển thị danh sách nhà hàng
      const resList = document.querySelector(".res-list");
      resList.innerHTML = ""; // Xóa kết quả cũ

      // Duyệt qua danh sách và tạo các phần tử HTML để hiển thị kết quả
      restaurants.forEach((restaurant) => {
        const resItem = document.createElement("div");
        resItem.classList.add("res-item");
        resItem.innerHTML = `
            <img src="${restaurant.restImage}" alt="" class="res-img">
            <div class="res-body">
              <h3>${restaurant.nameRestaurant}</h3>
              <p class="res-desc">${restaurant.description}</p>
              <p>Địa chỉ: ${restaurant.address}</p>
              <p>Điện thoại: ${restaurant.phone}</p>
              <p>Thời gian mở cửa: ${restaurant.openTime} - ${
          restaurant.closeTime
        }</p>
              <p>Loại: ${restaurant.category
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
      console.error("Lỗi khi tìm kiếm nhà hàng:", error);
    });
}

// Gọi hàm fetchCategories khi tải trang để tải danh sách category
document.addEventListener("DOMContentLoaded", () => {
  fetchCategories();
  document
    .getElementById("search-btn")
    .addEventListener("click", searchRestaurants);
  searchRestaurants();
});
