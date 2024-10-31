
const linkSignup = document.getElementById("link-signup");
const linkLogin = document.getElementById("link-login");
linkSignup.addEventListener("click", (e) => {
    e.preventDefault();

    const targetUrl = "./view/register"
    location.href = targetUrl;
});

linkLogin.addEventListener("click", (e) => {
    e.preventDefault();
    const targetUrl = "./view/login"
    location.href = targetUrl;
})



