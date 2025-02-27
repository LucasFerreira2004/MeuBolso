-- Inserção de usuário
INSERT INTO usuario (id, nome, email, senha) VALUES
    ('2ebbc2f1-bf81-470c-a5f2-063723d99d2c', 'Maria do Rosario Oliveira', 'mariarosariooli@gmail.com', 'oi2u243i');

INSERT INTO tipo_conta(id, tipo_conta) VALUES ('1', 'CONTA_CORRENTE');
INSERT INTO tipo_conta(id, tipo_conta) VALUES ('2', 'CARTEIRA');
INSERT INTO tipo_conta(id, tipo_conta) VALUES ('3', 'POUPANCA');
INSERT INTO tipo_conta(id, tipo_conta) VALUES ('4', 'INVESTIMENTOS');
INSERT INTO tipo_conta(id, tipo_conta) VALUES ('5', 'OUTRO');

INSERT INTO banco (nome, icone_url) VALUES ('Banco do Brasil', 'https://play-lh.googleusercontent.com/1-aNhsSPNqiVluwNGZar_7F5PbQ4u1zteuJ1jumnArhe8bfYHHaVwu4aVOF5-NAmLaA=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Banrisul', 'https://play-lh.googleusercontent.com/EesIfzYxpFnep4xcOBAV7uyhx_L4gvfcCN2eatEDSxW6yjy75Q8MmLQNasQ20J585D4=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Bradesco', 'https://play-lh.googleusercontent.com/ReQEaxm44OuduIlJEVO_-xs9iZXSyRNdzGKrONYoLSgAdOzyhPKTb1xuuoPXK6tABm0=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Banco de Brasília', 'https://play-lh.googleusercontent.com/xFXL9qp0F4yOeMaArR7-yQEFVXFJYfkj-QwidkPRsSMQFdUAhosBfivnxRoBuDWIUZGX=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('C6-Bank', 'https://play-lh.googleusercontent.com/qYXhGgBxFLr5xgnv0AGhqW9v7tyedb_i5AVoebI6pow5pWPNZH1qEHnslmSHNkVpB-g=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Caixa Econômica', 'https://play-lh.googleusercontent.com/ubV0x2kGJIEe10shxuFnH9Cy21OgHARwVUZ89nyE0YOZN9c25ov_dyHdk1rMgbPvoDI=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Inter', 'https://play-lh.googleusercontent.com/DABQ3z4xA93QNsK9wqR2LdnamoDHkaKc-h1AueqJrVE7pP9GkIvZqf_URfxOIiNbFyzK=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Itaú', 'https://play-lh.googleusercontent.com/gRcutACE4XkEHmxcbUdOehxpTbp_LjmwJ6qIEbqfD34oh9feTNhTnlDgf97HEZ9eGKY=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Mercado Pago', 'https://play-lh.googleusercontent.com/998jkBzpuMiCaJeG6nZLB6GFi56DeNNf5iV509W5RueTRp_HBnH5EGyCICmSqGgGXcRt=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Neon', 'https://play-lh.googleusercontent.com/Kik3BeultZtG_GIRRXRPDcenbpVsORTgw6MA9UQMYS1mTI88Z4vlcyBhywUvYj0B2-3m=s48-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Next', 'https://play-lh.googleusercontent.com/H10aAKl4vs91sow3buRJk85cEN58T7onVyNqVQPnWnEpRQmelArjGLdUx05imRQePjCD=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Nubank', 'https://play-lh.googleusercontent.com/NPkx0aiwABB31gBw_CuZO9Rwukhir-BwemxfNlAVjT6smwk6QgUbb3XrmsSSClfzk0dY=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('PagBank', 'https://play-lh.googleusercontent.com/O9GpqGB-9aE8Qt79JM1VXoVA5rRQjLb4LVk7yVwd2cuWeAi0ML6uVbc7aXZEOeyYwg=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Pan', 'https://play-lh.googleusercontent.com/KVoKo2vX9E3ZjwfOL7eXvMWrmqMVAPLz96ePKd3QhKFDABTtPY9laAwTzJELzy7-fqKp=s48-rw');
INSERT INTO banco (nome, icone_url) VALUES ('PicPay', 'https://play-lh.googleusercontent.com/pTvc9kCumx_24eJDwGUpvcBwljcIBkrsL3qHwhBW2NalMQ-XxTtHRV9YAJanBxkV0Rw=s48-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Santander', 'https://play-lh.googleusercontent.com/g_QDzrOlw8Belx8qb47fUu0MPL6AVFzDdbOz_NJZYQDNLveHYxwiUoe09Wvkxf-_548q=w240-h480-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Will', 'https://play-lh.googleusercontent.com/fazUU0h6BiMpjSxgq1son0-VRhBhkimr6kzCLw6T1Y3ZGZj1Nrnvi-ETM2je3-miRQ=s48-rw');
INSERT INTO banco (nome, icone_url) VALUES ('Outro', 'https://res.cloudinary.com/dniz7sgoh/image/upload/v1740324476/MeuBolso/aa7nx19ph6mhlijggnkt.png');

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


