const db = require("../config/connectDatabase");

'use strict';

const Leitura = db.sequelizeConnection.define('Leitura', 
    {
        id: {
            field: 'id_leitura',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        porcentagem_uso_cpu: {
            field: 'porcentagem_uso_cpu',
            allowNull: false,
            type: db.Sequelize.DECIMAL
        },
        porcentagem_uso_ram: {
            field: 'porcentagem_uso_ram',
            allowNull: false,
            type: db.Sequelize.DECIMAL
        },
        porcentagem_uso_disco: {
            field: 'porcentagem_uso_disco',
            allowNull: false,
            type: db.Sequelize.DECIMAL
        },
        data_hora: {
            field: 'data_hora',
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
        tableName: 'tb_leitura', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Leitura;