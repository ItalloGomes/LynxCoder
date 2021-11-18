const db = require("../config/connectDatabase");

'use strict';

const Squad = db.sequelizeConnection.define('Squad', 
    {
        id: {
            field: 'id_squad',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        id_trello: {
            field: 'id_trello',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        nome: {
            field: 'nome_squad',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        descricao: {
            field: 'descricao_squad',
            allowNull: false,
            type: db.Sequelize.TEXT
        },
    }, 
    {
        tableName: 'tb_squad', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Squad;