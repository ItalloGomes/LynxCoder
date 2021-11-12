const db = require("../config/connectDatabase");

'use strict';

const Squad = db.sequelizeConnection.define('Squad', 
    {
        id: {
            field: 'id_squad',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        nome: {
            field: 'nome_squad',
            allowNull: false,
            type: Sequelize.STRING
        },
        descricao: {
            field: 'descricao_squad',
            allowNull: false,
            type: Sequelize.TEXT
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