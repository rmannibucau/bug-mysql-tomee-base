package com.github.rmannibucau.bugmysqltomee;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(Arquillian.class)
public class TheTest {
    @Deployment(testable = false)
    public static Archive<?> app() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(TheEntity.class, TheService.class, TheWebService.class, MySQLHandlerImpl.class, DBController.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new StringAsset("<resources>\n" +
                        "<Resource id=\"mysqlInstance\" class-name=\"com.github.rmannibucau.bugmysqltomee.MySQL\" />\n" +
                        "<Resource id=\"mysql\" type=\"DataSource\" depends-on=\"mysqlInstance\">\n" +
                        "JdbcUrl = jdbc:mysql://localhost:3706/test\n" +
                        "JdbcDriver = com.mysql.jdbc.Driver\n" +
                        "Username = user\n" +
                        "Password = password\n" +
                        "</Resource>\n" +
                        "</resources>"), "resources.xml")
                .addAsWebInfResource(new StringAsset(Descriptors.create(PersistenceDescriptor.class)
                        .getOrCreatePersistenceUnit()
                        .name("test")
                        .jtaDataSource("mysql")
                        .clazz(TheEntity.class.getName()).excludeUnlistedClasses(true)
                        .getOrCreateProperties()
                        .createProperty().name("openjpa.jdbc.SynchronizeMappings").value("buildSchema(ForeignKeys=true)").up()
                        .up()
                        .up()
                        .exportAsString()), "persistence.xml");
    }

    @ArquillianResource
    private URL base;

    @Test
    public void run() throws MalformedURLException {
        final TheEntity theEntity = Service.create(
                new URL(base.toExternalForm() + "webservices/TheService?wsdl"),
                new QName("http://bugmysqltomee.rmannibucau.github.com/", "TheServiceService"))
                .getPort(TheWebService.class)
                .newInstance();
        System.out.println(theEntity);
    }
}
