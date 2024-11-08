const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";

document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant(); // Gọi hàm để lấy thông tin nhà hàng khi trang được tải
});

let restId;

// Hàm lấy thông tin nhà hàng
function fetchRestaurant() {
  const jwt = localStorage.getItem("token"); // Lấy JWT từ localStorage

  if (!jwt) {
    console.error("Không tìm thấy token. Bạn cần đăng nhập trước.");
    return; // Nếu không có JWT, không gửi yêu cầu
  }

  fetch(`/myRestaurant`, {
    method: "GET", // Đảm bảo phương thức là GET
    headers: {
      Authorization: `Bearer ${jwt}`, // Thêm JWT vào header Authorization
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`); // Nếu response không ok, ném lỗi
      }
      return response.json(); // Chuyển phản hồi thành JSON
    })
    .then((restaurant) => {
      // Kiểm tra nếu dữ liệu trả về hợp lệ
      if (!restaurant) {
        throw new Error("Dữ liệu nhà hàng không hợp lệ.");
      }
      restId = restaurant.id; // Lưu restId từ dữ liệu nhà hàng

      // Hiển thị thông tin nhà hàng
      document.getElementById("restImage").src =
        restaurant.restImage || "default-image.jpg"; // Nếu không có ảnh thì sử dụng ảnh mặc định
      document.getElementById("nameRestaurant").textContent =
        restaurant.nameRestaurant || "N/A";
      // document.getElementById("description").textContent =
      //   restaurant.description || "No description available";
      document.getElementById("address").textContent =
        restaurant.address || "No address available";
      document.getElementById("phone").textContent =
        restaurant.phone || "No phone number available";
      document.getElementById("openTime").textContent =
        restaurant.openTime || "N/A";
      document.getElementById("closeTime").textContent =
        restaurant.closeTime || "N/A";
      document.getElementById("category").textContent =
        restaurant.category || "N/A";
      // document.getElementById("status").textContent =
      //   restaurant.status || "N/A";

      // Sau khi đã lấy thông tin nhà hàng thành công, gọi hàm fetchReviews
      fetchReviews();
    })
    .catch((error) => {
      console.error("Lỗi khi lấy thông tin nhà hàng:", error);
      alert("Lỗi khi lấy thông tin nhà hàng. Vui lòng thử lại.");
    });
}

// Hàm lấy và hiển thị đánh giá của nhà hàng
function fetchReviews() {
  if (!restId) {
    console.error("Không có ID nhà hàng, không thể lấy đánh giá.");
    return;
  }

  fetch(`/restaurant/${restId}/review`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      const reviewContainer = document.getElementById("reviewContainer"); // Thêm phần tử container đánh giá nếu chưa có
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
}

// Hàm hiển thị sao
function renderStars(star) {
  let starsHTML = "";
  for (let i = 1; i <= 5; i++) {
    starsHTML += `<span class="star">${i <= star ? "★" : "☆"}</span>`;
  }
  return starsHTML; // Trả về HTML cho các sao
}
