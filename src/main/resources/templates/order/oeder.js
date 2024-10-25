document.getElementById("submit-btn").addEventListener("click", function(event) {
    event.preventDefault();  // Ngăn việc submit form tự động

    // Lấy giá trị của các trường
    const name = document.getElementById("name").value;
    const date = document.getElementById("date").value;
    const time = document.getElementById("time").value;
    const guests = document.getElementById("guests").value;
    const note = document.getElementById("note").value;

    // Kiểm tra nếu các trường bắt buộc bị bỏ trống
    if (!name || !date || !time || !guests) {
        alert("Vui lòng điền đầy đủ thông tin!");
        return;
    }

    // Xử lý dữ liệu (có thể gửi đến server ở đây)
    alert(`Đặt bàn thành công cho ${guests} khách vào ${date} lúc ${time}.`);
});
