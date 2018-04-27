/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.neo4jtest;

import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author PraveenKumar
 */
public class DBCreator {
    //Can alos use existing databse. Pass the exisiting database path.
    public static final String DATABASENAME = "database";
    public static GraphDatabaseService execute(){
        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(
            new File(DATABASENAME));
        return graphDb;
    }
}
