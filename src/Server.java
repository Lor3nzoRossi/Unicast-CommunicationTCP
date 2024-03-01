
import java.io.IOException;
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
    Socket socket;
    int porta;
    
    public Server(int porta){
        this.porta = porta;
    }
    public Socket attendi(){
        Socket socket = null;
        try {
            this.serverSocket = new ServerSocket(this.porta);
            System.out.println("Il server e' in ascolto...");
            socket = this.serverSocket.accept(); //ottengo il socket ritornato da serverSocket.accept() 
            System.out.println("Il client ha effettuato una richiesta, connessione avvenuta.");
        }catch(BindException e){
            System.err.println("Porta occupata.");
        }catch (IOException e) {
            System.err.println("Errore durante l'ascolto del server");
        }
        return socket;
    }
    public void scrivi(){
        
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
