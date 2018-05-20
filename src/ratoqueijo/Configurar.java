package ratoqueijo;

//import com.sun.xml.internal.ws.policy.spi.PolicyAssertionValidator;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Configurar {

    private String posicaoRato;
    private String posicaoQueijo;
    private String tamanhoPopulacao;
    private String taxaCruzamento;
    private String taxaMutacao;
    private String mecanismoSelecao;
    private String geracoes;
    public int quantidadeCruzamento;
    ArrayList<Individuo> individuos = new ArrayList<>();
    ArrayList<Individuo> individuosSelecionados;

    public Configurar(String posicaoRato, String posicaoQueijo, String tamanhoPopulacao, String taxaCruzamento, String taxaMutacao, String mecanismoSelecao, String geracoes) {

        this.posicaoRato = posicaoRato;
        this.posicaoQueijo = posicaoQueijo;
        this.tamanhoPopulacao = tamanhoPopulacao;
        this.taxaCruzamento = taxaCruzamento;
        this.taxaMutacao = taxaMutacao;
        this.mecanismoSelecao = mecanismoSelecao;
        this.geracoes = geracoes;

    }

    public void geraPopulacao() {

        int[] aux;

        int tamanho = Integer.parseInt(tamanhoPopulacao);

        System.out.println("\n\nTAMANHO: " + tamanho);

        for (int i = 0; i < tamanho; i++) {

            aux = new int[10];

            aux[0] = Integer.parseInt(posicaoRato);
            aux[9] = Integer.parseInt(posicaoQueijo);

            for (int j = 1; j < 9; j++) {

                Random rand = new Random();
                aux[j] = rand.nextInt(7) + 1;
                //System.out.println("Gerou: " + aux[j]);
            }

            Individuo individuo = new Individuo(aux);

            individuo.calculaFitness();

            individuos.add(individuo); //add no arraylist
        }

        for (int i = 0; i < tamanho; i++) {

            int[] tmp = individuos.get(i).getCaminho();
            int fit = individuos.get(i).getFitness();
            //System.out.println("INDIVIDUO " + i + " Fitness = " + fit);

            for (int x = 0; x < 10; x++) {

                //System.out.println(tmp[x]);
            }
        }

        System.out.println("Posição Rato: " + posicaoRato);
        System.out.println("Posição Queijo: " + posicaoQueijo);
        System.out.println("População: " + tamanhoPopulacao);
        System.out.println("Cruzamento: " + taxaCruzamento);
        System.out.println("Mutação: " + taxaMutacao);
        System.out.println("Seleção: " + mecanismoSelecao);

    }

    public void calculaQuantidadeCruzamento() {

        int quantidade = (Integer.parseInt(tamanhoPopulacao) * Integer.parseInt(taxaCruzamento)) / 100;

       // System.out.println(quantidade);

        quantidadeCruzamento = quantidade;

    }

    public ArrayList<Individuo> ordena(ArrayList<Individuo> ordenadoIndividuos) {
        Collections.sort(ordenadoIndividuos, Individuo.Comparators.FITNESS);
        return ordenadoIndividuos;
    }

    public void rank() {

        int geracao = 0;

        do {

            ArrayList<Individuo> ordenadoIndividuos = new ArrayList<Individuo>();
            ordenadoIndividuos = ordena(individuos);

            //System.out.println(individuos);
            //System.out.println(ordenadoIndividuos);
            for (int i = (ordenadoIndividuos.size() - 1); i >= quantidadeCruzamento; i--) {
                ordenadoIndividuos.remove(i);
            }

            individuosSelecionados = new ArrayList<>();
            individuosSelecionados.addAll(ordenadoIndividuos);

            Cruzamento cruzamento = new Cruzamento(individuosSelecionados, Integer.parseInt(taxaMutacao));
            cruzamento.Mutacao(geracao, Integer.parseInt(tamanhoPopulacao));
            individuos.clear();
            individuos.addAll(cruzamento.popAuxiliar);

            geracao++;

            /*System.out.println("-------------- Com Rank ----------------");
            for (int i = 0; i < ordenadoIndividuos.size(); i++) {

                int fit = ordenadoIndividuos.get(i).getFitness();
                System.out.println("INDIVIDUO " + i + " Fitness = " + fit);

            }

            System.out.println("-------------- Mutados ----------------");
            for (int i = 0; i < individuos.size(); i++) {

                int fit = individuos.get(i).getFitness();
                System.out.println("INDIVIDUO " + i + " Fitness = " + fit);

            }*/

        } while ((individuos.get(0).getFitness() > 9 + Math.abs(Integer.parseInt(posicaoRato) - Integer.parseInt(posicaoQueijo))) && geracao < Integer.parseInt(geracoes));

    }

    public void torneio() {

        int geracao = 0;

        do {

            ArrayList<Individuo> torneioWin = new ArrayList<>();

            Random random = new Random();

            while (torneioWin.size() < quantidadeCruzamento) {

                int[] aux = new int[2];

                aux[0] = random.nextInt(Integer.parseInt(tamanhoPopulacao));
                aux[1] = random.nextInt(Integer.parseInt(tamanhoPopulacao));

                //System.out.println("FITNES 1 ->" + individuos.get(aux[0]).fitness);
                //System.out.println("FITNES 2 ->" + individuos.get(aux[1]).fitness);
                int fitness1 = individuos.get(aux[0]).fitness;
                int fitness2 = individuos.get(aux[1]).fitness;

                if (fitness1 < fitness2) {    //comparar os fitness dos rands
                    torneioWin.add(individuos.get(aux[0]));

                } else {
                    torneioWin.add(individuos.get(aux[1]));
                }
            }

            individuosSelecionados
                    = new ArrayList<>();
            individuosSelecionados.addAll(torneioWin);

            Cruzamento cruzamento = new Cruzamento(individuosSelecionados, Integer.parseInt(taxaMutacao));
            cruzamento.Mutacao(geracao, Integer.parseInt(tamanhoPopulacao));
            individuos.clear();
            individuos.addAll(cruzamento.popAuxiliar);
            geracao++;
            /*
        
            System.out.println("-------------- Com Torneio ----------------");
       
            for (int i = 0; i < torneioWin.size(); i++) {
                
                int fit = torneioWin.get(i).getFitness();
                System.out.println("INDIVIDUO " + i + " Fitness = " + fit);
            }
        
             */
        } while ((individuos.get(0).getFitness() > 9 + Math.abs(Integer.parseInt(posicaoRato) - Integer.parseInt(posicaoQueijo))) && geracao < Integer.parseInt(geracoes));
    }

    public void rankTorneio() {

        int rank = (int) (quantidadeCruzamento * 0.1); //10% dos 85% ou 90%
        int torneio = quantidadeCruzamento - rank; //90% dos 85% ou 90%
        int geracao = 0;

        do {
            //----------- 90% com TORNEIO -----------//
            ArrayList<Individuo> torneioWin = new ArrayList<>();

            Random random = new Random();

            while (torneioWin.size() < torneio) {

                int[] aux = new int[2];

                aux[0] = random.nextInt(Integer.parseInt(tamanhoPopulacao));
                aux[1] = random.nextInt(Integer.parseInt(tamanhoPopulacao));

                int fitness1 = individuos.get(aux[0]).fitness;
                int fitness2 = individuos.get(aux[1]).fitness;

                if (fitness1 < fitness2) {    //comparar os fitness dos rands
                    torneioWin.add(individuos.get(aux[0]));

                } else {
                    torneioWin.add(individuos.get(aux[1]));
                }

            }

            //----------- 10% com RANK -----------//
            ArrayList<Individuo> ordenadoIndividuos = new ArrayList<Individuo>();
            ordenadoIndividuos = ordena(individuos);

            for (int i = (Integer.parseInt(tamanhoPopulacao) - 1); i >= rank; i--) {
                ordenadoIndividuos.remove(i);
            }

            ArrayList<Individuo> rankAndTorneio = new ArrayList<>();

            rankAndTorneio.addAll(ordenadoIndividuos);
            rankAndTorneio.addAll(torneioWin);

            individuosSelecionados = new ArrayList<>();
            individuosSelecionados.addAll(rankAndTorneio);

            Cruzamento cruzamento = new Cruzamento(individuosSelecionados, Integer.parseInt(taxaMutacao));
            cruzamento.Mutacao(geracao, Integer.parseInt(tamanhoPopulacao));
            individuos.clear();
            individuos.addAll(cruzamento.popAuxiliar);
            geracao++;
            /*
        
            System.out.println("-------------- Com Rank + Torneio ----------------");

            for (int i = 0; i < rankAndTorneio.size(); i++) {

                int fit = rankAndTorneio.get(i).getFitness();
                System.out.println("INDIVIDUO " + i + " Fitness = " + fit);
            }

                 */
        } while ((individuos.get(0).getFitness() > 9 + Math.abs(Integer.parseInt(posicaoRato) - Integer.parseInt(posicaoQueijo))) && geracao < Integer.parseInt(geracoes));
    }
}
