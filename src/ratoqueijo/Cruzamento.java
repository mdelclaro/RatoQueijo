package ratoqueijo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Cruzamento {
    
    ArrayList<Individuo> popIntermediaria;
    ArrayList<Individuo> popAuxiliar = new ArrayList<>();
    int taxa;
    
    public Cruzamento(ArrayList<Individuo> popIntermediaria, int taxa) {
        
        this.popIntermediaria = popIntermediaria;
        this.taxa = taxa;
        
        /*System.out.println("-------------- Original ----------------");
        
        for (int i = 0; i < popIntermediaria.size(); i++) {
            
            int fit = popIntermediaria.get(i).getFitness();
            System.out.println("INDIVIDUO " + i + " Fitness = " + fit);
            
        }*/
        
        popAuxiliar.addAll(popIntermediaria);
        
        Random random = new Random();
        
        int tamanho = popIntermediaria.size() / 2;

        for (int x = 0; x < tamanho ; x++) {
            
            int[] aux = new int[2];
            
            aux[0] = random.nextInt(popIntermediaria.size());
            aux[1] = random.nextInt(popIntermediaria.size());
            
            int corte = random.nextInt(7) + 1;
            
            int[] pai1 = new int[10];
            pai1 = popIntermediaria.get(aux[0]).getCaminho();
            
            int[] pai2 = new int[10];
            pai2 = popIntermediaria.get(aux[1]).getCaminho();
            
            int[] filho1 = new int[10];
            int[] filho2 = new int[10];
            
            for(int i = 0; i <= corte; i++) {
                
                filho1[i] = pai1[i];
                filho2[i] = pai2[i];               
                
            }
            
            for(int j = corte; j < 10; j++) {
                
                filho2[j] = pai1[j];
                filho1[j] = pai2[j];               
                
            }    
            
            Individuo f1 = new Individuo(filho1);
            popAuxiliar.add(f1);
            
            Individuo f2 = new Individuo(filho2);
            popAuxiliar.add(f2);
            
            
            
        }
        
    }
    
    public void Mutacao(int numeroGeracao, int tamanhoPopulacao) {
        
        int genes = popAuxiliar.size() * 8 * (taxa/100);
        
        Random random = new Random();
        
        for(int i = 0; i < genes; i++) {
        
            int aux = random.nextInt(popAuxiliar.size());            
            int mutacao = random.nextInt(7) + 1;           
            int valor = random.nextInt(9) + 1;
            
            int[] mutado = new int[10];     
            mutado = popAuxiliar.get(aux).getCaminho();
            mutado[mutacao] = valor;
            popAuxiliar.get(aux).setCaminho(mutado);

        }
        
        for(int j = popIntermediaria.size() -1; j < popAuxiliar.size(); j++) {
        
            popAuxiliar.get(j).calculaFitness();
        
        }
        
        /*System.out.println("-------------- Auxiliar com Filhos e Fitness ----------------");
        
        for (int i = 0; i < popAuxiliar.size(); i++) {
            
            int fit = popAuxiliar.get(i).getFitness();
            System.out.println("INDIVIDUO " + i + " Fitness = " + fit);
            
        } */
        
        ArrayList ordenadoIndividuos = new ArrayList<>();
        ordenadoIndividuos = ordena(popAuxiliar);

        for (int i = (tamanhoPopulacao - 1); i >= popIntermediaria.size(); i--) {
            popAuxiliar.remove(i);
        }
        
        //System.out.println("-------------- Auxiliar Mutada " + numeroGeracao + "----------------");
        
        for (int i = 0; i < tamanhoPopulacao; i++) {
            
            int fit = popAuxiliar.get(i).getFitness();
            
           // System.out.println("INDIVIDUO " + i + " Fitness = " + fit);
            
        }
        
    }
    
    public ArrayList<Individuo> ordena(ArrayList<Individuo> ordenadoIndividuos) {
        Collections.sort(ordenadoIndividuos, Individuo.Comparators.FITNESS);
        return ordenadoIndividuos;
    }
   
}
