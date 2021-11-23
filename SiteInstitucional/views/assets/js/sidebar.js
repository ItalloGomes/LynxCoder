let sidebar = document.querySelector(".sidebar");
<<<<<<< HEAD

let closeBtn = document.querySelector("#btn");

=======
// let filter = document.querySelector(".filter");

let closeBtn = document.querySelector("#btn");
// let closeBtnFilter = document.querySelector("#btnFilter");
>>>>>>> d0114ea7aec148b037ff0828973df0f38e9b6e8f

let searchBtn = document.querySelector(".bx-search");

closeBtn.addEventListener("click", ()=> {

    sidebar.classList.toggle("open");
    menuBtnChange();
    
});

<<<<<<< HEAD
=======
// closeBtnFilter.addEventListener("click", () => {

//     filter.classList.toggle("open");
//     filterBtnChange(); 

// });

>>>>>>> d0114ea7aec148b037ff0828973df0f38e9b6e8f
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
=======

// function filterBtnChange() {

//     if(filter.classList.contains("open")) {
//         closeBtnFilter.classList.replace("bxs-left-arrow", "bx-filter");
//     } else {
//         closeBtnFilter.classList.replace("bx-filter", "bxs-left-arrow");
//     }

// }
>>>>>>> d0114ea7aec148b037ff0828973df0f38e9b6e8f
