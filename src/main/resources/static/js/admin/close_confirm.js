// Kiểm tra JWT token trong localStorage, nếu không tìm thấy thì chuyển hướng đến trang đăng nhập
const jwt = localStorage.getItem("token");
if (!jwt) {
  location.href = "/view/login"; // Nếu không có token, chuyển hướng đến trang login
}

// Hàm để tải danh sách yêu cầu từ API
async function fetchRequests() {
  try {
    const token = localStorage.getItem("token"); // Lấy token từ localStorage
    const statusFilter = document.querySelector("#statusSelect").value; // Lấy giá trị từ dropdown trạng thái

    // Tạo URL với các tham số tìm kiếm, nếu có
    let url = "/admin/opens/ReadAll";
    if (statusFilter) {
      url += `?status=${statusFilter}`; // Thêm tham số status vào URL nếu có
    }

    const response = await fetch(url, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error("Unable to fetch data from the API");
    }

    const data = await response.json();
    console.log("Data received from API:", data); // Hiển thị dữ liệu nhận được từ API

    // Kiểm tra xem dữ liệu trả về có phải là mảng không
    if (Array.isArray(data)) {
      displayRequests(data); // Hiển thị danh sách yêu cầu
    } else {
      console.error("The returned data is not an array or is invalid.");
    }
  } catch (error) {
    console.error("Error while fetching API:", error);
  }
}

// Hàm để hiển thị danh sách yêu cầu lên bảng
function displayRequests(requests) {
  const tableBody = document.querySelector("#requestsTable tbody");
  tableBody.innerHTML = ""; // Xóa nội dung cũ trước khi thêm mới

  // Lặp qua từng yêu cầu và tạo một dòng trong bảng
  requests.forEach((request) => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${request.id}</td>
      <td>${request.nameRestaurant}</td>
      <td>${request.restNumber}</td>
      <td>${request.ownerName}</td>
      <td>${request.ownerIdNo}</td>
      <td>${request.reason}</td>
      <td>${request.status}</td>
      <td>${new Date(request.createdAt).toLocaleString()}</td>
      <td>${new Date(request.processedAt).toLocaleString()}</td>
      <td>
        ${
          request.status === "PENDING"
            ? `
                <button class="btn-approve" onclick="confirmRequest(${request.id})">Accept</button>
            `
            : ""
        }
      </td>
    `;

    // Thêm dòng mới vào bảng
    tableBody.appendChild(row);
  });
}

// Hàm để xử lý yêu cầu (chấp nhận hoặc từ chối)
async function confirmRequest(id) {
  fetch(`/admin/close/confirm/${id}`, {
    method: "PUT", // Dùng PUT để chấp nhận hoặc từ chối yêu cầu
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`,
    },
  })
    .then((response) => {
      if (!response.ok) {
        return response.text().then((text) => {
          alert(text); // Hiển thị lỗi nếu có
        });
      }
      return response.json();
    })
    .then(() => {
      alert(`Successfully confirmed`); // Thông báo thành công
      fetchRequests(); // Tải lại danh sách yêu cầu
    })
    .catch((error) => {
      console.error("An error occurred while processing the request:", error);
      alert(
        "An error occurred while processing the request. Please try again."
      );
    });
}

// Hàm gọi fetchRequests khi trang được tải
window.onload = fetchRequests;

// Sự kiện khi thay đổi giá trị trong dropdown "Select Status"
document
  .querySelector("#statusSelect")
  .addEventListener("change", fetchRequests);
