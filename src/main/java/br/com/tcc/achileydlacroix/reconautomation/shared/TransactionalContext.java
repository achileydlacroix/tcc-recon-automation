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
    public <T> T persist(T object) {
        manager.persist(object);
        return object;
    }

    @Transactional
    public <T> T update(T object) {
        return manager.merge(object);
    }

    @Transactional
    public <T> void delete(T object) {
        manager.remove(object);
    }
}
