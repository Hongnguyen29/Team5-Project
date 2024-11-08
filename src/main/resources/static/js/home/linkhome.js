const jwt = localStorage.getItem("token");
const nav = document.getElementById("nav");

if (jwt) {
  // Fetch thông tin profile từ API để lấy username và ảnh đại diện
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
    .then((profile) => {
      // Tạo phần hiển thị username và ảnh đại diện
      const navProfile = document.createElement("div");
      navProfile.id = "nav-profile";
      navProfile.style.display = "flex";
      navProfile.style.alignItems = "center"; // Căn giữa ảnh và tên

      // Tạo ảnh đại diện
      const avatarImg = document.createElement("img");
      avatarImg.src = profile.image || "/static/assets/img/user1.png"; // Dùng ảnh mặc định nếu không có ảnh
      avatarImg.alt = "Avatar";
      avatarImg.classList.add("avatar-img"); // Thêm class nếu cần thêm style từ CSS
      avatarImg.style.width = "30px"; // Kích thước ảnh đại diện
      avatarImg.style.height = "30px"; // Kích thước ảnh đại diện
      avatarImg.style.borderRadius = "50%"; // Làm cho ảnh thành hình tròn
      avatarImg.style.marginRight = "10px"; // Khoảng cách giữa ảnh và tên

      // Tạo tên người dùng
      const usernameText = document.createElement("span");
      usernameText.textContent = profile.username || "Username"; // Dùng giá trị mặc định nếu không có username
      usernameText.style.fontSize = "14px"; // Kích thước chữ của tên người dùng
      usernameText.style.color = "#333"; // Màu chữ của tên người dùng

      // Append ảnh và tên vào nav-profile
      navProfile.appendChild(avatarImg);
      navProfile.appendChild(usernameText);

      // Append phần avatar và username vào nav (đặt ở góc phải)
      nav.appendChild(navProfile);
    })
    .catch((error) => {
      console.error("Lỗi khi lấy thông tin profile:", error);
    });

  // Tạo các liên kết (Account Management và Home)
  const links = [{ text: "Account Management", href: "/view/profile" }];
  links.forEach((link) => {
    const li = document.createElement("li");
    const a = document.createElement("a");
    a.href = link.href;
    a.textContent = link.text;
    li.appendChild(a);
    nav.appendChild(li);
  });
  // Tạo liên kết Logout
  const logoutLi = document.createElement("li");
  const logoutLink = document.createElement("a");
  logoutLink.textContent = "Logout";
  logoutLink.href = "#"; // Đặt href là "#" để tránh chuyển hướng ngay lập tức

  logoutLink.addEventListener("click", (e) => {
    e.preventDefault(); // Ngăn chặn hành động mặc định
    localStorage.removeItem("token"); // Xóa token
    location.href = "/view/login"; // Chuyển hướng về trang đăng nhập
  });

  logoutLi.appendChild(logoutLink);
  nav.appendChild(logoutLi);
} else {
  const links = [
    { text: "Login", href: "/view/login" },
    { text: "Signup", href: "/view/signup" },
  ];

  links.forEach((link) => {
    const li = document.createElement("li");
    const a = document.createElement("a");
    a.href = link.href;
    a.textContent = link.text;
    li.appendChild(a);
    nav.appendChild(li);
  });
}
