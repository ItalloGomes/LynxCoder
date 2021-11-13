const { Router } = require("express");
const router = Router();
const db = require('../config/connectDatabase');

router.get('/', (req, res) => {
    res.render('login', { title: 'Express' });
});

router.post('/logar', (req, res) => {

    console.log("usuário logando...");

    const user = {
        email: req.body.email,
        senha: req.body.senha,
        tipo: req.body.tipoUsuario
    };

    let sql = `select * from tb_usuario where email='${user.email}' and senha='${user.senha}'`;


});

module.exports = router;