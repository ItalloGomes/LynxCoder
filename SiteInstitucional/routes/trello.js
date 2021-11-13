const { Router } = require("express");
const router = Router();
const db = require('../config/connectDatabase');

var fk_empresa;

router.get('/', (req, res) => {
    res.render('trello', { title: 'Express' });
});

router.get('/buscar_fk_empresa/:id_admin', (req, res) => {

    console.log("Recuperando foreign key da empresa...");

    let id_admin = req.params.id_admin;

    let instrucaoSql = `select fk_empresa from tb_administrador where id_admin = '${id_admin}'`;
    console.log(instrucaoSql);

    sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
        .then(resultado => {
            fk_empresa = resultado[0].fk_empresa;
            res.send(resultado);
        }).catch(erro => {
            console.error(erro);
            res.status(500).send(erro.message);
        });

});

router.get('/cadastrar_squads/:id_admin/:key/:token', (req, res) => {

    console.log("Recuperando boards da empresa...");

    let id_admin = req.params.id_admin;
    let key = req.params.key;
    let token = req.params.token;

    var url = new URL(`https://trello.com/1/members/${id_admin}/boards`),
        params = { key: key, token: token }
    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

    fetch(url, {
        method: "GET"
    }).then(function (resultado) {

        if (resultado.ok) {

            resultado.json().then(json => {

                json.forEach(board => {
                    Squad.create({
                        id_trello: board.id,
                        nome_squad: board.name,
                        descricao_squad: board.desc
                    }).then(resultado => {
                        console.log(`Registro criado: ${resultado}`)

                        let membershipList = [];
                        board.memberships.forEach(membership => {
                            if (membership.idMember != id_admin) {
                                membershipList.push(membership);
                            }
                        });

                        if (membershipList.length() > 0) {
                            res.json(membershipList);
                        } else {
                            res.status(500).send("Não foram encontrados membros na squad");
                        }
                        
                    }).catch(erro => {
                        console.error(erro);
                        res.status(500).send(erro.message);
                    });
                });
            });

        } else {
            console.log("Erro ao recuperar boards da empresa");
        }

    });

});

router.get('/listar_usuarios_squad/:id_board/:memberships/:key/:token', (req, res) => {

    console.log("Recuperando usuários da squad...");

    let id_board = req.params.id_board;

    let instrucaoSql = `select id_squad from tb_squad where id_trello = '${id_board}'`;
    console.log(instrucaoSql);

    sequelize.query(instrucaoSql, { type: sequelize.QueryTypes.SELECT })
        .then(resultado => {
            let fk_squad = resultado.id_squad;
            let memberships = req.params.memberships;
            let key = req.params.key;
            let token = req.params.token;

            let memberList = [];

            memberships.forEach(membership => {
                var url = new URL(`https://trello.com/1/members/${membership.idMember}`),
                    params = { key: key, token: token }
                Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

                fetch(url, {
                    method: "GET"
                }).then(function (resultado) {

                    if (resultado.ok) {

                        resultado.json().then(member => {

                            memberList.push({
                                id_trello: member.id,
                                nome_usuario: member.fullName,
                                foto_usuario: member.avatarUrl,
                                login: member.username,
                                senha: member.username + (Math.random() * (1_000_000 - 100_000) + 100_000),
                                is_gestor: null,
                                fk_supervisor: null,
                                fk_squad: fk_squad,
                                fk_empresa: fk_empresa
                            });

                        });
                    } else {
                        console.log("Erro ao listar usuários da squad");
                    }

                });
            });
            res.json(memberList);
        }).catch(erro => {
            console.error(erro);
            res.status(500).send(erro.message);
        });

});

module.exports = router;