package br.com.tcc.achileydlacroix.reconautomation.shared;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TransactionalContext {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public <T> T persist(T objeto) {
        manager.persist(objeto);
        return objeto;
    }

    @Transactional
    public <T> T update(T objeto) {
        return manager.merge(objeto);
    }
}
