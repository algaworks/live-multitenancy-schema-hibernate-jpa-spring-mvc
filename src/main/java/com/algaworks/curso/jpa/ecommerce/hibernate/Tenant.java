package com.algaworks.curso.jpa.ecommerce.hibernate;

public class Tenant {

    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void setIdentificador(String tenantId) {
        tl.set(tenantId);
    }

    public static String getIdentificador() {
        return tl.get();
    }
}
