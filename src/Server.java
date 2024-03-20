
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
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
            //scrivere al client
            scrivi();
            //ascolto di nuove richieste:
            leggi();
        }catch(BindException e){
            System.err.println("Porta occupata.");
        }catch (IOException e) {
            System.err.println("Server: errore durante l'ascolto " + e);
        }
    }
    public void scrivi(){
        Thread scrittura = new Thread(() -> {
            try {
                while (!this.serverSocket.isClosed() && !this.dataSocket.isClosed()) {                
                    try {
                        Scanner scanner = new Scanner(System.in);
                        this.bw.write("server) " + scanner.nextLine());
                        this.bw.newLine();
                        this.bw.flush();
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
            } catch (Exception e) {
                System.out.println("Server: errore nella scrittura " + e);
            }
        });
        scrittura.start();
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
                System.err.println("Server: errore nella lettura del messaggio: " + e);
            }
        });
        lettura.start();
    }
    //chiude la connessione con il client
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
    //termina completamente l'attività del server
    public void termina(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Server: errore nella chiusura della connessione." + e);
        }
    }
}
