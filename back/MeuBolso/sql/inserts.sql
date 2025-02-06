-- Inserção de tipos de conta
INSERT INTO tipo_conta (id, tipo_conta) VALUES
    (1, 'CONTA_CORRENTE'),
    (2, 'CARTEIRA'),
    (3, 'POUPANCA'),
    (4, 'INVESTIMENTOS'),
    (5, 'OUTRO');

-- Inserção de usuário
INSERT INTO usuario (id, nome, email, senha) VALUES
    ('2ebbc2f1-bf81-470c-a5f2-063723d99d2c', 'Maria do Rosario Oliveira', 'mariarosariooli@gmail.com', 'oi2u243i');

-- Inserção de bancos
INSERT INTO banco (nome, icone_url) VALUES
    ('Bradesco', 'https://play-lh.googleusercontent.com/ReQEaxm44OuduIlJEVO_-xs9iZXSyRNdzGKrONYoLSgAdOzyhPKTb1xuuoPXK6tABm0'),
    ('Banco do Brasil', 'https://play-lh.googleusercontent.com/1-aNhsSPNqiVluwNGZar_7F5PbQ4u1zteuJ1jumnArhe8bfYHHaVwu4aVOF5-NAmLaA'),
    ('Nubank', 'https://t.ctcdn.com.br/DIEw0gGtQl_GNhWXJwgrRmuGpIk=/i624750.png');

-- Inserção de contas
INSERT INTO conta (tipo_conta_id, banco_id, usuario_id, descricao) VALUES
    (1, 3, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c', 'conta01'),
    (3, 1, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c', 'conta02');

-- Inserção de categorias
INSERT INTO categoria (nome, tipo_categoria, cor, usuario_id) VALUES
    ('casa', 'DESPESA', '8755BC', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('alimentacao', 'DESPESA', 'BA0707', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('transporte', 'DESPESA', 'C19F13', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('educacao', 'DESPESA', '33A841', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('lazer', 'DESPESA', '57BEA1', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('roupas', 'DESPESA', '5797BE', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('saude', 'DESPESA', 'C92D78', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('pagamentos', 'DESPESA', '818181', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('salario', 'RECEITA', '33A841', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('investimentos', 'RECEITA', '0775BA', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('presente', 'RECEITA', '8755BC', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('renda extra', 'RECEITA', 'CE601B', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    ('outros', 'RECEITA', 'BC7D18', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');

-- Inserção de transações
INSERT INTO transacao (valor, data, descricao, tipo, categoria_id, conta_origem, usuario_id) VALUES
    (1000, '2024-12-01', 'venda bicicleta', 'RECEITA', 12, 1, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (16.28, '2024-12-01', 'açaí', 'DESPESA', 5, 1, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (1412, '2024-12-05', 'salario', 'RECEITA', 9, 2, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (123.55, '2024-12-10', 'conta luz', 'DESPESA', 1, 1, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (76.20, '2024-12-11', 'conta água', 'DESPESA', 1, 1, '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');

-- Inserção de metas
INSERT INTO meta (valor_investido, valor_meta, descricao, url_img, usuario_id) VALUES
    (500.00, 1000.00, 'Comprar uma nova bicicleta', 'https://exemplo.com/bicicleta.jpg', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (200.00, 500.00, 'Reserva de emergência', 'https://exemplo.com/reserva.jpg', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (1000.00, 5000.00, 'Viagem para a praia', 'https://exemplo.com/viagem.jpg', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');

-- Inserção de orçamentos para o usuário 'Maria do Rosario Oliveira'
INSERT INTO orcamento (ano, mes, valor_estimado, valor_gasto, valor_restante, categoria_id, descricao, usuario_id) VALUES
    (2024, 12, 2000.00, 1500.00, 500.00, 1, 'Orçamento para despesas de casa', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (2024, 12, 500.00, 300.00, 200.00, 2, 'Orçamento para alimentação', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (2024, 12, 300.00, 100.00, 200.00, 3, 'Orçamento para transporte', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c'),
    (2024, 12, 1000.00, 800.00, 200.00, 5, 'Orçamento para lazer', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');


