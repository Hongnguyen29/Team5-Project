const jwt = localStorage.getItem("token");
if (!jwt) location.href = "/view/login";

const nameRestInput = document.getElementById("name");
const businessNoInput = document.getElementById("business-No");
const ownerInput = document.getElementById("owner-Name");
const ownerIdInput = document.getElementById("owner-Id");
const businessImageInput = document.getElementById(
  "business-registration-image"
);
const ownerIdImageInput = document.getElementById("owner-id-image");

const openRest = document.getElementById("openRest-form");
openRest.addEventListener("submit", (e) => {
  e.preventDefault();

  const nameRestaurant = nameRestInput.value;
  const restNumber = businessNoInput.value;
  const ownerName = ownerInput.value;
  const ownerIdNo = ownerIdInput.value;

  const formData = new FormData();
  formData.append("nameRestaurant", nameRestaurant);
  formData.append("restNumber", restNumber);
  formData.append("ownerName", ownerName);
  formData.append("ownerIdNo", ownerIdNo);
  formData.append("imageRestNumber", businessImageInput.files[0]);
  formData.append("imageId", ownerIdImageInput.files[0]);

  fetch("/user/openRequest", {
    method: "post",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
      // Không cần đặt Content-Type cho FormData, nó sẽ tự động được thiết lập
    },
    body: formData,
  }).then((response) => {
    if (response.ok) location.href = "/view/request";
    else {
      return response.text().then((text) => {
        alert(`Error: ${text}`);
      });
    }
  });
});
