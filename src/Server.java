
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

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
    Socket clientSocket;
    int porta;
    //per scrivere
    BufferedWriter bw;
    //per leggere
    BufferedReader br;
    
    public Server(int porta){
        this.porta = porta;
    }
    public Socket attendi(){
        Socket socket = null;
        try {
            this.serverSocket = new ServerSocket(this.porta);
            System.out.println("Il server e' in ascolto...");
            socket = this.serverSocket.accept(); //ottengo il socket ritornato da serverSocket.accept() 
            this.clientSocket = socket;
            System.out.println("Richiesta del client accettata con successo, connessione avvenuta.");
            //definisco i due stream per comunicare con il Client
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //se tutto a buon fine, scrivere al client
            scrivi();
        }catch(BindException e){
            System.err.println("Porta occupata.");
        }catch (IOException e) {
            System.err.println("Errore durante l'ascolto del server.");
        }
        return socket;
    }
    public void scrivi(){
        try {
            this.bw.write("Client, un saluto da Server!");
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void leggi(){
        
    }
    public void chiudi(){
        
    }
    public void termina(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Errore nella chiusura della connessione.");
        }
    }
}
