create table banco (id bigint generated by default as identity, icone_url varchar(255), nome varchar(255), primary key (id));
create table categoria (ativa boolean default true not null, interna_sistema boolean default false not null, id bigint generated by default as identity, cor varchar(255) not null, nome varchar(255) not null, tipo_categoria varchar(255) check (tipo_categoria in ('RECEITA','DESPESA','META')), usuario_id varchar(255) not null, primary key (id), unique (usuario_id, nome));
create table conta (saldo_inicial numeric(38,2) not null, banco_id bigint, id bigint generated by default as identity, tipo_conta_id bigint, descricao varchar(255) not null, usuario_id varchar(255) not null, primary key (id), unique (usuario_id, descricao));
create table meta (progresso numeric(38,2), valor_investido numeric(38,2) not null, valor_meta numeric(38,2) not null, id bigint generated by default as identity, comentario varchar(255), descricao varchar(255) not null, url_img varchar(255), usuario_id varchar(255) not null, primary key (id), unique (usuario_id, descricao));
create table notificacao_meta (notificado boolean, threshold integer, id bigint generated by default as identity, meta_id bigint, primary key (id));
create table notificacao_orcamento (notificado boolean not null, threshold integer, id bigint generated by default as identity, orcamento_id bigint unique, primary key (id));
create table orcamento (ano integer not null, mes integer not null, progresso numeric(38,2), valor_estimado numeric(38,2) not null, valor_gasto numeric(38,2), valor_restante numeric(38,2), categoria_id bigint not null, id bigint generated by default as identity, descricao varchar(255), usuario_id varchar(255) not null, primary key (id), unique (usuario_id, mes, ano, categoria_id));
create table tipo_conta (id bigint generated by default as identity, tipo_conta varchar(255) check (tipo_conta in ('CONTA_CORRENTE','CARTEIRA','POUPANCA','INVESTIMENTOS','OUTRO')), primary key (id));
create table transacao (data DATE not null, valor numeric(38,2) not null, categoria_id bigint not null, conta_origem bigint not null, id bigint generated by default as identity, transacao_recorrente_id bigint, comentario TEXT, descricao TEXT not null, origem_transacao varchar(255) check (origem_transacao in ('NORMAL','FIXA','PARCELADA')), tipo varchar(255) not null check (tipo in ('RECEITA','DESPESA','META')), usuario_id varchar(255) not null, primary key (id));
create table transacao_meta (id bigint generated by default as identity, meta_id bigint not null, transacao_id bigint not null unique, primary key (id));
create table transacao_recorrente (ativa boolean default true not null, data_cadastro DATE not null, data_final date, qtd_parcelas integer, ultima_execucao date, valor numeric(38,2) not null, categoria_id bigint not null, conta_id bigint not null, id bigint generated by default as identity, descricao varchar(255) not null, periodicidade varchar(255) not null check (periodicidade in ('DIARIO','SEMANAL','MENSAL','ULTIMO_DIA_MES')), tipo varchar(255) not null check (tipo in ('RECEITA','DESPESA','META')), tipo_repeticao varchar(255) not null check (tipo_repeticao in ('FIXO','PARCELAMENTO')), usuario_id varchar(255) not null, primary key (id));
create table usuario (email varchar(255) not null unique, id varchar(255) not null, img_url varchar(255), nome varchar(255) not null, senha varchar(255) not null, primary key (id));

-- CATEGORIA
alter table if exists categoria
    add constraint fk_categoria_usuario
    foreign key (usuario_id)
    references usuario;

-- CONTA
alter table if exists conta
    add constraint fk_conta_banco
    foreign key (banco_id)
    references banco
    on delete cascade;

alter table if exists conta
    add constraint fk_conta_tipo_conta
    foreign key (tipo_conta_id)
    references tipo_conta;

alter table if exists conta
    add constraint fk_conta_usuario
    foreign key (usuario_id)
    references usuario;

-- META
alter table if exists meta
    add constraint fk_meta_usuario
    foreign key (usuario_id)
    references usuario;

-- NOTIFICACAO_META
alter table if exists notificacao_meta
    add constraint fk_notificacao_meta_meta
    foreign key (meta_id)
    references meta;

-- NOTIFICACAO_ORCAMENTO
alter table if exists notificacao_orcamento
    add constraint fk_notificacao_orcamento_orcamento
    foreign key (orcamento_id)
    references orcamento;

-- ORCAMENTO
alter table if exists orcamento
    add constraint fk_orcamento_categoria
    foreign key (categoria_id)
    references categoria;

alter table if exists orcamento
    add constraint fk_orcamento_usuario
    foreign key (usuario_id)
    references usuario;

-- TRANSACAO
alter table if exists transacao
    add constraint fk_transacao_categoria
    foreign key (categoria_id)
    references categoria;

alter table if exists transacao
    add constraint fk_transacao_conta_origem
    foreign key (conta_origem)
    references conta;

alter table if exists transacao
    add constraint fk_transacao_transacao_recorrente
    foreign key (transacao_recorrente_id)
    references transacao_recorrente;

alter table if exists transacao
    add constraint fk_transacao_usuario
    foreign key (usuario_id)
    references usuario;

-- TRANSACAO_META
alter table if exists transacao_meta
    add constraint fk_transacao_meta_meta
    foreign key (meta_id)
    references meta;

alter table if exists transacao_meta
    add constraint fk_transacao_meta_transacao
    foreign key (transacao_id)
    references transacao;

-- TRANSACAO_RECORRENTE
alter table if exists transacao_recorrente
    add constraint fk_transacao_recorrente_categoria
    foreign key (categoria_id)
    references categoria;

alter table if exists transacao_recorrente
    add constraint fk_transacao_recorrente_conta
    foreign key (conta_id)
    references conta;

