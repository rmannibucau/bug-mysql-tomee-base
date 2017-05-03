package com.github.rmannibucau.bugmysqltomee;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.config.SchemaConfig;
import com.wix.mysql.distribution.Version;

import javax.annotation.PreDestroy;

public class MySQL {
    private EmbeddedMysql mysql;

    public MySQL() {
        mysql = EmbeddedMysql.anEmbeddedMysql(MysqldConfig
                .aMysqldConfig(Version.v5_7_17)
                .withPort(3706)
                .withUser("user", "password")
                .build())
                .start()
                .addSchema(SchemaConfig.aSchemaConfig("test").build());
    }

    @PreDestroy
    public synchronized void stop() {
        if (mysql == null) {
            return;
        }
        mysql.stop();
        mysql = null;
    }
}
