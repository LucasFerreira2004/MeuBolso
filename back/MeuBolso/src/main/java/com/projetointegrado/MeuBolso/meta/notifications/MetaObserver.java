package com.projetointegrado.MeuBolso.meta.notifications;

import com.projetointegrado.MeuBolso.meta.Meta;

// Interface Observer
public interface MetaObserver {
    void atualizar(Meta meta, int threshold);
}
