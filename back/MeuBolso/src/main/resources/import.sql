/*TIPO CONTA*/
insert into tipo_conta(id, tipo_conta) values ('1', 'conta_corrente');
insert into tipo_conta(id, tipo_conta) values ('2', 'carteira');
insert into tipo_conta(id, tipo_conta) values ('3', 'poupanca');
insert into tipo_conta(id, tipo_conta) values ('4', 'investimentos');
insert into tipo_conta(id, tipo_conta) values ('5', 'outro');

/*USUARIO - não é possível fazer login com este pois ele não está sendo salvo com senha em hash*/
insert into usuario(id, nome, email, senha) values ('2ebbc2f1-bf81-470c-a5f2-063723d99d2c', 'Maria do Rosario Oliveira', 'mariarosariooli@gmail.com', 'oi2u243i');

/*BANCO*/
insert into banco (nome, icone_url) values ('Bradesco', 'https://play-lh.googleusercontent.com/ReQEaxm44OuduIlJEVO_-xs9iZXSyRNdzGKrONYoLSgAdOzyhPKTb1xuuoPXK6tABm0');
insert into banco (nome, icone_url) values ('Banco do Brasil', 'https://play-lh.googleusercontent.com/1-aNhsSPNqiVluwNGZar_7F5PbQ4u1zteuJ1jumnArhe8bfYHHaVwu4aVOF5-NAmLaA');
insert into banco (nome, icone_url) values ('Nubank', 'https://t.ctcdn.com.br/DIEw0gGtQl_GNhWXJwgrRmuGpIk=/i624750.png');
/*CONTA*/
insert into conta(saldo, tipo_conta_id, banco_id, usuario_id) values ('1412', '1', '3', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');
insert into conta(saldo, tipo_conta_id, banco_id, usuario_id) values ('2000.50', '3', '1', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');

/*CATEGORIAS*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('casa', 'despesa', '8755BC', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');             /*1*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('alimentacao', 'despesa', 'BA0707', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');      /*2*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('transporte', 'despesa', 'C19F13', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');       /*3*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('educacao', 'despesa', '33A841', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');         /*4*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('lazer', 'despesa', '57BEA1', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');            /*5*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('roupas', 'despesa', '5797BE', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');           /*6*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('saude', 'despesa', 'C92D78', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');            /*7*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('pagamentos', 'despesa', '818181', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');       /*8*/

insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('salario', 'receita', '33A841', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');          /*9*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('investimentos', 'receita', '0775BA', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');    /*10*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('presente', 'receita', '8755BC', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');         /*11*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('renda extra', 'receita', 'CE601B', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');      /*12*/
insert into categoria (nome, tipo_categoria, cor, usuario_id) values ('outros', 'receita', 'BC7D18', '2ebbc2f1-bf81-470c-a5f2-063723d99d2c');           /*13*/

/*TRANSACAO*/
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('1000', '2024-12-01', 'venda bicicleta', 'receita', '12', '1');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('16.28', '2024-12-01', 'açaí', 'despesa', '5', '1');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('1412', '2024-12-05', 'salario', 'receita', '9', '2');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('123.55', '2024-12-10', 'conta luz', 'despesa', '1', '1');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('76.20', '2024-12-11', 'conta água', 'despesa', '1', '1');
