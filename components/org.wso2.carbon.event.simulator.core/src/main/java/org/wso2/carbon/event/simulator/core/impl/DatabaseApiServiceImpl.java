package org.wso2.carbon.event.simulator.core.impl;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.PoolInitializationException;
import org.wso2.carbon.event.simulator.core.api.*;
import org.wso2.carbon.event.simulator.core.internal.generator.database.util.DatabaseConnector;
import org.wso2.carbon.event.simulator.core.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.wso2.carbon.event.simulator.core.api.NotFoundException;

import org.wso2.carbon.stream.processor.common.exception.ResponseMapper;

import javax.ws.rs.core.Response;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaMSF4JServerCodegen",
                            date = "2017-07-20T09:30:14.336Z")
public class DatabaseApiServiceImpl extends DatabaseApiService {
    @Override
    public Response getDatabaseTableColumns(DBConnectionModel connectionDetails, String tableName) throws NotFoundException {
        try {
            List<String> columnNames = DatabaseConnector.retrieveColumnNames(connectionDetails, tableName);
            return Response.status(Response.Status.OK)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                    .entity(columnNames)
                    .build();
        } catch (Throwable e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                    .entity(new ResponseMapper(Response.Status.BAD_REQUEST, e.getMessage()))
                    .build();
        }
    }

    @Override
    public Response getDatabaseTables(DBConnectionModel connectionDetails) throws NotFoundException {
        try {
            List<String> tableNames = DatabaseConnector.retrieveTableNames(connectionDetails);
            return Response.status(Response.Status.OK)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                    .entity(tableNames)
                    .build();
        } catch (Throwable e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                    .entity(new ResponseMapper(Response.Status.BAD_REQUEST, e.getMessage()))
                    .build();
        }
    }

    @Override
    public Response testDBConnection(DBConnectionModel connectionDetails) throws NotFoundException {
        try {
            HikariDataSource dataSource = DatabaseConnector.initializeDatasource(connectionDetails);
            Connection dbConnection = dataSource.getConnection();
            dbConnection.close();

            return Response.status(Response.Status.OK)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                    .entity(new ResponseMapper(Response.Status.OK, "Successfully connected to datasource '"
                            + connectionDetails.getDataSourceLocation() + "'."))
                    .build();
        } catch (PoolInitializationException | SQLException e) {
//            if any exception occurs, inform client that the database connection failed
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
                    .entity(new ResponseMapper(Response.Status.BAD_REQUEST, e.getMessage()))
                    .build();
        }
    }
}
