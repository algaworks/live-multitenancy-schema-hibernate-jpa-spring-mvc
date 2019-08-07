package com.algaworks.curso.jpa.ecommerce.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class SchemaMultiTenantConnectionProvider implements
        MultiTenantConnectionProvider, ServiceRegistryAwareService {

    private ConnectionProvider connectionProvider = null;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return connectionProvider.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connectionProvider.closeConnection(connection);
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();

        try {
            connection.createStatement().execute("use " + tenantIdentifier);
        } catch (SQLException e) {
            throw new HibernateException("Não foi possível alterar " +
                    "para o schema " + tenantIdentifier + ".", e);
        }

        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        Map<String, String> settings = serviceRegistry
                .getService(ConfigurationService.class)
                .getSettings();

        connectionProvider = new DriverManagerConnectionProviderImpl();
        ((DriverManagerConnectionProviderImpl) connectionProvider)
                .injectServices(serviceRegistry);
        ((DriverManagerConnectionProviderImpl) connectionProvider)
                .configure(settings);
    }
}
