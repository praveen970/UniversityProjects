/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.neo4jtest;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;

/**
 *
 * @author PraveenKumar
 */
public class GenerateData {
    Faker faker;
    GraphDatabaseService graphDB;
    Random rand;
    final int TOTAL_USERS = 2000;
    final int TOTAL_BOOKS = 200;
    final int TOTAL_STATES = 50;
    final String[] GENDER = {"Male", "Female"};
    public GenerateData(GraphDatabaseService graphDB) {
        this.graphDB = graphDB;
        faker = new Faker();
        rand = new Random();
    }
    
    public void generateData(){
        Node nodeUser,nodeAddress,nodeBook;
        graphDB.beginTx();
        for(int i=0;i<TOTAL_USERS;i++){
            nodeUser = graphDB.createNode(NodeTypes.USERS);
            nodeUser.setProperty(UserProperties.FIRSTNAME,faker.name().firstName());
            nodeUser.setProperty(UserProperties.LASTNAME, faker.name().lastName());
            nodeUser.setProperty(UserProperties.CONTACT, faker.phoneNumber().cellPhone());
            nodeUser.setProperty(UserProperties.GENDER, GENDER[rand.nextInt(2)]); 
        }
        for(int i=0;i<TOTAL_STATES;i++){
            nodeAddress = graphDB.createNode(NodeTypes.ADDRESS);
            nodeAddress.setProperty(AddressProperties.STATE, faker.address().state());
        }
        for(int i=0;i<TOTAL_BOOKS;i++){
            nodeBook = graphDB.createNode(NodeTypes.BOOKS);
            nodeBook.setProperty(BookProperties.TITLE, faker.book().title());
        }
        createKnowsRelationship();
        createStaysRelationship();
        createBooksRelationship();
        
    }
    public void createKnowsRelationship(){
        ResourceIterator<Node> users = graphDB.findNodes( NodeTypes.USERS);
        ArrayList<Node> usersList = new ArrayList<>();
        while( users.hasNext() )
        {
            Node user = users.next();
            usersList.add(user);            
        }
        int size = usersList.size()-1;
        while(size>0){
            int k = rand.nextInt(usersList.size()/25);
            for(int i =0;i<k;i++){
                usersList.get(size).createRelationshipTo(usersList.get(rand.nextInt(usersList.size()-1)), RelationTypes.KNOWS);
            }
            size--;
        }   
    }
    public void createStaysRelationship(){
        ResourceIterator<Node> users = graphDB.findNodes( NodeTypes.USERS);
        ResourceIterator<Node> addresses = graphDB.findNodes( NodeTypes.ADDRESS);
        ArrayList<Node> usersList = new ArrayList<>();
        while( users.hasNext() )
        {
            Node user = users.next();
            usersList.add(user);            
        }
        ArrayList<Node> addressesList = new ArrayList<>();
        while( addresses.hasNext() )
        {
            Node address = addresses.next();
            addressesList.add(address);            
        }
        for(int i =0;i<usersList.size()-1;i++){
            usersList.get(i).createRelationshipTo(addressesList.get(rand.nextInt(50)), RelationTypes.STAYS_AT);
        }
    }
    public void createBooksRelationship(){
        ResourceIterator<Node> users = graphDB.findNodes( NodeTypes.USERS);
        ResourceIterator<Node> books = graphDB.findNodes( NodeTypes.BOOKS);
        ArrayList<Node> usersList = new ArrayList<>();
        while( users.hasNext() )
        {
            Node user = users.next();
            usersList.add(user);            
        }
        ArrayList<Node> booksList = new ArrayList<>();
        while( books.hasNext() )
        {
            Node book = books.next();
            booksList.add(book);            
        }
        int size = usersList.size()-1;
        while(size>0){
            int k = rand.nextInt(6);
            for(int i =0;i<k;i++){
                usersList.get(size).createRelationshipTo(booksList.get(rand.nextInt(booksList.size()-1)), RelationTypes.READS);
            }
            size--;
        }
    }
    public static enum NodeTypes implements Label{
        USERS,
        ADDRESS,
        BOOKS;
    }
    public static enum RelationTypes implements RelationshipType{
        STAYS_AT,
        KNOWS,
        READS;
    }
    public static class UserProperties {
        public static final String FIRSTNAME = "Firstname";
        public static final String LASTNAME = "Lastname";
        public static final String GENDER = "Gender";
        public static final String CONTACT = "Contact";
    }
    public static class AddressProperties {
        public static final String STREET_ADDRESS = "StreetAddress";
        public static final String STATE = "State";
    }
    public static class BookProperties {
        public static final String TITLE = "Title";
        public static final String AUTHOR = "Author";
    }
}
