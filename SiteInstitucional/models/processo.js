const db = require("../config/connectDatabase");

'use strict';

const Processo = db.sequelizeConnection.define('Processo', 
    {
        id: {
            field: 'id_processo',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        pid: {
            field: 'PID_processo',
            allowNull: false,
            type: db.Sequelize.INTEGER
        },
        nome: {
            field: 'nome_processo',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        dataHora: {
            field: 'dataHora',
            allowNull: false,
            type: db.Sequelize.DATE
        },
        fk_maquina: {
            field: 'fk_maquina',
            allowNull: false,
            type: db.Sequelize.INTEGER,
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