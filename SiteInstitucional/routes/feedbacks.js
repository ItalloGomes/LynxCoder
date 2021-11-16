const router = require("express").Router();
const db = require('../config/connectDatabase');
const FeedBack = require("../models/Feedback");

router.post('/addFeedback', (req, res) => {

    FeedBack.create({
        mensagem: req.body.mensagem,
        aproveitamento: req.body.aproveitamento,
        facilidade: req.body.facilidade,
        fk_usuario: req.body.idUsuario,
        fk_sprint: req.body.idSprint
    }).then(resultado => {
        
        console.log(`Registro criado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.delete('/removeFeedback/:idFeedback', (req, res) => {

    FeedBack.destroy({
        where: {
            id: req.params.idFeedback
         }
    }).then(resultado => {
        
        console.log(`Registro deletado: ${resultado}`)

        res.sendStatus(200);
        
    }).catch(erro => {
        console.error(erro);
        res.status(500).send(erro.message);
    });

});

router.get('/:userId', (req, res) => {

    console.log("Listando todos feedbacks do usuario");

    let userId = req.params.userId;

    sql = `select * from tb_feedback where fk_usuario='${userId}'`;
    
    db.sequelizeConnection.query(sql, {
        model: FeedBack
    }).then(resultado => {

        console.log(`Feedbacks encontrados: ${resultado.length}`);

        res.json(resultado);
        
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
    
});

router.get('/:userId/:sprintId', (req, res) => {

    console.log("Listando todos feedbacks do usuario em uma determinada sprint");

    const params = {
        userId: req.params.userId,
        sprintId: req.params.sprintId
    }

    sql = `select * from tb_feedback where fk_usuario='${params.userId}' 
                                        and fk_sprint='${params.sprintId}'`;
    
    db.sequelizeConnection.query(sql, {
        model: FeedBack
    }).then(resultado => {

        console.log(`Feedbacks encontrados: ${resultado.length}`);

        res.json(resultado);
        
    }).catch(erro => {
		console.error(erro);
		res.status(500).send(erro.message);
  	});
    
});



module.exports = router;