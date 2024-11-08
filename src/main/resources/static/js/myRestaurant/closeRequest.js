// Kiểm tra nếu người dùng đã đăng nhập
const jwt = localStorage.getItem("token");
if (!jwt) {
  location.href = "/view/login"; // Nếu không có token, chuyển hướng đến trang đăng nhập
}

// Tham chiếu đến form và checkbox đồng ý
const closureForm = document.getElementById("closure-form");
const agreeCheckbox = document.getElementById("agree");
const closureReason = document.getElementById("closure-reason");

// Thêm sự kiện gửi form
closureForm.addEventListener("submit", function (event) {
  event.preventDefault(); // Ngừng hành động mặc định của form

  // Kiểm tra xem người dùng có đồng ý với các điều khoản không
  if (!agreeCheckbox.checked) {
    alert("You must agree to the terms and conditions to proceed.");
    return; // Dừng nếu không đồng ý điều khoản
  }

  // Lấy lý do ngừng kinh doanh từ textarea
  const reason = closureReason.value;

  // Kiểm tra xem người dùng đã nhập lý do chưa
  if (reason === "") {
    alert("Please provide a reason for closing your business.");
    return; // Dừng nếu không nhập lý do
  }

  // Tạo FormData object
  const formData = new FormData();
  formData.append("reason", reason); // Thêm lý do vào form-data

  // Gửi yêu cầu POST với FormData
  fetch("/rest/close", {
    method: "POST",
    headers: {
      Authorization: `Bearer ${jwt}`, // Thêm JWT vào header
    },
    body: formData, // Gửi dữ liệu dưới dạng form-data
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((text) => {
          alert(text);
        });
      }
      return response.json();
    })
    .then((data) => {
      alert("Your business closure request has been submitted successfully.");
      console.log(data);
    })
    .catch((error) => {
      console.error("Error submitting closure request:", error);
      alert(
        "There was an error submitting your closure request. Please try again later."
      );
    });
});
