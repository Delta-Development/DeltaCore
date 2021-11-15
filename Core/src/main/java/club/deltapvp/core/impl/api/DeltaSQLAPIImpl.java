/*
 *     DeltaCore is a Minecraft Java API Provider.
 *     Copyright (C) 2021 DeltaDevelopment
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 */

package club.deltapvp.core.impl.api;

import club.deltapvp.core.impl.sql.h2.H2Connector;
import club.deltapvp.core.impl.sql.mysql.MySQLConnector;
import club.deltapvp.deltacore.api.utilities.sql.DeltaSQLAPI;
import club.deltapvp.deltacore.api.utilities.sql.SQLConnector;

public class DeltaSQLAPIImpl extends DeltaSQLAPI {

    public DeltaSQLAPIImpl() {
        setInstance(this);
    }

    @Override
    public SQLConnector createConnection(String s, String s1, String s2, String s3, String s4) {
        return new MySQLConnector(s, s1, s2, s3, s4);
    }

    @Override
    public SQLConnector createConnection(String s) {
        return createConnection(s, "database");
    }

    @Override
    public SQLConnector createConnection(String s, String s1, String s2) {
        return new H2Connector(s, s1, s2);
    }

    @Override
    public SQLConnector createConnection(String s, String s1) {
        return new H2Connector(s, s1);
    }
}
