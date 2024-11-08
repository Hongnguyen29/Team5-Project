// Kiểm tra xem người dùng đã đăng nhập chưa, nếu chưa thì chuyển hướng đến trang đăng nhập
const jwt = localStorage.getItem("token");
if (!jwt) {
  location.href = "/view/login"; // Chuyển hướng đến trang login nếu không có JWT
}

// Hàm xử lý khi người dùng submit form
async function handleFormSubmit(event) {
  event.preventDefault(); // Ngừng hành động mặc định của form

  const formData = new FormData(document.getElementById("foodForm")); // Lấy dữ liệu từ form

  try {
    // Gửi dữ liệu lên server qua API PUT
    const response = await fetch("/rest/menu", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${jwt}`, // Thêm JWT vào header
      },
      body: formData,
    });

    // Kiểm tra nếu phản hồi từ server là OK (200)
    if (response.ok) {
      alert("Data submitted successfully!");
      window.location.href = "/view/myRestaurant/menu"; // Chuyển hướng sau khi gửi thành công
    } else {
      alert("Failed to submit data.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("An error occurred while submitting the data.");
  }
}

// Lắng nghe sự kiện submit của form
document
  .getElementById("foodForm")
  .addEventListener("submit", handleFormSubmit);
