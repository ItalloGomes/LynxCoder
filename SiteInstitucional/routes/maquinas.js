const router = require("express").Router();
const Maquina = require("../models/maquina");
const Leitura = require("../models/leitura");
const db = require('../config/connectDatabase');

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

router.get('/alertasQuartis/:fk_maquina', function(req, res, next) {

	console.log("Alertas quartis");

	var id = req.params.fk_maquina;

	var sql = `select 
					(select count(*) from tb_leitura as l where l.porcentagem_uso_ram > 80 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'RAM_critico',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_ram between 70 and 80 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'RAM_alto',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_ram between 50 and 69 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'RAM_moderado',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_ram < 50 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'RAM_baixo',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_cpu > 85 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'CPU_critico',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_cpu between 75 and 85 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'CPU_alto',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_cpu between 55 and 74 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'CPU_moderado',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_cpu < 50 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'CPU_baixo',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_disco > 80 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'Disco_critico',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_disco between 70 and 80 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'Disco_alto',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_disco between 50 and 69 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'Disco_moderado',
					(select count(*) from tb_leitura as l where l.porcentagem_uso_disco < 50 and l.data_hora >= CONVERT (date, dateadd(DD,-1,getdate()))) as 'Disco_baixo'
				from tb_leitura as l
					where l.fk_maquina = ${id} group by l.fk_maquina`;
	
	db.sequelizeConnection.query(sql, {
		model: Leitura
	}).then(resultado => {
		
		res.json(resultado);

	}).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
		});

});

module.exports = router;