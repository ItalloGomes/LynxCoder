const router = require("express").Router();
const Squad = require("../models/Squad");

router.post('/addSquad', (req, res) => {

    Squad.create({
        id_trello: req.body.id_trello,
        nome: req.body.nome,
        descricao: req.body.descricao,
        fk_empresa: req.body.fk_empresa
    }).then(resultado => {
        
        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/squadComIdTrello/:idTrello', (req, res) => {

    Squad.findAndCountAll({
        where: {
            id_trello: req.params.idTrello
         }
    }).then(resultado => {
        res.json(resultado.rows);
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeSquad:idSquad', (req, res) => {

    Squad.destroy({
        where: {
            id: req.params.idSquad
         }
    }).then(resultado => {
        
        console.log(`Registro deletado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/:fkEmpresa', function(req, res, next) {
	console.log('Todas Squads');
	
    Squad.findAndCountAll({
        where: {
            fk_empresa: req.params.fkEmpresa
        }
    }).then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/', function(req, res, next) {
	console.log('Todas Squads');
	
    Squad.findAndCountAll().then(resultado => {
		
        console.log(`${resultado.count} registros`);

		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

module.exports = router;