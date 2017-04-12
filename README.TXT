1) First generate your project directory using https://start.spring.io and then import into IntelliJ IDEA and off you go
2) select as gradle project
3) import or open project in intellij
4) Add below dependencies into build.gradle

    dependencies {
    	compile('org.springframework.boot:spring-boot-starter-data-jpa')
    	testCompile('org.springframework.boot:spring-boot-starter-test')

    	//liquibase jars
    	compile group: 'org.liquibase', name: 'liquibase-core', version: '3.5.3'
    	testCompile group: 'org.liquibase', name: 'liquibase-core', version: '3.5.3'
    	testCompile group: 'org.hsqldb', name: 'hsqldb', version: '2.2.8'

    	//h2 and mysql jars
    	compile group: 'com.h2database', name: 'h2'
    	compile group: 'mysql', name: 'mysql-connector-java', version: '5.1.6'

    	// tag::jetty[]
    	compile("org.springframework.boot:spring-boot-starter-web") {
    		exclude module: "spring-boot-starter-tomcat"
    	}
    	compile("org.springframework.boot:spring-boot-starter-jetty")
    	compile("org.springframework.boot:spring-boot-starter-jdbc")


    	// end::jetty[]
    	// tag::actuator[]
    	compile("org.springframework.boot:spring-boot-starter-actuator")
    	// end::actuator[]
    	testCompile("junit:junit")
    }

5) Add DB Properties into application.properties

     liquibase.enabled=true
     management.security.enabled=false

     spring.jpa.hibernate.ddl-auto=none

     #liquibase.change-log=classpath:db/changelog/db.changelog-master.xml

     spring.h2.console.enabled=true
     #spring.datasource.url=jdbc:mysql://localhost:3306/testliquibase
     #spring.datasource.username=root
     #spring.datasource.password=admin$123
     #spring.datasource.driver-class-name=com.mysql.jdbc.Driver


6) create db/changelog folder in resources

7)  Create db.changelog-master.yaml file or db.changelog-master.xml
      if you are using xml or different yaml file then spring boot won't detect automatically , so you have to add below property in application.properties

      liquibase.change-log=classpath:db/changelog/db.changelog-master.xml add create table script .

      Example :

        <databaseChangeLog
                xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog-ext
           http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd
           http://www.liquibase.org/xml/ns/dbchangelog
           http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.4.xsd">

            <changeSet author="Gopi" id="nisum_create_1">
                <createTable tableName="person">
                    <column name="address" type="varchar(255)"/>
                    <column name="name" type="varchar(255)"/>
                </createTable>
            </changeSet>

            <changeSet author="Gopi" id="nisum_insert_2">
                <insert dbms="h2, oracle"
                        tableName="person">
                    <column name="address" value="832 Livermore" type="varchar(255)"/>
                    <column name="name" value="Gopi" type="varchar(255)"/>
                </insert>
            </changeSet>

        </databaseChangeLog>


8) gradle bootRun

9) You can look at http://localhost:8080/liquibase to review the list of scripts.

10) This sample also enables the H2 console (at http://localhost:8080/h2-console) so that you can review the state of the database (the default jdbc url is jdbc:h2:mem:testdb).


NOTE :

To automatically run Liquibase database migrations on startup, add the org.liquibase:liquibase-core to your classpath.
The master change log is by default read from db/changelog/db.changelog-master.yaml but can be set using liquibase.change-log. In addition to YAML, Liquibase also supports JSON, XML, and SQL change log formats.
By default Liquibase will autowire the (@Primary) DataSource in your context and use that for migrations. If you like to use a different DataSource you can create one and mark its @Bean as @LiquibaseDataSource - if you do that remember to create another one and mark it as @Primary if you want two data sources. Or you can use Liquibase’s native DataSource by setting liquibase.[url,user,password] in external properties.



