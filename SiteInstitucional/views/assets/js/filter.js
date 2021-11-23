let filter = document.querySelector(".filter");
let closeBtnFilter = document.querySelector("#btnFilter");

closeBtnFilter.addEventListener("click", () => {

    filter.classList.toggle("open");
    filterBtnChange(); 

});

function filterBtnChange() {

    if(filter.classList.contains("open")) {
        closeBtnFilter.classList.replace("bxs-left-arrow", "bx-filter");
    } else {
        closeBtnFilter.classList.replace("bx-filter", "bxs-left-arrow");
    }

}