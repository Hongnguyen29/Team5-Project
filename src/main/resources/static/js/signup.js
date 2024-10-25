const username = document.getElementById("username");
const password = document.getElementById("password");
const checkPw = document.getElementById("checkpw");
const email = document.getElementById("email");
const phone = document.getElementById("phone");

const formSignup = document.querySelector(".signup-form"); // Sửa đổi để lấy đúng thẻ

formSignup.addEventListener("submit", (e) => {
  e.preventDefault();

  if (password.value !== checkPw.value) {
    alert("Your passwords do not match!!!");
    return;
  }

  const dataInput = {
    username: username.value,
    password: password.value,
    passwordCheck: checkPw.value,
    email: email.value,
    phone: phone.value,
  };

  fetch("/register", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(dataInput),
  }).then((response) => {
    if (response.ok) {
      location.href = "/view/login"; //Link front end
      return response.json();
    } else {
      return response.text().then((text) => {
        alert(text);
      });
    }
  });
});

