const db = require("../config/connectDatabase");

'use strict';

const Leitura = db.sequelizeConnection.define('Leitura', 
    {
        id: {
            field: 'id_leitura',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        porcentagemUsoCPU: {
            field: 'porcentagemUsoCPU',
            allowNull: false,
            type: Sequelize.DECIMAL
        },
        porcentagemUsoMemoria: {
            field: 'porcentagemUsoMemoria',
            allowNull: false,
            type: Sequelize.DECIMAL
        },
        porcentagemUsoDisco: {
            field: 'porcentagemUsoDisco',
            allowNull: false,
            type: Sequelize.DECIMAL
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
        tableName: 'tb_leitura', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Leitura;