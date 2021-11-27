const router = require("express").Router();
const Usuario = require("../models/Usuario");

router.get('/usuariosEmpresa/:idSquad/:offSet', (req, res, next) => {

    Usuario.findAll({ 
        limit: 3,
        offset: req.params.offSet,
        where: {
            fk_squad: req.params.idSquad
        }
    }).then(response => {

        console.log(`${response.count} registros`);

		res.json(response.rows);
    
    }).catch(err => {
        console.error(err);
		res.status(500).send(err.message);
    });

});

module.exports = router;