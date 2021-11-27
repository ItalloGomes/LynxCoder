const router = require("express").Router();
const { where } = require("sequelize/types");
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");

router.get('/usuariosEmpresa/:idSquad/:offSet', (req, res, next) => {

    Usuario.findAll({ 
        limit: 3,
        offset: req.params.offSet,
        where: {
            fk_squad: req.params.idSquad
        }
    }).then(response => {

        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);
    
    }).catch(err => {
        console.error(erro);
		res.status(500).send(erro.message);
    });

});