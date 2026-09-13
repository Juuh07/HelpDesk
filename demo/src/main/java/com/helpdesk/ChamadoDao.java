package com.helpdesk;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class ChamadoDao {
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public ChamadoDao() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("helpdesk");
        collection = database.getCollection("chamados");
    }

    public void create(Chamado chamado) {
        Document document = new Document();

        document.append("id", chamado.getId());
        document.append("titulo", chamado.getTitulo());
        document.append("descricao", chamado.getDescricao());
        document.append("categoria", chamado.getCategoria());
        document.append("prioridade", chamado.getPrioridade());
        document.append("status", chamado.isStatus());
        document.append("nomeSolicitante", chamado.getNomeSolicitante());

        collection.insertOne(document);
    }

    public void read() {
        for (Document document : collection.find()) {
            System.out.println("ID: " + document.getInteger("id"));
            System.out.println("Título: " + document.getString("titulo"));
            System.out.println("Descrição: " + document.getString("descricao"));
            System.out.println("Categoria: " + document.getString("categoria"));
            System.out.println("Prioridade: " + document.getString("prioridade"));
            System.out.println("Status: " + document.getBoolean("status"));
            System.out.println("Nome do solicitante: " + document.getString("nomeSolicitante"));
        }
    }

    public void update(Chamado chamado) {
        Document filtro = new Document("id", chamado.getId());

        Document atualizacao = new Document("$set",
            new Document("titulo", chamado.getTitulo())
                .append("descricao", chamado.getDescricao())
                .append("categoria", chamado.getCategoria())
                .append("prioridade", chamado.getPrioridade())
                .append("status", chamado.isStatus())
                .append("nomeSolicitante", chamado.getNomeSolicitante())
        );

        collection.updateOne(filtro, atualizacao);
    }

    public void delete(int id) { 
        collection.deleteOne(new Document("id", id));

    }
}

