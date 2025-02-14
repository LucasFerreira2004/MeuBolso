package com.projetointegrado.MeuBolso.config;

import com.projetointegrado.MeuBolso.ArmazenamentoImagens.CloudinaryStorageService;
import com.projetointegrado.MeuBolso.ArmazenamentoImagens.IStorageService;
import com.projetointegrado.MeuBolso.autorizacao.token.ITokenService;
import com.projetointegrado.MeuBolso.autorizacao.token.TokenService;
import com.projetointegrado.MeuBolso.banco.BancoService;
import com.projetointegrado.MeuBolso.banco.IBancoService;
import com.projetointegrado.MeuBolso.categoria.CategoriaService;
import com.projetointegrado.MeuBolso.categoria.ICategoriaService;
import com.projetointegrado.MeuBolso.conta.ContaService;
import com.projetointegrado.MeuBolso.conta.IContaService;
import com.projetointegrado.MeuBolso.tipoConta.ITipoContaService;
import com.projetointegrado.MeuBolso.tipoConta.TipoContaService;
import com.projetointegrado.MeuBolso.transacao.ITransacaoService;
import com.projetointegrado.MeuBolso.transacao.TransacaoService;
import com.projetointegrado.MeuBolso.transacaoRecorrente.ITransacaoRecorrenteService;
import com.projetointegrado.MeuBolso.transacaoRecorrente.TransacaoRecorrenteService;
import com.projetointegrado.MeuBolso.usuario.IUsuarioService;
import com.projetointegrado.MeuBolso.usuario.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjecaoDependeciaConfig {
    @Bean
    public IBancoService BancoService() {
        return new BancoService();
    }

    @Bean
    public ITokenService TokenService() {
        return new TokenService();
    }

    @Bean
    public ICategoriaService CategoriaService() {
        return new CategoriaService();
    }

    @Bean
    public IContaService ContaService() {
        return new ContaService();
    }

    @Bean
    public ITipoContaService TipoContaService() {
        return new TipoContaService();
    }

    @Bean
    public ITransacaoService TransacaoService() {
        return new TransacaoService();
    }
    
    @Bean
    public IUsuarioService UsuarioService() {
        return new UsuarioService();
    }

    @Bean
    public ITransacaoRecorrenteService TransacaoFixaService(){return new TransacaoRecorrenteService();}

    @Bean
    public IStorageService StorageService() {return new CloudinaryStorageService();}
}
