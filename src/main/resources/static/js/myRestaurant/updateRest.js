const jwt = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant(); // Lấy thông tin nhà hàng
  fetchCategories(); // Lấy danh sách các category để điền vào select
  document
    .getElementById("updateRestaurantForm")
    .addEventListener("submit", handleSubmit); // Lắng nghe sự kiện gửi form
});

// Lấy thông tin nhà hàng
function fetchRestaurant() {
  if (!jwt) {
    alert("Bạn phải đăng nhập để xem nhà hàng này.");
    return;
  }

  fetch("/myRestaurant", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${jwt}`,
    },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Lỗi HTTP! Mã trạng thái: ${response.status}`);
      }
      return response.json();
    })
    .then((restaurant) => {
      // Hiển thị thông tin nhà hàng
      document.getElementById("restImage").src = restaurant.restImage;
      document.getElementById("nameRestaurant").textContent =
        restaurant.nameRestaurant;
      document.getElementById("description").textContent =
        restaurant.description;
      document.getElementById("address").textContent = restaurant.address;
      document.getElementById("phone").textContent = restaurant.phone;
      document.getElementById("openTime").textContent = restaurant.openTime;
      document.getElementById("closeTime").textContent = restaurant.closeTime;
      document.getElementById("category").textContent = restaurant.category;
      document.getElementById("status").textContent = restaurant.status;
    })
    .catch((error) => {
      console.error("Lỗi khi lấy thông tin nhà hàng:", error);
    });
}

// Xử lý khi gửi form
function handleSubmit(e) {
  e.preventDefault(); // Ngừng hành động mặc định của form

  if (!jwt) {
    alert("Bạn phải đăng nhập để cập nhật thông tin nhà hàng.");
    return;
  }

  // Thu thập dữ liệu từ form
  const formData = {
    address: document.getElementById("address-input").value.trim(),
    phone: document.getElementById("phone-input").value.trim(),
    description: document.getElementById("description-input").value.trim(),
    category: document.getElementById("category-select").value.trim(),
    openTime: document.getElementById("openTime-input").value.trim(),
    closeTime: document.getElementById("closeTime-input").value.trim(),
  };
  console.log("Dữ liệu sẽ được gửi:", formData);

  // Gửi yêu cầu PUT để cập nhật thông tin nhà hàng
  fetch("/rest/updateInfo", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`, // Gửi JWT trong header Authorization
    },
    body: JSON.stringify(formData), // Gửi dữ liệu form dưới dạng JSON
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Lỗi HTTP! Mã trạng thái: ${response.status}`);
      }
      return response.json();
    })
    .then((data) => {
      alert("Cập nhật thông tin nhà hàng thành công!");
      console.log(data); // Log dữ liệu phản hồi từ server
      fetchRestaurant(); // Lấy lại thông tin nhà hàng sau khi cập nhật
    })
    .catch((error) => {
      console.error("Lỗi khi cập nhật thông tin nhà hàng:", error);
      alert("Cập nhật thông tin nhà hàng thất bại");
    });
}

// Lấy danh mục từ server
async function fetchCategories() {
  try {
    const response = await fetch("/category");
    if (!response.ok) {
      throw new Error(`Lỗi HTTP! Mã trạng thái: ${response.status}`);
    }
    const categories = await response.json();
    console.log("Danh mục:", categories);

    const categorySelect = document.getElementById("category-select");
    categories.forEach((category) => {
      const option = document.createElement("option");
      option.value = category;
      option.textContent = category.replace("_", " ");
      categorySelect.appendChild(option);
    });
  } catch (error) {
    console.error("Lỗi khi lấy danh sách category:", error);
  }
}

document.addEventListener("DOMContentLoaded", () => {
  // Lắng nghe sự kiện submit của form ảnh
  document
    .getElementById("profile-img-form")
    .addEventListener("submit", handleImageUpload);

  // Lắng nghe sự kiện khi người dùng chọn ảnh
  document
    .getElementById("image-input")
    .addEventListener("change", previewImage);
});

// Hàm xem trước ảnh
function previewImage(e) {
  const file = e.target.files[0];
  const reader = new FileReader();

  reader.onload = function (event) {
    document.getElementById("image-preview").src = event.target.result;
  };

  if (file) {
    reader.readAsDataURL(file); // Đọc ảnh dưới dạng URL để hiển thị xem trước
  }
}

// Hàm xử lý việc tải ảnh lên server
function handleImageUpload(e) {
  e.preventDefault(); // Ngừng hành động mặc định của form

  const jwt = localStorage.getItem("token"); // Lấy JWT từ localStorage
  const fileInput = document.getElementById("image-input");
  const file = fileInput.files[0];

  if (!file) {
    alert("Vui lòng chọn ảnh để tải lên.");
    return;
  }

  if (!jwt) {
    alert("Bạn phải đăng nhập để cập nhật ảnh.");
    return;
  }

  // Tạo một đối tượng FormData để gửi ảnh
  const formData = new FormData();
  formData.append("file", file); // Thêm file ảnh vào FormData

  // Gửi yêu cầu PUT với ảnh và JWT trong header
  fetch("/rest/updateImg", {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${jwt}`, // Gửi JWT trong header Authorization
    },
    body: formData, // Gửi ảnh dưới dạng FormData
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Lỗi HTTP! Mã trạng thái: ${response.status}`);
      }
      return response.json();
    })
    .then((data) => {
      alert("Cập nhật ảnh thành công!");
      console.log(data);
      // Cập nhật ảnh xem trước nếu server trả về URL ảnh mới
      document.getElementById("image-preview").src = data.newImageUrl;
      location.reload();
    })
    .catch((error) => {
      console.error("Lỗi khi cập nhật ảnh:", error);
      alert("Cập nhật ảnh thất bại.");
    });
  fetchRestaurant();
}
