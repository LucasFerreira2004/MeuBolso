package com.projetointegrado.MeuBolso.globalExceptions;

import org.springframework.validation.BindingResult;

public class ValoresNaoPermitidosException extends RuntimeException {
    private final BindingResult bindingResult;

    public ValoresNaoPermitidosException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
