/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ratoqueijo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author mathe
 */
public class Main extends Application {

    public static final ObservableList<String> posicoes
            = FXCollections.observableArrayList(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
            );
    public static final ObservableList<String> populacao
            = FXCollections.observableArrayList(
                    "100", "500", "1000", "5000"
            );
    public static final ObservableList<String> cruzamento
            = FXCollections.observableArrayList(
                    "85", "90"
            );
    public static final ObservableList<String> mutacao
            = FXCollections.observableArrayList(
                    "1", "3", "5"
            );
    public static final ObservableList<String> selecao
            = FXCollections.observableArrayList(
                    "rank", "torneio", "rank + torneio"
            );
    public static final ObservableList<String> geracoes
            = FXCollections.observableArrayList(
                    "500", "2500", "5000"
            );
    
    Image tabuleiro;
    Image rato;
    Image queijo;

    public ImageView ratoImageView;
    public ImageView queijoImageView;
    ImageView caminho1, caminho2, caminho3, caminho4, caminho5, caminho6, caminho7, caminho8;
    
    int[] bestCaminho;

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group(), 160, 400);

        final ComboBox comboBoxRato = new ComboBox();
        final Label labelRato = new Label("Posição Rato");

        final ComboBox comboBoxQueijo = new ComboBox();
        final Label labelQueijo = new Label("Posição Queijo");

        final ComboBox comboBoxPopulacao = new ComboBox();
        final Label labelPopulacao = new Label("População");

        final ComboBox comboBoxCruzamento = new ComboBox();
        final Label labelCruzamento = new Label("Cruzamento");

        final ComboBox comboBoxMutacao = new ComboBox();
        final Label labelMutacao = new Label("Mutação");

        final ComboBox comboBoxSelecao = new ComboBox();
        final Label labelSelecao = new Label("Seleção");
        
        final ComboBox comboBoxGeracao = new ComboBox();
        final Label labelGeracao = new Label("Gerações");

        comboBoxRato.getItems().addAll(posicoes);
        comboBoxQueijo.getItems().addAll(posicoes);
        comboBoxPopulacao.getItems().addAll(populacao);
        comboBoxCruzamento.getItems().addAll(cruzamento);
        comboBoxMutacao.getItems().addAll(mutacao);
        comboBoxSelecao.getItems().addAll(selecao);
        comboBoxGeracao.getItems().addAll(geracoes);

        Button btn = new Button();
        btn.setText("Executar");

        GridPane grid = new GridPane();

        grid.add(labelRato, 0, 0);
        grid.add(comboBoxRato, 0, 1);

        grid.add(labelQueijo, 0, 2);
        grid.add(comboBoxQueijo, 0, 3);

        grid.add(labelPopulacao, 0, 4);
        grid.add(comboBoxPopulacao, 0, 5);

        grid.add(labelCruzamento, 0, 6);
        grid.add(comboBoxCruzamento, 0, 7);

        grid.add(labelMutacao, 0, 8);
        grid.add(comboBoxMutacao, 0, 9);

        grid.add(labelSelecao, 0, 10);
        grid.add(comboBoxSelecao, 0, 11);
        
        grid.add(labelGeracao, 0, 12);
        grid.add(comboBoxGeracao, 0, 13);

        grid.add(btn, 0, 16);

        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(4, 4, 4, 4));

        Group root = (Group) scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);

        stage.setTitle("Rato Queijo");
        stage.setScene(scene);
        stage.show();

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Stage stageTab = new Stage();
                String posicaoRato, posicaoQueijo, tamanhoPopulacao, taxaCruzamento, taxaMutacao, mecanismoSelecao, geracoes;
                int ratoY, queijoY;

                posicaoRato = (String) comboBoxRato.getValue();
                posicaoQueijo = (String) comboBoxQueijo.getValue();
                tamanhoPopulacao = (String) comboBoxPopulacao.getValue();
                taxaCruzamento = (String) comboBoxCruzamento.getValue();
                taxaMutacao = (String) comboBoxMutacao.getValue();
                mecanismoSelecao = (String) comboBoxSelecao.getValue();
                geracoes = (String) comboBoxGeracao.getValue();

                ratoY = (80 + ((Integer.parseInt(posicaoRato) - 1) * 60));

                queijoY = (75 + ((Integer.parseInt(posicaoQueijo) - 1) * 60));

                Configurar config = new Configurar(posicaoRato, posicaoQueijo, tamanhoPopulacao, taxaCruzamento, taxaMutacao, mecanismoSelecao, geracoes);
                config.geraPopulacao();
                config.calculaQuantidadeCruzamento();

                if (mecanismoSelecao.equals("rank")) {

                    config.rank();

                    bestCaminho = new int[10];
                    bestCaminho = config.individuos.get(0).getCaminho();

                    for (int i = 0; i < 10; i++) {
                        System.out.println("Posicao " + i + " -> " + bestCaminho[i]);
                    }
                } else if (mecanismoSelecao.equals("torneio")) {

                    config.torneio();

                    bestCaminho = new int[10];
                    bestCaminho = config.individuos.get(0).getCaminho();

                    for (int i = 0; i < 10; i++) {
                        System.out.println("Posicao " + i + " -> " + bestCaminho[i]);
                    }
                } else if (mecanismoSelecao.equals("rank + torneio")) {

                    config.rankTorneio();

                    bestCaminho = new int[10];
                    bestCaminho = config.individuos.get(0).getCaminho();

                    for (int i = 0; i < 10; i++) {
                        System.out.println("Posicao " + i + " -> " + bestCaminho[i]);
                    }
                }

                Group grupoObjetos = new Group();
                stageTab.setTitle("Tabuleiro");
                stageTab.setResizable(false);
                stageTab.setWidth(800);
                stageTab.setHeight(800);
                stageTab.setScene(new Scene(grupoObjetos));

                //Creating an image 
                try {
                    tabuleiro = new Image(new FileInputStream("C:\\Users\\mathe\\Desktop\\RatoQueijo\\img\\tabuleiro.png"));
                    rato = new Image(new FileInputStream("C:\\Users\\mathe\\Desktop\\RatoQueijo\\img\\rato.png"));
                    queijo = new Image(new FileInputStream("C:\\Users\\mathe\\Desktop\\RatoQueijo\\img\\queijo.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Setting the image view 
                ImageView tabuleiroImageView = new ImageView(tabuleiro);
                tabuleiroImageView.setX(0);
                tabuleiroImageView.setY(0);
                tabuleiroImageView.setFitHeight(800);
                tabuleiroImageView.setFitWidth(800);
                tabuleiroImageView.setPreserveRatio(true);

                System.out.println("Configurou as Imagens!");

                //Setting the image view 
                ratoImageView = new ImageView(rato);
                ratoImageView.setTranslateX(95);
                ratoImageView.setTranslateY(ratoY);
                ratoImageView.setFitHeight(40);
                ratoImageView.setFitWidth(40);
                //Setting the image view 
                queijoImageView = new ImageView(queijo);
                queijoImageView.setTranslateX(640);
                queijoImageView.setTranslateY(queijoY);
                queijoImageView.setFitHeight(40);
                queijoImageView.setFitWidth(40);
                queijoImageView.setPreserveRatio(true);
                
                caminho1 = new ImageView(rato);
                caminho1.setTranslateX(calculaX(1));
                caminho1.setTranslateY(calculaY(1));
                caminho1.setFitHeight(40);
                caminho1.setFitWidth(40);
                
                caminho2 = new ImageView(rato);
                caminho2.setTranslateX(calculaX(2));
                caminho2.setTranslateY(calculaY(2));
                caminho2.setFitHeight(40);
                caminho2.setFitWidth(40);
                
                caminho3 = new ImageView(rato);
                caminho3.setTranslateX(calculaX(3));
                caminho3.setTranslateY(calculaY(3));
                caminho3.setFitHeight(40);
                caminho3.setFitWidth(40);
                
                caminho4 = new ImageView(rato);
                caminho4.setTranslateX(calculaX(4));
                caminho4.setTranslateY(calculaY(4));
                caminho4.setFitHeight(40);
                caminho4.setFitWidth(40);
                
                caminho5 = new ImageView(rato);
                caminho5.setTranslateX(calculaX(5));
                caminho5.setTranslateY(calculaY(5));
                caminho5.setFitHeight(40);
                caminho5.setFitWidth(40);
                
                caminho6 = new ImageView(rato);
                caminho6.setTranslateX(calculaX(6));
                caminho6.setTranslateY(calculaY(6));
                caminho6.setFitHeight(40);
                caminho6.setFitWidth(40);
                
                caminho7 = new ImageView(rato);
                caminho7.setTranslateX(calculaX(7));
                caminho7.setTranslateY(calculaY(7));
                caminho7.setFitHeight(40);
                caminho7.setFitWidth(40);
                
                caminho8 = new ImageView(rato);
                caminho8.setTranslateX(calculaX(8));
                caminho8.setTranslateY(calculaY(8));
                caminho8.setFitHeight(40);
                caminho8.setFitWidth(40);

                grupoObjetos.getChildren().addAll(tabuleiroImageView, ratoImageView, queijoImageView, caminho1, caminho2, caminho3, caminho4, caminho5, caminho6, caminho7, caminho8);
                stageTab.show();
            }
        });
    }

    public int calculaX(int i) {
        int x = 95 + i * 60;
        return x;
    }
    
    public int calculaY(int i) {
        int y = (80 + (bestCaminho[i] - 1) * 60);
        return y;        
    }

    public static void main(String[] args) {
        launch(args);

    }

}
