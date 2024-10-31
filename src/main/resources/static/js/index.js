
 const reservationButtons = document.querySelectorAll(".reservation");
         reservationButtons.forEach((button, index) => {
             button.addEventListener("click", () => {
                 const restId = index + 1; // Giả sử ID của nhà hàng là từ 1 đến 10
                 window.location.href = `/view/rest/menu/{restId}`;
             });
         });
const shopsContainer = document.getElementById("shops-container");
const setData = (shopsPage) => {
  const { content, page } = shopsPage;
  if (content.length === 0)
    pageInput.disabled = true;
  else pageInput.max = page.totalPages;

  if (content.length === 0) {
    const messageHead = document.createElement("h3");
    messageHead.innerText = "No Shops";
    shopsContainer.append(messageHead);
    return;
  }
}

fetch("/rest/menu", {
    method: 'POST',
  headers: {
    "Authorization": 'Bearer ' + localStorage.getItem("token"),
  },
}).then(response => {
    console.log(response)
  if (!response.ok) {
    // localStorage.removeItem("token");
    // location.href = "/view/login";
  }
  return response.json();
}).then(setData);





