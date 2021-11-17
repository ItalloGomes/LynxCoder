const db = require("../config/connectDatabase");

'use strict';

const Maquina = db.sequelizeConnection.define('Maquina', 
    {
        id: {
            field: 'id_maquina',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        hostname: {
            field: 'hostname',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        tipo_cpu: {
            field: 'tipo_cpu',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        total_ram: {
            field: 'total_ram',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        total_disco: {
            field: 'total_disco',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        sistema_operacional: {
            field: 'sistema_operacional',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        fk_usuario: {
            field: 'fk_usuario',
            allowNull: false,
            type: db.Sequelize.INTEGER,
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