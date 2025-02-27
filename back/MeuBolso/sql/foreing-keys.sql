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