const db = require("../config/connectDatabase");

'use strict';

const Tarefa = db.sequelizeConnection.define('Tarefa', 
    {
        id: {
            field: 'id_tarefa',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        id_trello: {
            field: 'id_tarefa',
            allowNull: false,
            type: Sequelize.STRING,
        },
        nome: {
            field: 'nome_tarefa',
            allowNull: false,
            type: Sequelize.STRING
        },
        pontos: {
            field: 'pontos',
            allowNull: false,
            type: Sequelize.INTEGER
        },
        total_concluido: {
            field: 'total_concluido',
            allowNull: false,
            type: Sequelize.DECIMAL
        },
        prazo: {
            field: 'prazo',
            allowNull: false,
            type: Sequelize.DATE
        },
        fk_usuario: {
            field: 'fk_usuario',
            allowNull: false,
            type: Sequelize.INTEGER
        },
        fk_sprint: {
            field: 'fk_sprint',
            allowNull: false,
            type: Sequelize.INTEGER
        }
    }, 
    {
        tableName: 'tb_tarefa', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Tarefa;