
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Studenti
 */
public class Server {
    ServerSocket serverSocket;
    Socket dataSocket;
    int porta;
    //per output
    BufferedWriter bw;
    //per input
    BufferedReader br;
    
    public Server(int porta){
        this.porta = porta;
    }
    public void attendi(){
        try {
            this.serverSocket = new ServerSocket(this.porta);
            System.out.println("Il server e' in ascolto...");
            this.dataSocket = this.serverSocket.accept(); //ottengo il socket ritornato da serverSocket.accept() 
            System.out.println("Richiesta del client accettata con successo, connessione avvenuta.");
            //definisco i due stream per comunicare con il Client
            this.bw = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            //se tutto a buon fine inizia 
            //ascolto di nuove richieste:
            leggi();
            //possibilità di scrittura al client
            scrivi();
        }catch(BindException e){
            System.err.println("Porta occupata.");
        }catch (IOException e) {
            System.err.println("Errore durante l'ascolto del server.");
        }
    }
    public void scrivi(){
        try {
            this.bw.write("Client, un saluto da Server!");
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void leggi(){
        Thread lettura = new Thread(() -> {
            try {
                while (!this.serverSocket.isClosed() && !this.dataSocket.isClosed()) {
                    String msg = br.readLine();
                    if(msg!=null){
                        System.out.println(msg);
                    }else{
                        System.out.println("La connessione è stata interrotta");
                        chiudi();
                    }
                }
            } catch (IOException e) {
                System.err.println("Errore nella lettura del messaggio: " + e);
            }
        });
        lettura.start();
        try {
            lettura.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void chiudi(){
        if(!dataSocket.isClosed()){
            try {
                dataSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        termina();
    }
    public void termina(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Errore nella chiusura della connessione.");
        }
    }
}