alter table if exists transacao_recorrente
    add constraint fk_transacao_recorrente_usuario
    foreign key (usuario_id)
    references usuario;

insert into tipo_conta(id, tipo_conta) values ('1', 'CONTA_CORRENTE');
insert into tipo_conta(id, tipo_conta) values ('2', 'CARTEIRA');
insert into tipo_conta(id, tipo_conta) values ('3', 'POUPANCA');
insert into tipo_conta(id, tipo_conta) values ('4', 'INVESTIMENTOS');
insert into tipo_conta(id, tipo_conta) values ('5', 'OUTRO');

insert into banco (nome, icone_url) values ('Banco do Brasil', 'https://play-lh.googleusercontent.com/1-aNhsSPNqiVluwNGZar_7F5PbQ4u1zteuJ1jumnArhe8bfYHHaVwu4aVOF5-NAmLaA=w240-h480-rw');
insert into banco (nome, icone_url) values ('Banrisul', 'https://play-lh.googleusercontent.com/EesIfzYxpFnep4xcOBAV7uyhx_L4gvfcCN2eatEDSxW6yjy75Q8MmLQNasQ20J585D4=w240-h480-rw');
insert into banco (nome, icone_url) values ('Bradesco', 'https://play-lh.googleusercontent.com/ReQEaxm44OuduIlJEVO_-xs9iZXSyRNdzGKrONYoLSgAdOzyhPKTb1xuuoPXK6tABm0=w240-h480-rw');
insert into banco (nome, icone_url) values ('Banco de Brasília', 'https://play-lh.googleusercontent.com/xFXL9qp0F4yOeMaArR7-yQEFVXFJYfkj-QwidkPRsSMQFdUAhosBfivnxRoBuDWIUZGX=w240-h480-rw');
insert into banco (nome, icone_url) values ('C6-Bank', 'https://play-lh.googleusercontent.com/qYXhGgBxFLr5xgnv0AGhqW9v7tyedb_i5AVoebI6pow5pWPNZH1qEHnslmSHNkVpB-g=w240-h480-rw');
insert into banco (nome, icone_url) values ('Caixa Econômica', 'https://play-lh.googleusercontent.com/ubV0x2kGJIEe10shxuFnH9Cy21OgHARwVUZ89nyE0YOZN9c25ov_dyHdk1rMgbPvoDI=w240-h480-rw');
insert into banco (nome, icone_url) values ('Inter', 'https://play-lh.googleusercontent.com/DABQ3z4xA93QNsK9wqR2LdnamoDHkaKc-h1AueqJrVE7pP9GkIvZqf_URfxOIiNbFyzK=w240-h480-rw');
insert into banco (nome, icone_url) values ('Itaú', 'https://play-lh.googleusercontent.com/gRcutACE4XkEHmxcbUdOehxpTbp_LjmwJ6qIEbqfD34oh9feTNhTnlDgf97HEZ9eGKY=w240-h480-rw');
insert into banco (nome, icone_url) values ('Mercado Pago', 'https://play-lh.googleusercontent.com/998jkBzpuMiCaJeG6nZLB6GFi56DeNNf5iV509W5RueTRp_HBnH5EGyCICmSqGgGXcRt=w240-h480-rw');
insert into banco (nome, icone_url) values ('Neon', 'https://play-lh.googleusercontent.com/Kik3BeultZtG_GIRRXRPDcenbpVsORTgw6MA9UQMYS1mTI88Z4vlcyBhywUvYj0B2-3m=s48-rw');
insert into banco (nome, icone_url) values ('Next', 'https://play-lh.googleusercontent.com/H10aAKl4vs91sow3buRJk85cEN58T7onVyNqVQPnWnEpRQmelArjGLdUx05imRQePjCD=w240-h480-rw');
insert into banco (nome, icone_url) values ('Nubank', 'https://play-lh.googleusercontent.com/NPkx0aiwABB31gBw_CuZO9Rwukhir-BwemxfNlAVjT6smwk6QgUbb3XrmsSSClfzk0dY=w240-h480-rw');
insert into banco (nome, icone_url) values ('PagBank', 'https://play-lh.googleusercontent.com/O9GpqGB-9aE8Qt79JM1VXoVA5rRQjLb4LVk7yVwd2cuWeAi0ML6uVbc7aXZEOeyYwg=w240-h480-rw');
insert into banco (nome, icone_url) values ('Pan', 'https://play-lh.googleusercontent.com/KVoKo2vX9E3ZjwfOL7eXvMWrmqMVAPLz96ePKd3QhKFDABTtPY9laAwTzJELzy7-fqKp=s48-rw');
insert into banco (nome, icone_url) values ('PicPay', 'https://play-lh.googleusercontent.com/pTvc9kCumx_24eJDwGUpvcBwljcIBkrsL3qHwhBW2NalMQ-XxTtHRV9YAJanBxkV0Rw=s48-rw');
insert into banco (nome, icone_url) values ('Santander', 'https://play-lh.googleusercontent.com/g_QDzrOlw8Belx8qb47fUu0MPL6AVFzDdbOz_NJZYQDNLveHYxwiUoe09Wvkxf-_548q=w240-h480-rw');
insert into banco (nome, icone_url) values ('Will', 'https://play-lh.googleusercontent.com/fazUU0h6BiMpjSxgq1son0-VRhBhkimr6kzCLw6T1Y3ZGZj1Nrnvi-ETM2je3-miRQ=s48-rw');
insert into banco (nome, icone_url) values ('Outro', 'https://res.cloudinary.com/dniz7sgoh/image/upload/v1740324476/MeuBolso/aa7nx19ph6mhlijggnkt.png');
