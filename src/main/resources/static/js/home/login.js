const loginForm = document.getElementById("login-form");
const usernameInput = document.getElementById("name");
const passwordInput = document.getElementById("password");
loginForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const username = usernameInput.value;
  const password = passwordInput.value;
  fetch("/login", {
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  })
    .then((response) => {
      if (response.ok) return response.json();
      else throw Error("failed to login");
    })
    .then((json) => {
      localStorage.setItem("token", json.token);
      location.href = "/view/home";
    })
    .catch((error) => alert(error.message));
});
