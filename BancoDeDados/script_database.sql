create table tb_squad(
	id_squad int primary key auto_increment
    ,id_trello text not null
    ,nome_squad varchar(100) not null
    ,descricao_squad text
);

create table tb_sprint(
	id_sprint int primary key auto_increment
    ,id_trello text not null
    ,descricao_sprint text
    ,ativa bit
    ,fk_squad int
    ,foreign key(fk_squad) references tb_squad(id_squad)
);

create table tb_empresa(
	id_empresa int primary key auto_increment
    ,nome_empresa varchar(100) not null
    ,logo_empresa text
    ,CNPJ_empresa varchar(100) not null
    ,telefone_empresa varchar(15)
    ,estado_empresa varchar(100)
    ,cidade_empresa varchar(100)
    ,CEP_empresa varchar(15)
    ,logradouro_empresa varchar(100)
    ,numero_empresa varchar(10)
);

create table tb_administrador(
    id_admin int primary key auto_increment
    ,id_trello text not null
    ,key_trello text not null
    ,token_trello text not null
    ,email_admin varchar(100) not null
    ,login_admin varchar(100) not null
    ,senha_admin varchar(45) not null
    ,fk_empresa int
    ,foreign key(fk_empresa) references tb_empresa(id_empresa)
);

create table tb_usuario(
	id_usuario int primary key auto_increment
    ,id_trello text not null
    ,nome_usuario varchar(50) not null
    ,foto_usuario text
    ,login varchar(100) not null
    ,senha varchar(100) not null
    ,isGestor bit
    ,fk_supervisor int
    ,foreign key(fk_supervisor) references tb_usuario(id_usuario)
    ,fk_squad int
    ,foreign key(fk_squad) references tb_squad(id_squad)
    ,fk_empresa int
    ,foreign key(fk_empresa) references tb_empresa(id_empresa)
);

create table tb_tarefa(
	id_tarefa int primary key auto_increment
    ,id_trello text not null
    ,nome_tarefa varchar(100)
    ,pontos int
    ,totalConcluido decimal(5,2)
    ,prazo datetime
    ,fk_usuario int
    ,foreign key(fk_usuario) references tb_usuario(id_usuario)
    ,fk_sprint int
    ,foreign key(fk_sprint) references tb_sprint(id_sprint)
);

create table tb_feedback(
	id_feedback int primary key auto_increment
    ,tipo_feedback varchar(45)
    ,mensagem_feedback text
    ,aproveitamento_feedback decimal(4,2)
    ,facilidade_feedback decimal(4,2)
    ,fk_usuario int
    ,foreign key(fk_usuario) references tb_usuario(id_usuario)
    ,fk_sprint int
    ,foreign key(fk_sprint) references tb_sprint(id_sprint)
);

create table tb_maquina(
	id_maquina int primary key auto_increment
    ,hostname varchar(50)
    ,tipoCPU varchar(100)
    ,totalMemoria varchar(15)
    ,totalDisco varchar(15)
    ,sistemaOperacional varchar(150)
    ,fk_usuario int
    ,foreign key(fk_usuario) references tb_usuario(id_usuario)
);

create table tb_processo(
	id_processo int primary key auto_increment
    ,PID_processo varchar(10)
    ,nome_processo varchar(45) not null
    ,dataHora datetime
    ,fk_maquina int
    ,foreign key(fk_maquina) references tb_maquina(id_maquina)
);

create table tb_leitura(
	id_leitura int primary key auto_increment
    ,porcentagemUsoCPU decimal(5,2)
    ,porcentagemUsoMemoria decimal(5,2)
    ,porcentagemUsoDisco decimal(5,2)
    ,dataHora datetime
    ,fk_maquina int
    ,foreign key(fk_maquina) references tb_maquina(id_maquina)
);

----------------------------------

insert into tb_empresa 
values (
    null
	,'Alpper'			  -- nome_empresa
    ,null				  -- logo_empresa
    ,'05.889.173/0001-61' -- cnpj_empresa
    ,'(11) 2391 9632'     -- telefone
    ,'S??o Paulo'		  -- estado
    ,'S??o Paulo'		  -- cidade
    ,'04505-000'		  -- cep
    ,'Av. Santo Amaro'	  -- logradouro
    ,'48'				  -- numero
);

insert into tb_empresa 
values (
    null
	,'Evolve'		      -- nome_empresa
    ,null				  -- logo_empresa
    ,'12.345.678/0001-71' -- cnpj_empresa
    ,'(11) 3134 8372'     -- telefone
    ,'S??o Paulo'		  -- estado
    ,'Santo Andr??'		  -- cidade
    ,'03205-000'		  -- cep
    ,'Av. Pereira Barreto'-- logradouro
    ,'280'				  -- numero
);

insert into tb_administrador
values (
    null
	,'618f085ba004cf0d54bc26bd'				                            -- id_trello
    ,'9bf1d14a6dd035ff37c36040ca6dafde'				                    -- key_trello
    ,'e927ce9cf5f4a5c1e90eaa6ca4c03dc421dcadaa095d38a389e86eb7a39554e0' -- token_trello
    ,'admalpper.lynxcoder@gmail.com'                                    -- email_admin
    ,'lynxcoderadmalpper'		                                        -- login_admin
    ,'admalpper139231'		                                            -- senha_admin
    ,'1'		                                                        -- fk_empresa
);