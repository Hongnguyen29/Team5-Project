const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";
// JavaScript để bật/tắt menu
if (jwt) {
  function toggleMenu() {
    const menu = document.getElementById("menu");
    menu.classList.toggle("active");
  }
}

const profile = document.getElementById("profile-form");

const setProfileData = (profile) => {
  document.getElementById("username").innerText =
    "Username :" + profile.username;
  document.getElementById("email").innerHTML = "Email   :" + profile.email;
  document.getElementById("phone").innerHTML = "Phone  :" + profile.phone;
  document.getElementById("role").innerHTML = "Role   :" + profile.role;

  const avatarImg = document.querySelector(".avatar"); // Lấy phần tử <img> có class "avatar"

  // Cập nhật thuộc tính src của thẻ <img>
  avatarImg.src = profile.image;
};

//email, phone, image, role
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
  .then(setProfileData);
// Hàm fetch API và hiển thị danh sách đặt chỗ
async function fetchReservations() {
  try {
    // Gửi yêu cầu lấy danh sách đặt chỗ từ API
    const response = await fetch("/user/reservation", {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
    const reservations = await response.json();

    // Lấy phần tử HTML để hiển thị danh sách đặt chỗ
    const reservationList = document.getElementById("reservation");
    reservationList.innerHTML = ""; // Xóa nội dung cũ (nếu có)

    // Duyệt qua danh sách và tạo các phần tử HTML
    reservations.forEach((reservation) => {
      const listItem = document.createElement("div");
      listItem.classList.add("reservation-item");
      listItem.innerHTML = `
        <h3>${reservation.nameCustom} - ${reservation.restaurantName}</h3>
        <p>Địa chỉ: ${reservation.restaurantAddress}</p>
        <p>Thời gian: ${new Date(reservation.time).toLocaleString()}</p>
        <p>Số người: ${reservation.peopleNumber}</p>
        <p>Ghi chú: ${reservation.note || "Không có ghi chú"}</p>
        <p>Trạng thái: ${reservation.status}</p>
      `;

      // Thêm phần tử vào danh sách
      reservationList.appendChild(listItem);
    });

    // Hiển thị thông báo nếu danh sách rỗng
    if (reservations.length === 0) {
      reservationList.innerHTML = "<p>Không có đặt chỗ nào.</p>";
    }
  } catch (error) {
    console.error("Lỗi khi lấy danh sách đặt chỗ:", error);
    // Hiển thị thông báo lỗi cho người dùng
    document.getElementById("reservation-list").innerHTML =
      "<p>Không thể tải danh sách đặt chỗ. Vui lòng thử lại sau.</p>";
  }
}

// Gọi hàm fetchReservations khi tải trang
document.addEventListener("DOMContentLoaded", fetchReservations);
