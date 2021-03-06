const router = require("express").Router();
const Empresa = require("../models/empresa");

router.post('/addEmpresa', async function (req, res) {

    console.log("Inserindo empresa");

    await Empresa.create({
        nome: req.body.nomeEmpresa,
        email: req.body.emailEmpresa,
        logo: req.body.logoEmpresa,
        cnpj: req.body.cnpjEmpresa,
        telefone: req.body.telefoneEmpresa,
        estado: req.body.estadoEmpresa,
        cidade: req.body.cidadeEmpresa,
        cep: req.body.cepEmpresa,
        logradouro: req.body.logradouro,
        numero: req.body.numeroEmpresa
    }).then(resultado => {

        console.log(`Registro criado: ${resultado}`);

        res.sendStatus(200);
    
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeEmpresa:idEmpresa', (req, res) => {

    Empresa.destroy({
        where: {
            id: req.params.idEmpresa
         }
    }).then(resultado => {
        
        console.log(`Registro deletado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/', function(req, res, next) {
	console.log('Todas Empresas');
	
    Empresa.findAndCountAll().then(resultado => {
		
        console.log(`${resultado.count} registros`);
        
		res.json(resultado.rows);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
});

router.get('/getEmpresaById/:id', function(req, res, next) {
	
    Empresa.findByPk(req.params.id).then( resultado => {
        console.log("Empresa Usuario: "+ resultado.nome);
        res.send(resultado);
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

module.exports = router;