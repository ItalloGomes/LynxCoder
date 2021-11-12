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
        totalConcluido: {
            field: 'totalConcluido',
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