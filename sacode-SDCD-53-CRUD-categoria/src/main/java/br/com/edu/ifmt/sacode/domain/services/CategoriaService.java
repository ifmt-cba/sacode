package br.com.edu.ifmt.sacode.domain.services;

import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private List<String> categorias;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    // Create - Adiciona uma categoria
    public void adicionarCategoria(String categoria) {
        categorias.add(categoria);
    }

    // Read - Obtem todas as categorias
    public List<String> obterCategorias() {
        return categorias;
    }

    // Update - Atualiza uma categoria
    public void atualizarCategoria(int indice, String novaCategoria) {
        if (indice >= 0 && indice < categorias.size()) {
            categorias.set(indice, novaCategoria);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    // Delete - Remove uma categoria
    public void removerCategoria(int indice) {
        if (indice >= 0 && indice < categorias.size()) {
            categorias.remove(indice);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public static void main(String[] args) {
        CategoriaService crud = new CategoriaService();

        // Adiciona categorias
        crud.adicionarCategoria("Categoria 1");
        crud.adicionarCategoria("Categoria 2");
        crud.adicionarCategoria("Categoria 3");

        // Lista categorias
        System.out.println("Categorias:");
        List<String> listaCategorias = crud.obterCategorias();
        for (String categoria : listaCategorias) {
            System.out.println(categoria);
        }

        // Atualiza categoria
        crud.atualizarCategoria(1, "Nova Categoria");

        // Lista categorias atualizadas
        System.out.println("\n Categorias Atualizadas:");
        listaCategorias = crud.obterCategorias();
        for (String categoria : listaCategorias) {
            System.out.println(categoria);
        }

        // Remove categoria
        crud.removerCategoria(0);

        // Lista categorias após remoção
        System.out.println("\n Categorias Após Remoção:");
        listaCategorias = crud.obterCategorias();
        for (String categoria : listaCategorias) {
            System.out.println(categoria);
        }
    }
}