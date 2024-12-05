/*TIPO CONTA*/
insert into tipo_conta(tipo_conta) values ('conta_corrente');
insert into tipo_conta(tipo_conta) values ('carteira');
insert into tipo_conta(tipo_conta) values ('poupanca');
insert into tipo_conta(tipo_conta) values ('investimentos');
insert into tipo_conta(tipo_conta) values ('outro');

/*TRANSACAO*/
insert into transacao (valor, data_transacao, descricao, tipo) values ('1000', '2024-12-01', 'venda bicicleta', 'receita');
insert into transacao (valor, data_transacao, descricao, tipo) values ('16.28', '2024-12-01', 'açaí', 'despesa');
insert into transacao (valor, data_transacao, descricao, tipo) values ('1412', '2024-12-05', 'salario', 'receita');
insert into transacao (valor, data_transacao, descricao, tipo) values ('123.55', '2024-12-10', 'conta luz', 'despesa');
insert into transacao (valor, data_transacao, descricao, tipo) values ('76.20', '2024-12-11', 'conta água', 'despesa');