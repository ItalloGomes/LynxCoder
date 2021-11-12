const { sequelizeConnection, Sequelize } = require("../config/connectDatabase");

'use strict';

const Sprint = sequelizeConnection.define('Sprint', 
    {
        id: {
            field: 'id_sprint',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        descricao: {
            field: 'descricao_sprint',
            allowNull: false,
            type: Sequelize.TEXT
        },
        fk_squad: {
            field: 'fk_squad',
            allowNull: false,
            type: Sequelize.INTEGER,
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