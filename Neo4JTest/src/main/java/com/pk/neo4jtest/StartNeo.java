/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.neo4jtest;

import com.github.javafaker.Faker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.traversal.Evaluators;

/**
 *
 * @author PraveenKumar
 */
public class StartNeo {
    GraphDatabaseService graphDB;
    GenerateData data;
    Faker faker;
    Random rand = new Random();
    public static void main(String args[]){
        StartNeo start = new StartNeo();
        start.initialize();
    }
    public void initialize(){
        graphDB = DBCreator.execute();
        data = new GenerateData(graphDB);
        data.generateData();
        faker = new Faker();
        
        startQueries();
    }
    public void startQueries(){
        query1();
        query2();
        query3();
        query4();
        query5();
        traversalKnowsAPI();
        traversalReadsAPI();        
    }
    
    
    public void query1(){
//      *******To Print all users that are known to a user.******
        ResourceIterator<Node> users = graphDB.findNodes( GenerateData.NodeTypes.USERS);  
        while(users.hasNext()){
            Node user = users.next();
            System.out.println(user.getProperty( GenerateData.UserProperties.FIRSTNAME ) +" Knows");
            for(Relationship relationships:user.getRelationships(GenerateData.RelationTypes.KNOWS)){
                Node knownPerson = relationships.getOtherNode(user);
                System.out.println("--"+knownPerson.getProperty(GenerateData.UserProperties.FIRSTNAME));
            }
        }
        System.out.println("********************************");
    }
    public void query2(){
// List of users state-wise.      
        ResourceIterator<Node> users = graphDB.findNodes(GenerateData.NodeTypes.USERS);
        ResourceIterator<Node> addresses = graphDB.findNodes(GenerateData.NodeTypes.ADDRESS);
        Node address;
           while(addresses.hasNext()){
               address = addresses.next();
               System.out.println(address.getProperty(GenerateData.AddressProperties.STATE));
               for(Relationship relationships:address.getRelationships(GenerateData.RelationTypes.STAYS_AT)){
                Node knownPerson = relationships.getOtherNode(address);
                System.out.println("--"+knownPerson.getProperty(GenerateData.UserProperties.FIRSTNAME));
            }
               
           }
           System.out.println("********************************");
    }
    public void query3(){
// List of books read by each user        
        ResourceIterator<Node> users = graphDB.findNodes( GenerateData.NodeTypes.USERS);        
            Node user;
            while(users.hasNext()){
                user = users.next();
            System.out.println("USER is: "+user.getProperty( GenerateData.UserProperties.FIRSTNAME ) );
            for(Relationship relationships:user.getRelationships(GenerateData.RelationTypes.READS)){
                Node readsBook = relationships.getOtherNode(user);
                System.out.println("Book Title: "+readsBook.getProperty(GenerateData.BookProperties.TITLE));
            }
            }
            System.out.println("********************************");
    }
    public void query4(){
// Name of users and total number of users(Female and Male) reading each book         
        ResourceIterator<Node> books = graphDB.findNodes( GenerateData.NodeTypes.BOOKS);
        Node book;
        int total = 0,fTotal=0, mTotal=0;
        while(books.hasNext()){
            book = books.next();
            System.out.println("BOOK is: "+book.getProperty( GenerateData.BookProperties.TITLE ) );
            for(Relationship relationships:book.getRelationships(GenerateData.RelationTypes.READS)){
                total++;
                Node user = relationships.getOtherNode(book);
                
                if(user.getProperty(GenerateData.UserProperties.GENDER).equals(data.GENDER[1])){
                    fTotal++;
                }else{
                    mTotal++;
                }
                System.out.println("User: "+user.getProperty(GenerateData.UserProperties.FIRSTNAME));
            }
            System.out.println("Total Female Readers:"+fTotal);
            System.out.println("Total Male Reader:"+mTotal);
            System.out.println("Total Readers:"+total);
            total = 0;
            mTotal = 0;
            fTotal = 0;
        }
        System.out.println("********************************");
    }
    public void query5(){
        //Add new Author property for Book nodes
        ResourceIterator<Node> books = graphDB.findNodes( GenerateData.NodeTypes.BOOKS);  
        while(books.hasNext()){
            Node book = books.next();
            book.setProperty(GenerateData.BookProperties.AUTHOR,faker.book().author() );
        }
        System.out.println("********************************");
    }
    public void traversalKnowsAPI(){
    // List of friends of users and thier friends at depth of 3.
        ResourceIterator<Node> users = graphDB.findNodes( GenerateData.NodeTypes.USERS);
        while(users.hasNext()){
        Node node = users.next();
        System.out.println("User: "+node.getProperty(GenerateData.UserProperties.FIRSTNAME));
        for ( Path position : graphDB.traversalDescription()
        .depthFirst()
        .relationships( GenerateData.RelationTypes.KNOWS,Direction.INCOMING )
        .evaluator( Evaluators.toDepth(3) )
        .traverse( node ) )
{
    System.out.println(render(position,GenerateData.UserProperties.FIRSTNAME,GenerateData.UserProperties.FIRSTNAME,false));
}
        }
        System.out.println("********************************");
    }
    public void traversalReadsAPI(){
        //List of books of users who have atleast one common book with specific user
        ResourceIterator<Node> users = graphDB.findNodes( GenerateData.NodeTypes.USERS);
        while(users.hasNext()){
        Node node = users.next();
            System.out.println("User:"+node.getProperty(GenerateData.UserProperties.FIRSTNAME));
        int i =1;
        for ( Path position : graphDB.traversalDescription()
        .depthFirst()
        .relationships( GenerateData.RelationTypes.READS,Direction.BOTH )
        .evaluator( Evaluators.toDepth(3) )
        .traverse( node ) )
{
        System.out.println(render(position,GenerateData.UserProperties.FIRSTNAME,"Title",true));
}
        }
        System.out.println("********************************");
    }
    
    public String render(Path path, String prop, String prop2,boolean flag) {
   StringBuilder sb=new StringBuilder();
   int i=0;
   for (PropertyContainer pc : path) {
       i=i%2;
      if (pc instanceof Node) sb.append(toString((Node)pc,prop,prop2));
      else {if(flag){sb.append(toString((Relationship)pc,i));i++;}else{sb.append(toString((Relationship)pc,1));}}
   }
   return sb.toString();
}
    public String toString(Node n, String prop, String prop2) { 
        try{
            return "("+n.getProperty(prop)+")"; 
        }catch(Exception e){
            return "("+n.getProperty(prop2)+")";
        }
    }
    public String toString(Relationship r,int i) { 
        if(i==0){
            return "-["+r.getType().name()+"]->";
        }else{
            return "<-["+r.getType().name()+"]-";
        }
         
        
    }
}
