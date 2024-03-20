
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
public class Client {
    String nome;
    String colore;
    Socket socket;
    //per scrivere
    BufferedWriter bw;
    //per leggere
    BufferedReader br;
    

    public Client(String nomeDefault, String coloreDefault) {
        this.nome = nomeDefault;
        this.colore = coloreDefault;
    }
    public void connetti(String nomeServer, int portaServer) {
        try {
            this.socket = new Socket(nomeServer, portaServer);
            System.out.println("Il client si è connesso al server " + nomeServer + " sulla porta " + portaServer);
            //definisco i due stream per scrivere e leggere dal server
            this.bw = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            leggi();
            scrivi();
        } catch (IOException e) {
            System.err.println("Errore durante la connessione al server " + nomeServer + " sulla porta " + portaServer + ": " + e.getMessage());
        }
    }
    public void scrivi(){
        Thread scrittura = new Thread(() -> {
            try {
                Scanner scanner = new Scanner(System.in);
                String input = "esci";
                do {
                    System.out.print(this.nome + ") ");
                    input = scanner.nextLine();
                    this.bw.write(this.nome + ") " + input);
                    this.bw.newLine();
                    this.bw.flush();
                } while (!input.equals("esci"));
            } catch (IOException e) {
                System.out.println("Client: errore nella scrittura" + e);
            }
        });
        scrittura.start();
    }
    public void leggi(){
        Thread lettura = new Thread(() -> {
            try {
                while(!this.socket.isClosed()){
                    String msg = this.br.readLine();
                    if(msg!=null){
                        System.out.println(msg);
                    }
                }
            } catch (IOException e) {
                System.out.println("Client: errore nella lettura " + e);
            }
        });
        lettura.start();
    }
    public void chiudi(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
