package com.algaworks.curso.jpa.ecommerce.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class SchemaCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return Tenant.getIdentificador();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
