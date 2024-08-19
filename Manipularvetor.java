/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.manipularvetor;
import java.util.Scanner;
/**
 *
 * @author 1442616
 */
public class Manipularvetor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite uma sequência de caracteres:");
        String input = scanner.nextLine();

        int numeroPalavras = 0;
        int comecar = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                if (comecar < i) {
                    numeroPalavras++;
                }
                comecar = i + 1;
            }
        }

        if (comecar < input.length()) {
            numeroPalavras++;
        }

      
        System.out.println("Palavras na ordem invertida:");

       
        int end = input.length();
        for (int i = input.length() - 1; i >= 0; i--) {
            if (input.charAt(i) == ' ' || i == 0) {
                int comecarPalavra = (i == 0) ? i : i + 1;
                
                for (int j = comecarPalavra; j < end; j++) {
                    System.out.print(input.charAt(j));
                }
                if (i > 0) {
                    System.out.print(" ");
                }
                end = i; 
            }
        }

        System.out.println();
    }
}