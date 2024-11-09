const loginForm = document.getElementById("login-form");
const usernameInput = document.getElementById("name");
const passwordInput = document.getElementById("password");

loginForm.addEventListener("submit", (e) => {
  e.preventDefault(); // Prevent form submission

  // Get the values from the input fields
  const username = usernameInput.value;
  const password = passwordInput.value;

  // Make a POST request to the login API
  fetch("/login", {
    method: "POST", // POST method for login
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ username, password }),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Login failed, please check your credentials.");
      }
      return response.json(); // Parse JSON response
    })
    .then((data) => {
      // Assuming the response contains 'token' and 'role'
      console.log("Login successful:", data);

      const token = data.token;
      const role = data.role;

      // Store the token in localStorage
      localStorage.setItem("token", data.token); // You can use sessionStorage as an alternative

      // Based on the role, redirect the user to the appropriate page
      if (data.role === "ROLE_USER") {
        window.location.href = "/view/profile"; // Redirect to profile page for ROLE_USER
      } else if (data.role === "ROLE_OWNER") {
        window.location.href = "/view/home"; // Redirect to home page for ROLE_OWNER
      } else {
        console.log("Unknown role:", role);
      }
    })
    .catch((error) => {
      // Handle any errors that occur during the login request
      console.error("Error during login:", error);
      alert("Login failed. Please try again.");
    });
});
