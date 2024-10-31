const jwt = localStorage.getItem("token") ?? null;
if (jwt) fetch("/auth/profile", {
    headers: {
        "Authorization": `Bearer ${jwt}`,
    },
}).then(response => {
    if (response.ok) location.href = "/view";
})



