
import java.io.IOException;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Studenti
 */
public class Client {
    String nome;
    String colore;
    Socket socket;
    

    public Client(String nomeDefault, String coloreDefault) {
        this.nome = nomeDefault;
        this.colore = coloreDefault;
    }
    public void connetti(String nomeServer, int portaServer) {
        try {
            this.socket = new Socket(nomeServer, portaServer);
            System.out.println("Il client si è connesso al server " + nomeServer + " sulla porta " + portaServer);
        } catch (IOException e) {
            System.err.println("Errore durante la connessione al server " + nomeServer + " sulla porta " + portaServer + ": " + e.getMessage());
        }
    }
    public void scrivi(){
        
    }
    public void leggi(){
        
    }
    public void chiudi(){
        
    }
}
