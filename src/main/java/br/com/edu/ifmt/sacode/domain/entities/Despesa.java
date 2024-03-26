package br.com.edu.ifmt.sacode.domain.entities;
import java.util.Date;

//import org.json; //.JSONObject;

//import br.edu.ifmt.springbootcleanarch.domain.entities.vo.Email;
//import br.edu.ifmt.springbootcleanarch.domain.entities.vo.Password;
//import br.edu.ifmt.springbootcleanarch.domain.entities.vo.Username;

public class Despesa {
    
    private String descricao;
    private float valor;
    private Date date;
    private String usuario;
    private String autorDespesa;
    private boolean fixa;
    private String financiadorDespesa;
    private Despesa despesaOriginal;
    private int numParcela;


    public Despesa() {
        this.descricao = null;
        this.descricao = null;
        this.valor = 0;
        this.date = null;
        this.usuario = null;
        this.autorDespesa = null;
        this.fixa = false;
        this.financiadorDespesa = null;
        this.despesaOriginal = new Despesa();
        this.numParcela = 0;
    }
  
    public String getDescricao() {return descricao;}
    
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public float getValor() {return valor;}

    public void setValor(float valor) {this.valor = valor;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public String getUsuario() {return usuario;}

    public void setUsuario(String usuario) {this.usuario = usuario; }

    public String getAutorDespesa() {return autorDespesa;}

    public void setAutorDespesa(String autorDespesa) {this.autorDespesa = autorDespesa;}

    public boolean isFixa() {return fixa;}

    public void setFixa(boolean fixa) {this.fixa = fixa;}

    public String getFinanciadorDespesa() {return financiadorDespesa;}

    public void setFinanciadorDespesa(String financiadorDespesa) {this.financiadorDespesa = financiadorDespesa;}

    public Despesa getDespesaOriginal() {return despesaOriginal;}

    public void setDespesaOriginal(Despesa despesaOriginal) {this.despesaOriginal = despesaOriginal;}

    public int getNumParcela() {return numParcela;}

    public void setNumParcela(int numParcela) {this.numParcela = numParcela;}   

    
    @Override
    public String toString() {
        return String.format( """
                {
                    "descricao":"%s",                   
                    "valor":%s,
                    "date":"%s",
                    "usuario":"%s",
                    "autoDespesa":"%s",
                    "fixa": %s,
                    "financiadorDespesa":"%s",
                    "despesaOriginal":%s,
                    "numParcela":%s
                }
                """,
            this.descricao.toString(),
            Float.toString(this.valor),
            this.date.toString(),
            this.usuario.toString(),
            this.autorDespesa.toString(),
            Boolean.toString(this.fixa),
            this.financiadorDespesa.toString(),
            n(this.despesaOriginal),
            Integer.toString(this.numParcela)
        );
    }


    String n(Despesa a){
        if(a == null){
            return "null";
        }else{
            return new String("\n"+a.toString()); 
        }
    }
}