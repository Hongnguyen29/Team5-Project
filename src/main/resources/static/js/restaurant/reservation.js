// Lấy token từ localStorage
const jwt = localStorage.getItem("token");

// Kiểm tra nếu không có token thì chuyển hướng về trang login
if (!jwt) {
  location.href = "/view/login";
}

// Lắng nghe sự kiện khi trang đã được tải xong
document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant();
});

// Lấy ID của nhà hàng từ URL
function getRestaurantId() {
  const url = window.location.href; // Lấy URL hiện tại
  const parts = url.split("/"); // Tách URL theo dấu "/"
  return parts[parts.length - 2]; // Lấy phần cuối cùng là ID của nhà hàng
}

// Lấy thông tin nhà hàng và hiển thị trên giao diện
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
      // Hiển thị thông tin nhà hàng lên giao diện
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

// Lấy thông tin sao đánh giá của nhà hàng
const id = getRestaurantId();

fetch(`/restaurant/${id}/star`)
  .then((response) => {
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json();
  })
  .then((data) => {
    const starCount = parseFloat(data); // Chuyển giá trị thành số thực
    document.getElementById("star-num").textContent = starCount.toFixed(1); // Hiển thị số sao với 1 chữ số thập phân
    displayStars(starCount); // Hiển thị sao đánh giá
  })
  .catch((error) => {
    console.error("Lỗi khi lấy thông tin sao:", error);
  });

// Hiển thị sao đánh giá
function displayStars(starCount) {
  const starContainer = document.getElementById("star");
  starContainer.innerHTML = ""; // Xóa nội dung cũ

  for (let i = 1; i <= 5; i++) {
    const starElement = document.createElement("span");
    starElement.className = "star"; // Thêm lớp để định dạng sao

    starElement.innerHTML = i <= Math.floor(starCount) ? "★" : "☆"; // Hiển thị sao đầy hoặc rỗng

    starContainer.appendChild(starElement);
  }
}

// Phần xử lý đặt bàn - Khi người dùng submit form
const nameCustomInput = document.getElementById("nameCustom");
const dateInput = document.getElementById("date");
const timeInput = document.getElementById("time");
const peopleNumberInput = document.getElementById("peopleNumber");
const noteInput = document.getElementById("note");

const setReservation = document.getElementById("reservationForm");

setReservation.addEventListener("submit", (e) => {
  e.preventDefault(); // Ngăn hành động submit mặc định của form

  // Tạo đối tượng dữ liệu từ form
  const dataInput = {
    nameCustom: nameCustomInput.value,
    date: dateInput.value,
    time: timeInput.value,
    peopleNumber: peopleNumberInput.value,
    note: noteInput.value,
  };

  const restId = getRestaurantId(); // Lấy ID nhà hàng từ URL

  // Gửi dữ liệu lên server bằng fetch
  fetch(`/restaurant/${restId}/reservation`, {
    method: "POST", // Phương thức POST
    headers: {
      "Content-Type": "application/json", // Gửi dữ liệu dưới dạng JSON
      Authorization: `Bearer ${jwt}`, // Gửi token trong header Authorization
    },
    body: JSON.stringify(dataInput), // Chuyển đối tượng thành JSON
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to submit reservation");
      }
      return response.json(); // Lấy dữ liệu trả về từ server
    })
    .then((data) => {
      alert("Reservation submitted successfully!");

      window.location.href = "/view/home";
    })
    .catch((error) => {
      // Xử lý lỗi nếu có
      console.error("Error:", error);
      alert("Error: " + error.message); // Hiển thị thông báo lỗi
    });
});
