let sidebar = document.querySelector(".sidebar");
// let filter = document.querySelector(".filter");

let closeBtn = document.querySelector("#btn");
// let closeBtnFilter = document.querySelector("#btnFilter");

let searchBtn = document.querySelector(".bx-search");

closeBtn.addEventListener("click", ()=> {

    sidebar.classList.toggle("open");
    menuBtnChange();
    
});

// closeBtnFilter.addEventListener("click", () => {

//     filter.classList.toggle("open");
//     filterBtnChange(); 

// });

searchBtn.addEventListener("click", ()=> { 

    sidebar.classList.toggle("open");
    menuBtnChange();

});

function menuBtnChange() {

    if(sidebar.classList.contains("open")) {
        closeBtn.classList.replace("bx-menu", "bx-menu-alt-right");
    } else {
        closeBtn.classList.replace("bx-menu-alt-right", "bx-menu");
    }

}

// function filterBtnChange() {

//     if(filter.classList.contains("open")) {
//         closeBtnFilter.classList.replace("bxs-left-arrow", "bx-filter");
//     } else {
//         closeBtnFilter.classList.replace("bx-filter", "bxs-left-arrow");
//     }

// }