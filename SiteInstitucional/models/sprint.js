const db = require("../config/connectDatabase");

'use strict';

const Sprint = db.sequelizeConnection.define('Sprint', 
    {
        id: {
            field: 'id_sprint',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        id_trello: {
            field: 'id_trello',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        descricao: {
            field: 'descricao_sprint',
            allowNull: false,
            type: db.Sequelize.TEXT
        },
        ativa: {
            field: 'ativa',
            allowNull: false,
            type: db.Sequelize.BOOLEAN
        },
        fk_squad: {
            field: 'fk_squad',
            allowNull: false,
            type: db.Sequelize.INTEGER,
            foreignKey: true
        }
    }, 
    {
        tableName: 'tb_sprint', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Sprint;