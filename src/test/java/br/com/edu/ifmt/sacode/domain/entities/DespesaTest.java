package br.com.edu.ifmt.sacode.domain.entities;

public class DespesaTest {
    public static void main(String[] args) {
        Despesa a = new Despesa("bananas", 10, "101010", "AAA",
         "Rogério", true, "null",
          null, 1);
        Despesa b = new Despesa("nutella", 50, "111010", "AAA",
         "Rogérinho", false, "Rogério",
          a, 2);
        
        System.out.println(a.toString());
        System.out.println(b.toString());
    }
}
