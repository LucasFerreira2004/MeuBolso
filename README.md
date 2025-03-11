# Meu Bolso

## Sobre o Projeto
O **Meu Bolso** é uma aplicação de gestão financeira pessoal que permite aos usuários controlar suas receitas e despesas de forma intuitiva. A plataforma oferece dashboards financeiros para análise detalhada, além de diversas funcionalidades para facilitar a organização das finanças.

## Back-End
O back-end da aplicação foi desenvolvido em **Spring Boot** e conta com uma arquitetura robusta para garantir a escalabilidade e segurança dos dados. Ele expõe uma API REST que possibilita a comunicação com o front-end, além de realizar toda a lógica de negócio e persistência dos dados.

### Tecnologias Utilizadas:
- **Spring Boot**
- **PostgreSQL** (em homologação e produção via Docker)
- **H2 Database** (para desenvolvimento local)
- **Docker e Docker Compose** (para conteinerização do back-end)
- **Oracle Cloud** (hospedagem da API e do banco de dados em produção)

## Front-End
O front-end foi desenvolvido utilizando **Electron** e **React**, permitindo que a aplicação seja executada como um software desktop multiplataforma. A interface é moderna e responsiva, garantindo uma boa experiência ao usuário.

## Funcionalidades:
- **Visualização de dashboards financeiros:**
  - Despesas totais por categoria
  - Receitas totais por categoria
  - Balanço de receitas e despesas por mês
  - Balanço de saldo
- **Gerenciamento de categorias** (criação e edição)
- **Cadastro e edição de transações:**
  - Despesas e receitas
  - Transações fixas
  - Transações parceladas
- **Orçamentos para categorias de despesa**
- **Perfil do usuário:**
  - Edição de informações
  - Upload de foto de perfil
- **Autenticação:**
  - Login e cadastro de usuários
- **Navegação entre meses** para visualizar previsões de saldo e outras métricas financeiras futuras ou passadas

## Demonstração
Veja a ferramenta em funcionamento no seguinte vídeo:
[https://youtu.be/kYQffXmHoBE]

Veja explicações sobre o códgio da ferramenta no seguinte vídeo:
[https://youtu.be/35dvyHKy-MQ]

## Documentações importantes
Acesse o seguinte repositório para encontrar documentações do projeto:
[https://github.com/LucasFerreira2004/PI-1/tree/main/Terceira%20Entrega]


## Padrões de projeto implementados
Acesse a seguinte pasta para entender alguns padrões de projetos utilizados em nosso back-end (strategies, observers, factories e etc) e como eles foram aplicados:
[https://github.com/LucasFerreira2004/PI-1/tree/main/Terceira%20Entrega/Padr%C3%B5es%20de%20projeto%20(PDS)]
