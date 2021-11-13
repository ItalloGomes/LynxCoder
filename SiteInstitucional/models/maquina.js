const db = require("../config/connectDatabase");

'use strict';

const Maquina = db.sequelizeConnection.define('Maquina', 
    {
        id: {
            field: 'id_maquina',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        hostname: {
            field: 'hostname',
            allowNull: false,
            type: Sequelize.STRING
        },
        tipoCPU: {
            field: 'tipoCPU',
            allowNull: false,
            type: Sequelize.STRING
        },
        totalMemoria: {
            field: 'totalMemoria',
            allowNull: false,
            type: Sequelize.STRING
        },
        totalDisco: {
            field: 'totalDisco',
            allowNull: false,
            type: Sequelize.STRING
        },
        sistemaOperacional: {
            field: 'sistemaOperacional',
            allowNull: false,
            type: Sequelize.STRING
        },
        fk_usuario: {
            field: 'fk_usuario',
            allowNull: false,
            type: Sequelize.INTEGER,
            foreignKey: true
        }
    }, 
    {
        tableName: 'tb_maquina', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Maquina;