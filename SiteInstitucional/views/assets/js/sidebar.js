let sidebar = document.querySelector(".sidebar");
<<<<<<< HEAD
// let filter = document.querySelector(".filter");

let closeBtn = document.querySelector("#btn");
// let closeBtnFilter = document.querySelector("#btnFilter");

=======
let closeBtn = document.querySelector("#btn");
>>>>>>> 429a56735d50c770ce0cd477c555ca7fd47c9173
let searchBtn = document.querySelector(".bx-search");

closeBtn.addEventListener("click", ()=> {

    sidebar.classList.toggle("open");
    menuBtnChange();
    
});

<<<<<<< HEAD
// closeBtnFilter.addEventListener("click", () => {

//     filter.classList.toggle("open");
//     filterBtnChange(); 

// });

=======
>>>>>>> 429a56735d50c770ce0cd477c555ca7fd47c9173
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
<<<<<<< HEAD

// function filterBtnChange() {

//     if(filter.classList.contains("open")) {
//         closeBtnFilter.classList.replace("bxs-left-arrow", "bx-filter");
//     } else {
//         closeBtnFilter.classList.replace("bx-filter", "bxs-left-arrow");
//     }

// }
=======
>>>>>>> 429a56735d50c770ce0cd477c555ca7fd47c9173
