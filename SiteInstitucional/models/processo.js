const { sequelizeConnection, Sequelize } = require("../config/connectDatabase");

'use strict';

const Processo = sequelizeConnection.define('Processo', 
    {
        id: {
            field: 'id_processo',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        pid: {
            field: 'PID_processo',
            allowNull: false,
            type: Sequelize.INTEGER
        },
        nome: {
            field: 'nome_processo',
            allowNull: false,
            type: Sequelize.STRING
        },
        dataHora: {
            field: 'dataHora',
            allowNull: false,
            type: Sequelize.DATE
        },
        fk_maquina: {
            field: 'fk_maquina',
            allowNull: false,
            type: Sequelize.INTEGER,
            foreignKey: true
        }
    }, 
    {
        tableName: 'tb_processo', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Processo;