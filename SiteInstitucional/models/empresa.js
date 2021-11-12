const { sequelizeConnection, Sequelize } = require("../config/connectDatabase");

'use strict';

const Empresa = sequelizeConnection.define('Empresa', 
    {
        id: {
            field: 'id_empresa',
            primaryKey: true,
            autoIncrement: true,
            type: Sequelize.INTEGER,
        },
        nome: {
            field: 'nome_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        logo: {
            field: 'logo_empresa',
            allowNull: false,
            type: Sequelize.TEXT
        },
        cnpj: {
            field: 'CNPJ_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        telefone: {
            field: 'telefone_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        estado: {
            field: 'estado_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        cidade: {
            field: 'cidade_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        cep: {
            field: 'CEP_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        logradouro: {
            field: 'logradouro_empresa',
            allowNull: false,
            type: Sequelize.STRING
        },
        numero: {
            field: 'numero_empresa',
            allowNull: false,
            type: Sequelize.STRING
        }
    }, 
    {
        tableName: 'tb_empresa', 
        freezeTableName: true, 
        underscored: true,
        timestamps: false,
    }
);

module.exports = Empresa;