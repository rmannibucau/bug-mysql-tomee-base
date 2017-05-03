package com.github.rmannibucau.bugmysqltomee;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MySQLHandlerImpl implements DBController {
    @Resource(name = "mysqlInstance")
    private MySQL mySQL;

    @Override
    public void stop() {
        mySQL.stop();
    }
}
