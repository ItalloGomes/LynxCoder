const router = require("express").Router();
const Maquina = require("../models/maquina");
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

router.get('/:idUser', function(req, res, next) {
	console.log('Maquina usuário: '+req.params.idUser);

	console.log("entrou");
	
    Maquina.findOne({
        where: {
            fk_usuario: req.params.idUser
        }
    }).then(resultado => {

		console.log("recuperou maquina: "+resultado.length);

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