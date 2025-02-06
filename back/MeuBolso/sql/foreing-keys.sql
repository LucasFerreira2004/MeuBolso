-- Chaves estrangeiras para a tabela conta
ALTER TABLE conta ADD CONSTRAINT FK_conta_banco FOREIGN KEY (banco_id) REFERENCES banco(id);
ALTER TABLE conta ADD CONSTRAINT FK_conta_tipo_conta FOREIGN KEY (tipo_conta_id) REFERENCES tipo_conta(id);
ALTER TABLE conta ADD CONSTRAINT FK_conta_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

-- Chaves estrangeiras para a tabela categoria
ALTER TABLE categoria ADD CONSTRAINT FK_categoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

-- Chaves estrangeiras para a tabela transacao
ALTER TABLE transacao ADD CONSTRAINT FK_transacao_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id);
ALTER TABLE transacao ADD CONSTRAINT FK_transacao_conta FOREIGN KEY (conta_origem) REFERENCES conta(id);
ALTER TABLE transacao ADD CONSTRAINT FK_transacao_transacao_recorrente FOREIGN KEY (transacao_fixa_id) REFERENCES transacao_recorrente(id);
ALTER TABLE transacao ADD CONSTRAINT FK_transacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

-- Chaves estrangeiras para a tabela transacao_recorrente
ALTER TABLE transacao_recorrente ADD CONSTRAINT FK_transacao_recorrente_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id);
ALTER TABLE transacao_recorrente ADD CONSTRAINT FK_transacao_recorrente_conta FOREIGN KEY (conta_id) REFERENCES conta(id);
ALTER TABLE transacao_recorrente ADD CONSTRAINT FK_transacao_recorrente_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

-- Chaves estrangeiras para a tabela meta
ALTER TABLE meta ADD CONSTRAINT FK_meta_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);

-- Chaves estrangeiras para a tabela orcamento
ALTER TABLE orcamento ADD CONSTRAINT FK_orcamento_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id);
ALTER TABLE orcamento ADD CONSTRAINT FK_orcamento_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id);