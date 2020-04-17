import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteHandler extends Thread{

    private HashMap<Integer, MusicFile> musicas; //id e musica
    private HashMap<String, User> utilizadores; //username e user
    private HashMap<String, String> notificacoes;  //nome e interprete da musica
    private Socket cliente;
    private int clientID = -1;

    private BufferedReader in;
    private PrintWriter out;




    public ClienteHandler (HashMap<Integer,MusicFile> musicas, HashMap<String,User> utilizadores, HashMap<String, String> notificacoes, Socket cliente, int id) throws IOException {
        this.musicas = musicas;
        this.utilizadores = utilizadores;
        this.notificacoes = notificacoes;
        this.cliente = cliente;
        this.clientID = id;
        this.in = new BufferedReader(new InputStreamReader(cliente.getInputStream())); //servidor lê o que o cliente está enviando
        this.out = new PrintWriter(cliente.getOutputStream(),true);  //envia dados ao servidor

    }

    public synchronized String registo(String username, String password) {

        boolean estado = utilizadores.containsKey(username); //verifica se o username fornecido ja se encontra no sistema

        if (estado) { //se true

            return "UTILIZADOR JÁ EXISTE";
        }

        else{
            User utilizador = new User(username,password);
            utilizadores.put(username,utilizador);
            utilizadores.get(username).setLigacao(true); //coloca ligação a true: regista e faz logo login do user

            return "REGISTO CONCLUÍDO COM SUCESSO";

        }
    }


    public String login ( String username, String password ) {

        String pass_aux;
        boolean ligacao;
        boolean estado = utilizadores.containsKey(username);

        if (estado) {
            ligacao = utilizadores.get(username).getLigacao();

            if(ligacao)
                return "UTILIZADOR JA CONECTADO";

            pass_aux = utilizadores.get(username).getPassword();

            if ( pass_aux.equals(password) ) {
                utilizadores.get(username).setLigacao(true); //ligacao a true: faz o login
                return "LOGIN FEITO COM SUCESSO";
            }

            return "PALAVRA PASSE INCORRETA";

        }
        return "NOME DE UTILIZADOR INEXISTENTE";
    }


    //fazer o upload de uma musica para o sistema
    public synchronized String upload(String username, String nom, String inter, String a, String gen, String alb){

        try{
            MusicFile mf = new MusicFile(nom,inter,a,gen,alb);
            mf.setUserUploader(username); //guarda o utilizador que publicou o ficheiro

            SoundShare.uploadMusica(mf);
            SoundShare.addNotificacao(nom,inter); //cria uma notificação da nova musica carregada

        }catch (NullPointerException e){
            System.out.println(e);
        }
        
        return "UPLOAD TERMINADO";

    }



    public synchronized String download(String username, String idMusica) throws NullPointerException{

        //String res;

        int id = Integer.parseInt(idMusica);
        MusicFile mf = this.musicas.get(id);
        User ut = this.utilizadores.get(username);


        if (this.musicas.containsKey(id)){ //se a música existir no sistema, faz o download
            ut.addMusica(mf);
            mf.incrementNumDownloads();
            return "DOWNLOAD TERMINADO";
        }
        else
            return "MUSICA INEXISTENTE";

    }



    public synchronized String remover(String username, String idMusica) throws NullPointerException, NumberFormatException{

        int id = Integer.parseInt(idMusica);

        MusicFile mf = this.musicas.get(id);

        if(mf.hasPermission(username) && musicas.containsKey(id)){
            SoundShare.removerMusica(id);
            return "MUSICA REMOVIDA COM SUCESSO";
        }

        else
            return "REJEITADO! NÃO PODE REMOVER MÚSICAS CARREGADAS POR TERCEIROS";

    }




    public synchronized String procura(String tag) throws NullPointerException{ // tag pode ser: titulo, genero, interprete, ano ou album

        StringBuilder sb = new StringBuilder();

        for(Map.Entry<Integer, MusicFile> musica : musicas.entrySet())

            if (tag.equalsIgnoreCase(musica.getValue().getInterprete()) || tag.equalsIgnoreCase(musica.getValue().getAno()) || tag.equalsIgnoreCase(musica.getValue().getTitulo()) || tag.equalsIgnoreCase(musica.getValue().getGenero()) || tag.equalsIgnoreCase(musica.getValue().getAlbum())){

                sb.append("Id da música: ");
                sb.append(musica.getKey());
                sb.append(" | ");
                sb.append("Título: ");
                sb.append(musica.getValue().getTitulo());
                sb.append(" | ");
                sb.append("Intérprete: ");
                sb.append(musica.getValue().getInterprete());
                sb.append(" | ");
                sb.append("Ano: ");
                sb.append(musica.getValue().getAno());
                sb.append(" | ");
                sb.append("Género: ");
                sb.append(musica.getValue().getGenero());
                sb.append(" | ");
                sb.append("Álbum: ");
                sb.append(musica.getValue().getAlbum());
                sb.append(" | ");
                sb.append("Número de downloads: ");
                sb.append(musica.getValue().getNumDownloads());
                sb.append("\n");
            }


        return sb.toString()+"procurar musica";
    }


    //lista de musicas de um dado user (biblioteca pessoal)
    public synchronized String listMyLibrary(String username) throws NullPointerException {
        StringBuilder sb = new StringBuilder();

        User ut = this.utilizadores.get(username);
        List<MusicFile> aux = ut.getMusicas();
        try{
            for(MusicFile mf: aux){
                sb.append("Título: ");
                sb.append(mf.getTitulo());
                sb.append(" | ");
                sb.append("Intérprete: ");
                sb.append(mf.getInterprete());
                sb.append(" | ");
                sb.append("Ano: ");
                sb.append(mf.getAno());
                sb.append(" | ");
                sb.append("Género: ");
                sb.append(mf.getGenero());
                sb.append(" | ");
                sb.append("Álbum: ");
                sb.append(mf.getAlbum());
                sb.append(" | ");
                sb.append("Número de downloads: ");
                sb.append(mf.getNumDownloads());
                sb.append("\n");
            }
            return sb.toString()+"ver biblioteca";

        }catch (NullPointerException e){
            return "Algo correu mal! :( ";
        }

    }


    public synchronized String notificar() throws NullPointerException{

        StringBuilder sb = new StringBuilder();

        try{
            for(Map.Entry<String, String> artista : notificacoes.entrySet()){
                sb.append("Título: ");
                sb.append(artista.getKey()); //titulo
                sb.append(" | ");
                sb.append("Intérprete: ");
                sb.append(artista.getValue()); //interprete
                sb.append("\n");
            }

            return sb.toString()+"notificacoes";


        }catch (NullPointerException e){
            return "Não notificado!";
        }
    }


    public synchronized boolean handler (String s) throws IOException, NullPointerException, ParseException {
        boolean hand = true;

        try{

            String [] aux = s.split("\\|");
            switch(aux[0]){
                case "REGISTO": out.println(registo(aux[1],aux[2])); break;
                case "LOGIN" :  out.println( login(aux[1],aux[2])); break;
                case "Upload": out.println(upload(aux[1], aux[2], aux[3], aux[4], aux[5], aux[6]));break;
                case "Procurar": out.println(procura(aux[1]));break;
                case "Download": out.println(download(aux[1],aux[2]));break;
                case "Notificar": out.println(notificar());break;
                case "Biblioteca":out.println(listMyLibrary(aux[1]));break;
                case "Remover":out.println(remover(aux[1], aux[2]));break;
                case "Sair": utilizadores.get(aux[1]).setLigacao(false); break;
                default: break;

            }


        }catch (NullPointerException e){
            System.out.println("À ESPERA...");
            hand = false;
        }


        return hand;
    }

    @Override
    public void run() throws NullPointerException{

        System.out.println("Novo cliente no servidor. ID: "+this.clientID);
        try {
            PrintWriter o = new PrintWriter(cliente.getOutputStream());
            //BufferedReader b = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            while(handler(in.readLine())){
                //System.out.println (in.readLine());
                o.flush();
            }


        } catch (IOException | ParseException e) {
            Logger.getLogger(ClienteHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

