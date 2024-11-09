// Kiểm tra xem người dùng đã đăng nhập chưa, nếu chưa thì chuyển hướng đến trang đăng nhập
const jwt = localStorage.getItem("token");
if (!jwt) {
  location.href = "/view/login"; // Chuyển hướng đến trang login nếu không có JWT
}

const menuId = window.location.pathname.split("/").pop(); // Lấy menuId từ URL

// Hàm để lấy thông tin menu từ API
async function fetchMenuData() {
  try {
    const response = await fetch(`/restaurant/menu/${menuId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${jwt}`, // Thêm JWT vào header
      },
    });

    if (response.ok) {
      const menuData = await response.json();
      console.log(menuData); // Kiểm tra dữ liệu trả về trong console

      // Hiển thị dữ liệu vào form
      document.getElementById("nameFood").value = menuData.nameFood;
      document.getElementById("price").value = menuData.price;
      document.getElementById("status").value = menuData.status;

      // Nếu có file, bạn có thể xử lý file ở đây
      // Ví dụ: nếu menuData có thuộc tính imageURL, bạn có thể tạo một thẻ <img> để hiển thị hình ảnh
      // document.getElementById('file').value = menuData.imageURL;
    } else {
      alert("Failed to fetch menu data.");
    }
  } catch (error) {
    console.error("Error fetching menu data:", error);
    alert("An error occurred while fetching menu data.");
  }
}

// Lấy thông tin menu khi trang được tải
fetchMenuData();

// Hàm xử lý khi người dùng submit form
async function handleFormSubmit(event) {
  event.preventDefault(); // Ngừng hành động mặc định của form

  const formData = new FormData(document.getElementById("foodForm")); // Lấy dữ liệu từ form

  try {
    // Gửi dữ liệu lên server qua API PUT
    const response = await fetch(`/rest/menu/${menuId}`, {
      method: "PUT",
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
