function accessControl(user){

    if (user.is_gestor){

        document.getElementById('navDashboard').style.display = 'none';
        document.getElementById('navRelatorios').style.display = 'none';
        
    } else {
        
        document.getElementById('navEquipe').style.display = 'none';
        document.getElementById('navSprint').style.display = 'none';
        document.getElementById('navConfig').style.display = 'none';

    }


}