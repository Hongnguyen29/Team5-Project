// Lấy token JWT từ localStorage
const jwt = localStorage.getItem("token");

// Nếu không có token, điều hướng người dùng đến trang đăng nhập
if (!jwt) {
  location.href = "/view/login"; // Thay đổi URL đến trang login nếu không có token
}

// Lấy Review ID từ URL (nếu cần)
function getReservationId() {
  const url = window.location.href; // Lấy URL hiện tại
  const parts = url.split("/"); // Tách URL theo dấu "/"
  return parts[parts.length - 1]; // Lấy phần cuối cùng là ID
}

// Lắng nghe sự kiện khi form được submit
document
  .getElementById("reviewForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Ngừng hành động mặc định của form (submit)

    // Lấy các giá trị từ form
    const star = document.getElementById("star").value; // Lấy giá trị sao
    const content = document.getElementById("content").value; // Lấy nội dung đánh giá
    const image = document.getElementById("image").files[0]; // Lấy ảnh đã chọn

    // Tạo FormData để gửi dữ liệu bao gồm ảnh
    const formData = new FormData();
    formData.append("star", star);
    formData.append("content", content);
    if (image) {
      formData.append("image", image);
    }

    const reservationId = getReservationId();
    console.log(reservationId),
      // Gọi API POST để tạo review
      fetch(`/user/reservation/${reservationId}`, {
        // Chú ý, đây là POST API để tạo mới review
        method: "POST",
        headers: {
          Authorization: `Bearer ${jwt}`, // Thêm token vào header
        },
        body: formData, // Dữ liệu gửi đi là FormData
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.json(); // Chuyển dữ liệu trả về thành JSON
        })
        .then((data) => {
          alert("Đánh giá đã được tạo thành công.");
          window.location.href = "/view/review"; // Điều hướng về trang danh sách đánh giá sau khi tạo thành công
        })
        .catch((error) => {
          console.error("Có lỗi khi tạo đánh giá:", error);
          alert("Có lỗi xảy ra khi tạo đánh giá.");
        });
  });