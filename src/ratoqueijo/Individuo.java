package ratoqueijo;

import java.util.Comparator;

public class Individuo implements Comparable<Individuo>{

    private int[] caminho = null;
    public int fitness;

    public Individuo(int[] caminho) {

        this.caminho = caminho;
    }

    public int[] getCaminho() {
        return caminho;
    }
    
    public void setCaminho(int[] caminho) {
        
        this.caminho = caminho;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int calculaFitness() {
        
        int aux;

        for (int i = 0; i < 9; i++) {

            aux = caminho[i] - caminho [i+1];
            fitness += Math.abs(aux);
            aux = 0;
        }

        fitness += 9;

        setFitness(fitness);

        //System.out.println(fitness);

        return fitness;

    }
    
    @Override
    public int compareTo(Individuo o) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public static class Comparators {

        public static Comparator<Individuo> FITNESS = new Comparator<Individuo> () {
            @Override
            public int compare(Individuo o1, Individuo  o2) {
                return o1.fitness - o2.fitness;
            }
        };
    }

}