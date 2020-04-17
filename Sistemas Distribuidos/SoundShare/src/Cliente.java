//import javax.xml.bind.annotation.XmlType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;

class Cliente{

    public static Socket cliente;
    public static BufferedReader in;
    public static PrintWriter out;
    public static BufferedReader sin;


    public static void main(String[] args) throws IOException, ParseException {


        cliente = new Socket("localhost", 4444);

        out = new PrintWriter (cliente.getOutputStream(), true); //envia dados ao servidor
        in = new BufferedReader (new InputStreamReader (cliente.getInputStream())); //servidor lê o que o cliente está enviando
        sin = new BufferedReader (new InputStreamReader(System.in)); //para ler do teclado

        try{
            menu_inicial();
            String current = sin.readLine();
            int converse;
            converse = Integer.parseInt(current); //converter a opção em string para int

            while(converse!=0){

                switch (converse){
                    case 1: //Registar
                        System.out.print("Introduz o username: ");
                        String u = sin.readLine();
                        System.out.print("Introduz a password: ");
                        String p = sin.readLine();
                        out.println("REGISTO|"+u+"|"+p);

                        System.out.println(current = in.readLine());
                        if ( !current.equals("UTILIZADOR JÁ EXISTE")){

                            menu_utilizador(u);
                        }
                        else{
                            main(args);
                        }
                        break;

                    case 2:  //Login
                        System.out.print("Username: ");
                        String ul = sin.readLine();
                        System.out.print("Password: ");
                        String pl = sin.readLine();

                        out.println("LOGIN|" +ul + "|" +pl );
                        System.out.println(current=in.readLine());
                        if ( current.equals("UTILIZADOR JA CONECTADO"))
                            menu_utilizador(ul);

                        if ( current.equals("LOGIN FEITO COM SUCESSO"))
                            menu_utilizador(ul);
                        else{
                            main(args);
                            break;
                        }
                    default:
                        System.out.println("COMANDO INVALIDO");
                        main(args);
                }

                //se for 0, sai da aplicação
                current = sin.readLine();
                converse = Integer.parseInt(current);
                sin.close();
                in.close();
                out.close();
            }

            System.out.println("SAIU DA APLICAÇAO");
            System.exit(0);

        } catch (IOException ex) {
            System.out.println("SERVIDOR INDISPONIVEL, TENTE MAIS TARDE!");
        }
    }




    private static void menu_inicial(){
        System.out.println(" ______________________________________________________________");
        System.out.println("                                                               ");
        System.out.println("                      S O U N D S H A R E                      ");
        System.out.println("                                                               ");
        System.out.println(" >>>>>>>> Descobre, partilha e disfruta a tua música! <<<<<<<< ");
        System.out.println(" _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ");
        System.out.println("|                                                             |");
        System.out.println("|                     [1]    REGISTAR                         |");
        System.out.println("|                     [2]    LOGIN                            |");
        System.out.println("|                     [0]    SAIR                             |");
        System.out.println("|                                                             |");
        System.out.println("|-------------------------------------------------------------|");
    }

    private static void menu_utilizador(String u) throws IOException, ParseException{
            int op = -1;


            while(op!=0){

                System.out.println("...........................................................");
                System.out.println(":                   B E M   V I N D O !                   :");
                System.out.println("...........................................................");
                System.out.println("|                                                         |");
                System.out.println("+                  [1] UPLOAD MÚSICA                      +");
                System.out.println("|                  [2] PROCUAR MÚSICA                     |");
                System.out.println("+                  [3] DOWNLOAD MÚSICA                    +");
                System.out.println("|                  [4] MINHA BIBLIOTECA                   |");
                System.out.println("+                  [5] REMOVER MÚSICA                     +");
                System.out.println("+                  [6] NOTIFICAÇÕES                       +");
                System.out.println("|                  [7] VOLTAR MENU INICIAL                |");
                System.out.println("+                  [0] SAIR                               +");
                System.out.println("|                                                         |");
                System.out.println("+                                                         +");
                System.out.println("|---------------------------------------------------------|");

                op=Integer.parseInt(sin.readLine());
                String current;//in.readLine();
                switch (op) {
                    case 1:
                        System.out.print("Nome da música: ");
                        String n = sin.readLine();
                        System.out.print("Interprete: ");
                        String i = sin.readLine();
                        System.out.print("Ano: ");
                        String a = sin.readLine();
                        System.out.print("Genero: ");
                        String g = sin.readLine();
                        System.out.print("Album: ");
                        String al = sin.readLine();
                        out.println("Upload|"+u+"|"+n+"|"+i+"|"+a+"|"+g+"|"+al);

                        //out.println("Notificar|"+n+"|"+i);

                        System.out.println(current = in.readLine());

                        if (current.equals("UPLOAD TERMINADO")) {
                            menu_utilizador(u);
                            break;

                        } else {
                            System.out.println("MUSICA NÃO CARREGADA");
                            //main(args);
                            break;
                        }



                    case 2:
                        boolean estado = true;
                        System.out.print("Introduza o critério de pesquisa (interprete, titulo, album, genero ou ano): ");
                        String criterio = sin.readLine();
                        System.out.print("Introduza o " + criterio + ": ");
                        String tag = sin.readLine();
                        if (tag.equalsIgnoreCase("ano")) {
                            int tag1 = Integer.parseInt(tag);
                            out.println("Procurar|" + tag1);
                        }

                        out.println("Procurar|" + tag);
                        while (estado) {
                            String line;
                            System.out.println(line = in.readLine());
                            if (line.equals("procurar musica"))
                                estado = false;
                        }
                        break;


                    case 3:
                        System.out.print("Introduza o id da música pretendida para download: ");
                        String idMusica = sin.readLine();
                        //int id = Integer.parseInt(idMusica);
                        out.println("Download|" + u + "|" + idMusica);

                        System.out.println(current = in.readLine());
                        if (current.equals("DOWNLOAD TERMINADO")) {
                            menu_utilizador(u);
                            break;

                        } else {

                            menu_utilizador(u);
                            break;
                        }


                    case 4: {
                        boolean estado1 = true;
                        System.out.println("Biblioteca de " + u);
                        out.println("Biblioteca|" + u);
                        while (estado1) {
                            String line;
                            System.out.println(line = in.readLine());
                            if (line.equals("ver biblioteca"))
                                estado1 = false;
                        }

                        break;
                    }

                    case 5:
                        System.out.print("Introduza o id da música a remover: ");
                        String id_aux = sin.readLine();
                        out.println("Remover|" + u + "|" + id_aux);
                        System.out.println(current = in.readLine());

                        if (current.equals("MUSICA REMOVIDA COM SUCESSO")) {
                            menu_utilizador(u);
                        }
                        else{
                            menu_utilizador(u);
                        }

                        break;

                    case 6:{
                        boolean estado2 = true;
                        System.out.println("Notificações de novas músicas: ");
                        out.println("Notificar|");
                        while (estado2) {
                            String line;
                            System.out.println(line = in.readLine());
                            if (line.equals("notificacoes"))
                                estado2 = false;
                        }
                        menu_utilizador(u);
                        break;
                    }

                    case 7:
                        String[] args = null;
                        main(args);
                        break;


                    case 0:{
                        out.println("Sair|"+u);
                        System.exit(0);
                        System.out.println("Até Breve!");
                    }

                    default:
                        menu_utilizador(u);
                        break;


                }

            }

            System.exit(0);
    }
}
