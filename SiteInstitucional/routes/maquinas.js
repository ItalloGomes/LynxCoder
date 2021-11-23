const router = require("express").Router();
const db = require('../config/connectDatabase');
const Usuario = require("../models/Usuario");
const Maquina = require("../models/Administrador");
const Leitura = require("../models/leitura");

router.get('/', function(req, res, next) {
	console.log('Todas maquinas');
	
    Maquina.findAndCountAll().then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/maquinaUsuario/:idUser', function(req, res, next) {
	console.log('Maquina usuário: '+req.params.idUser);
	
    Maquina.findOne({
        where: {
            fk_usuario: req.params.idUser
        }
    }).then(resultado => {

		res.json(resultado);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/leiturasMaquina/:fk_maquina', function(req, res, next) {

	console.log('Leituras maquina usuário: '+req.params.fk_maquina);
	
    Leitura.findOne({
        where: {
            fk_maquina: req.params.fk_maquina
        },
        order: [
            ['id_leitura', 'DESC'],
        ]
    }).then(resultado => {

		res.json(resultado);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

module.exports = router;