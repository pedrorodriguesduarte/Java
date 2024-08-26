/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.contabancaria;

/**
 *
 * @author Pedro
 */


public class ContaBancaria {
    private int numero;
    private String cpf;
    private double limite;
    private double saldo;

    private boolean validarNumeroDaConta(int num) {
        return num >= 10000 && num <= 99999;
    }

    public ContaBancaria(int numero, String cpf, double limite, double saldo) {
        if (validarNumeroDaConta(numero)) {
            this.numero = numero;
        } else {
            this.numero = 0;
        }
        this.cpf = cpf;
        this.limite = limite;
        this.saldo = saldo;
    }

    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
        } else if ((saldo + limite) >= valor) {
            saldo -= valor;
        } else {
            System.out.println("Valor indisponível para saque");
        }
    }

    public void depositar(double valor) {
        if (saldo >= 0) {
            saldo += valor;
        } else {
            double juros = saldo * (-1) * 0.03;
            saldo += valor - juros;
        }
    }

    public String getInfo() {
        return "Numero: " + numero + "\nCPF: " + cpf + "\nLimite: " + limite + "\nSaldo: " + saldo;
    }

    public static void main(String[] args) {
        ContaBancaria C = new ContaBancaria(50000, "102153443", 879.0, 9908.2);
        System.out.println(C.getInfo());
    }
}
