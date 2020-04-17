import java.io.IOException;
import java.text.ParseException;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.*;
import java.util.*;


public class SoundShare {

    private static HashMap<Integer, MusicFile> musicas = new HashMap<>();
    private static HashMap<String, User> utilizadores = new HashMap<>();
    private static HashMap<String,String> notificacoes = new HashMap<>();



  

    public static void main(String[] args) throws IOException, ParseException{

        povoamento(); //popular a base dados com utilizadores e musicas

        ServerSocket ss = new ServerSocket(4444);
        int id=0;
        while (true) {
            Socket clientSocket = ss.accept();
            ClienteHandler thread = new ClienteHandler(musicas,utilizadores,notificacoes,clientSocket,id++);
            thread.start();
        }
    }

    public static void uploadMusica(MusicFile mf){
        int idMusica = musicas.size()+1;
        musicas.put(idMusica, mf);
    }

    public static void removerMusica(int idMusica) {
        musicas.remove(idMusica);
    }

    public static void addNotificacao(String titulo, String artista ){
        notificacoes.put(titulo,artista);
    }





    public static void povoamento() {
        User ut1 = new User("d","d");
        User ut2 = new User("p","p");
        User ut3 = new User("s","s");

        utilizadores.put("d", ut1);
        utilizadores.put("p", ut2);
        utilizadores.put("s", ut3);

        MusicFile mf1 = new MusicFile("Jammin", "Bob Marley", "1977", "Reggae", "Babylon");
        MusicFile mf2 = new MusicFile("Buffalo", "Bob Marley", "1980", "Reggae", "Road to zion");
        MusicFile mf3 = new MusicFile("TKO", "Justin Timberlake", "2015", "Pop", "Mr Timberlake");
        MusicFile mf4 = new MusicFile("Indigo","Chris Brown","2019","R&B","Indigo");
        MusicFile mf5 = new MusicFile("Talk","Khalid","2019","R&B","Free Spirit");
        MusicFile mf6 = new MusicFile("Afeto","Mayra Andrade","2019","World","Manga");

        mf1.setUserUploader(ut1.getUsername()); // d
        mf2.setUserUploader(ut2.getUsername()); // p
        mf3.setUserUploader(ut3.getUsername()); // s
        mf4.setUserUploader(ut1.getUsername()); // d
        mf5.setUserUploader(ut2.getUsername()); // p
        mf6.setUserUploader(ut3.getUsername()); // s

        musicas.put(1,mf1);
        musicas.put(2,mf2);
        musicas.put(3,mf3);
        musicas.put(4,mf4);
        musicas.put(5,mf5);
        musicas.put(6,mf6);
    }




}
