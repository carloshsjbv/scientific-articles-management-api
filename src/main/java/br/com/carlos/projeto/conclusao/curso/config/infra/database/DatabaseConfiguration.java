package br.com.carlos.projeto.conclusao.curso.config.infra.database;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Classe de configuração de banco de dados. Por hora não está sendo mais
 * utilizada.
 *
 * As configurações de banco de dados estão sendo feitas no arquivo
 * application.properties
 *
 * @author Carlos H
 */
//@Configuration
public class DatabaseConfiguration {

//    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521/XE");
        dataSource.setUsername("PROJETO_CONCLUSAO_DE_CURSO1");
        dataSource.setPassword("violaopreto");

        return dataSource;

    }

//    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.ORACLE);
        // Mostra operações efetuadas no console.
        adapter.setShowSql(true);
        // Permite que o Hibernate crie tabelas automaticamente.
//        adapter.setGenerateDdl(true);
//        adapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
        adapter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");
        adapter.setPrepareConnection(true);

        return adapter;
    }

}
