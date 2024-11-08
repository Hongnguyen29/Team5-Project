// Lấy token từ localStorage
const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";
//

// Hàm thiết lập dữ liệu profile
const setProfileData = (profile) => {
  document.getElementById("username").innerText = profile.username;
  document.getElementById("email").innerHTML = "Email   :" + profile.email;
  document.getElementById("phone").innerHTML = "Phone  :" + profile.phone;
  document.getElementById("role").innerHTML = "Role   :" + profile.role;

  const avatarImg = document.querySelector(".avatar");
  avatarImg.src = profile.image ?? "/static/assets/img/user1.png";

  // Gọi hàm để tạo các liên kết điều hướng dựa trên vai trò
  createNavigationLinks(profile.role);
};

// Lấy thông tin profile từ API
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

// Hàm tạo các liên kết điều hướng dựa trên vai trò
function createNavigationLinks(role) {
  const navContainer = document.getElementById("nav-links");
  navContainer.innerHTML = ""; // Xóa nội dung cũ

  const links = {
    USER: [
      { text: "View Restaurants", href: "/view/home" },
      { text: "Open Restaurant", href: "/view/openRest" },
      { text: "My Reservations", href: "/view/reservation" },
      { text: "My Reviews", href: "/view/review" },
      { text: "My Requests", href: "/view/request" },
    ],
    OWNER: [
      { text: "Update My Restaurant", href: "/view/myRestaurant" },
      { text: "My Restaurant reviews", href: "/view/myRestaurant/review" },
      { text: "My Requests", href: "/view/request" },
      { text: "My Restaurant Menu", href: "/view/myRestaurant/menu" },
      {
        text: "My Restaurant Reservations",
        href: "/view/myRestaurant/reservation",
      },
      { text: "Restaurant closure request", href: "/view/myRestaurant/close" },
    ],
    ADMIN: [
      { text: "User List", href: "/view/admin/users" },
      { text: "Open Request", href: "/view/admin/open-confirm" },
      { text: "Close Request", href: "/view/admin/close-confirm" },
    ],
  };

  const roleLinks = links[role] || [];
  roleLinks.forEach((link) => {
    const a = document.createElement("a");
    a.href = link.href;
    a.innerText = link.text;
    navContainer.appendChild(a);
  });
}

// Gọi hàm fetchReservations khi tải trang
document.addEventListener("DOMContentLoaded", () => {
  fetchReservations();
});
