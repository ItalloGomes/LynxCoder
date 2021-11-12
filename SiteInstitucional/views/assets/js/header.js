(function () {
    window.addEventListener('scroll', function () {
        if (window.scrollY > 100) {
            header.classList.add('header-active');
            navBar.classList.add('navbar-active');
            navBar.classList.remove('navbar');
            logoNavbar.src = "assets/img/logo-type3.1.png";
        }
        else {
            header.classList.remove('header-active');
            navBar.classList.remove('navbar-active');
            navBar.classList.add('navbar');
            logoNavbar.src = "assets/img/logo-type2.png";
        }
    });
})();