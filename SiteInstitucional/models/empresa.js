const db = require("../config/connectDatabase");

'use strict';

const Empresa = db.sequelizeConnection.define('Empresa', 
    {
        id: {
            field: 'id_empresa',
            primaryKey: true,
            autoIncrement: true,
            type: db.Sequelize.INTEGER,
        },
        nome: {
            field: 'nome_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        logo: {
            field: 'logo_empresa',
            allowNull: false,
            type: db.Sequelize.TEXT
        },
        cnpj: {
            field: 'CNPJ_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        telefone: {
            field: 'telefone_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        estado: {
            field: 'estado_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        cidade: {
            field: 'cidade_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        cep: {
            field: 'CEP_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        logradouro: {
            field: 'logradouro_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        numero: {
            field: 'numero_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
        },
        email: {
            field: 'email_empresa',
            allowNull: false,
            type: db.Sequelize.STRING
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