/*TIPO CONTA*/
insert into tipo_conta(id, tipo_conta) values ('1', 'conta_corrente');
insert into tipo_conta(id, tipo_conta) values ('2', 'carteira');
insert into tipo_conta(id, tipo_conta) values ('3', 'poupanca');
insert into tipo_conta(id, tipo_conta) values ('4', 'investimentos');
insert into tipo_conta(id, tipo_conta) values ('5', 'outro');

/*USUARIO*/
insert into usuario(nome, email, senha) values ('Maria do Rosario Oliveira', 'mariarosariooli@gmail.com', 'oi2u243i');

/*BANCO*/
insert into banco (nome, icone_url) values ('Bradesco', 'https://play-lh.googleusercontent.com/ReQEaxm44OuduIlJEVO_-xs9iZXSyRNdzGKrONYoLSgAdOzyhPKTb1xuuoPXK6tABm0');
insert into banco (nome, icone_url) values ('Banco do Brasil', 'https://play-lh.googleusercontent.com/1-aNhsSPNqiVluwNGZar_7F5PbQ4u1zteuJ1jumnArhe8bfYHHaVwu4aVOF5-NAmLaA');
insert into banco (nome, icone_url) values ('Nubank', 'https://t.ctcdn.com.br/DIEw0gGtQl_GNhWXJwgrRmuGpIk=/i624750.png');
/*CONTA*/
insert into conta(saldo, tipo_conta_id, banco_id, usuario_id) values ('1412', '1', '3', '1');
insert into conta(saldo, tipo_conta_id, banco_id, usuario_id) values ('2000.50', '3', '1', '1');

/*CATEGORIAS*/
insert into categoria (nome, tipo_categoria, cor) values ('casa', 'despesa', '8755BC');             /*1*/
insert into categoria (nome, tipo_categoria, cor) values ('alimentacao', 'despesa', 'BA0707');      /*2*/
insert into categoria (nome, tipo_categoria, cor) values ('transporte', 'despesa', 'C19F13');       /*3*/
insert into categoria (nome, tipo_categoria, cor) values ('educacao', 'despesa', '33A841');         /*4*/
insert into categoria (nome, tipo_categoria, cor) values ('lazer', 'despesa', '57BEA1');            /*5*/
insert into categoria (nome, tipo_categoria, cor) values ('roupas', 'despesa', '5797BE');           /*6*/
insert into categoria (nome, tipo_categoria, cor) values ('saude', 'despesa', 'C92D78');            /*7*/
insert into categoria (nome, tipo_categoria, cor) values ('pagamentos', 'despesa', '818181');       /*8*/

insert into categoria (nome, tipo_categoria, cor) values ('salario', 'receita', '33A841');          /*9*/
insert into categoria (nome, tipo_categoria, cor) values ('investimentos', 'receita', '0775BA');    /*10*/
insert into categoria (nome, tipo_categoria, cor) values ('presente', 'receita', '8755BC');         /*11*/
insert into categoria (nome, tipo_categoria, cor) values ('renda extra', 'receita', 'CE601B');      /*12*/
insert into categoria (nome, tipo_categoria, cor) values ('outros', 'receita', 'BC7D18');           /*13*/

/*TRANSACAO*/
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('1000', '2024-12-01', 'venda bicicleta', 'receita', '12', '1');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('16.28', '2024-12-01', 'açaí', 'despesa', '5', '1');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('1412', '2024-12-05', 'salario', 'receita', '9', '2');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('123.55', '2024-12-10', 'conta luz', 'despesa', '1', '1');
insert into transacao (valor, data_transacao, descricao, tipo, categoria, conta_origem) values ('76.20', '2024-12-11', 'conta água', 'despesa', '1', '1');
