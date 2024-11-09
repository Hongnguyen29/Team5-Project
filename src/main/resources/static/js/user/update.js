const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";

// phần cập nhật phone và email
const phoneInput = document.getElementById("phone-input");
const emailInput = document.getElementById("email-input");

const setInfoData = (userInfo) => {
  phoneInput.value = userInfo.phone;
  emailInput.value = userInfo.email;
};
fetch("/auth/profile", {
  headers: {
    Authorization: `Bearer ${jwt}`,
  },
})
  .then((response) => {
    loggedIn = response.ok;
    if (!loggedIn) {
      localStorage.removeItem("token");
      location.href = "/view/login";
    }
    return response.json();
  })
  .then(setInfoData);
const updateInfo = document.getElementById("info-form");
updateInfo.addEventListener("submit", (e) => {
  e.preventDefault();
  const phone = phoneInput.value;
  const email = emailInput.value;

  const body = { phone, email };
  fetch("/auth/updateInfo", {
    method: "put",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  }).then((response) => {
    if (response.ok) location.reload();
    else if (response.status === 403) {
      location.href = "/view/login";
    } else {
      return response.text().then((text) => {
        alert(`Error: ${text}`);
      });
    }
  });
});

//phần cập nhật ảnh
const imageForm = document.getElementById("profile-img-form");
const imageInput = imageForm.querySelector("input[type='file']");
const imagePreview = document.getElementById("image-preview");
//tạo hàm để load ảnh
function loadUserProfileImage() {
  fetch("/auth/profile", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.json(); // Giả sử server trả về một JSON chứa URL của ảnh
      } else {
        throw new Error("Could not load profile image.");
      }
    })
    .then((data) => {
      // Giả sử data.url chứa URL của ảnh
      if (data.image) {
        imagePreview.src = data.image; // Cập nhật ảnh hiển thị
      }
    })
    .catch((error) => {
      console.error(error);
    });
}

// Gọi hàm để tải ảnh khi trang được tải
loadUserProfileImage();

imageInput.addEventListener("change", (e) => {
  const file = e.target.files[0];
  const reader = new FileReader();

  reader.onload = function (event) {
    imagePreview.src = event.target.result; // Cập nhật ảnh hiển thị
  };

  if (file) {
    reader.readAsDataURL(file); // Đọc ảnh dưới dạng URL
  }
});

imageForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const formData = new FormData();
  formData.append("file", imageInput.files[0]);

  fetch("/auth/updateImage", {
    method: "put",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
    body: formData,
  }).then((response) => {
    if (response.ok) location.reload();
    else if (response.status === 403) location.href = "/view/login";
    else {
      return response.text().then((text) => {
        alert(`Error: ${text}`);
      });
    }
  });
});

// phần cập nhật password

const passOldInput = document.getElementById("password-old");
const passNewInput = document.getElementById("password-new");
const passConfirmInput = document.getElementById("password-confirm");

const updatePass = document.getElementById("password-form");
updatePass.addEventListener("submit", (e) => {
  e.preventDefault();
  const oldPassword = passOldInput.value;
  const password = passNewInput.value;
  const passwordCheck = passConfirmInput.value;

  const body = { oldPassword, password, passwordCheck };
  fetch("/auth/password", {
    method: "put",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  }).then((response) => {
    if (response.ok) location.href = "/view/login";
    else {
      return response.text().then((text) => {
        alert(`Error: ${text}`);
      });
    }
  });
});
