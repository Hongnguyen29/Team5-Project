document.addEventListener("DOMContentLoaded", () => {
  fetchRestaurant();
});
function getRestaurantId() {
  const url = window.location.href; // Lấy URL hiện tại
  const parts = url.split("/"); // Tách URL theo dấu "/"
  return parts[parts.length - 1]; // Lấy phần cuối cùng là ID
}
function fetchRestaurant() {
  const id = getRestaurantId();
  fetch(`/restaurant/${id}`) // Thay đổi URL này thành URL API thực tế
    .then((response) => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
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
