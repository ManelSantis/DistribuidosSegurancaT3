package Classes;

import java.io.Serializable;

import Enums.CarTypes;

public class Car implements Serializable{
    private static final long serialVersionUID = 1L;

    private int renavan;
    private String nome;
    private int anoFabricacao;
    private double preco;
    private String type;

    public Car(int renavan, String nome, int anoFabricacao, double preco, String type) {
        this.renavan = renavan;
        this.nome = nome;
        this.anoFabricacao = anoFabricacao;
        this.preco = preco;
        this.type = type;
    }

    public Car() {
        //TODO Auto-generated constructor stub
    }

    public int getRenavan() {
        return renavan;
    }

    public void setRenavan(int renavan) {
        this.renavan = renavan;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "Carro: " + nome + "\nRenavan: " + renavan + "\nCategoria: " + type + "\nAno de Fabricacao: " + anoFabricacao + "\nTipo: " + type + "\nPreco: R$" + preco;
    }

}

