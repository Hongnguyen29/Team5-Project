
const reservationButtons = document.querySelectorAll(".reservation");
        reservationButtons.forEach((button, index) => {
            button.addEventListener("click", () => {
                const restId = index + 1; // Giả sử ID của nhà hàng là từ 1 đến 10
                window.location.href = `/restaurant/${restId}/menu`;
            });
        });

  


