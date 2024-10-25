// const jwt = localStorage.getItem("token") ?? null;
// if (jwt) {
//   // Kiểm tra token, nếu có thực hiện yêu cầu lấy thông tin người dùng
//   fetch("/register/login", {
//     headers: {
//       "Authorization": `Bearer ${jwt}`,
//     },
//   }).then(response => {
//     if (response.ok) {
//       location.href = "/view";  // Điều hướng đến trang view nếu token hợp lệ
//     }
//   }).catch(error => {
//     console.error("Error fetching user info:", error);
//   });
// }

const loginForm = document.getElementById("loginForm");
const usernameInput = document.getElementById("usernameInput");
const passwordInput = document.getElementById("passwordInput");

loginForm.addEventListener("submit", e => {
  e.preventDefault();  // Ngăn hành vi submit mặc định của form

  const username = usernameInput.value;
  const password = passwordInput.value;

  fetch("/login", {
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),  // Gửi dữ liệu đăng nhập
  })
    .then(response => {
      if (response.ok) return response.json();  // Nếu thành công, trả về JSON
      else throw Error("failed to login");  // Nếu không thành công, ném lỗi
    })
    .then(json => {
      localStorage.setItem("token", json.token);  // Lưu token vào localStorage
      location.href = "/view/index";  // Điều hướng đến trang view sau khi đăng nhập thành công
    })
    .catch(error => {
      alert(error.message);  // Hiển thị thông báo lỗi nếu có
    });
});

