import java.lang.String;

class MusicFile {

    private String titulo;
    private String interprete;
    private String ano;
    private String genero;
    private String album;
    private int numDownloads; //qtas vezes a musica foi descarregada

    private String userUploader; //utilizador que publicou o ficheiro



    public MusicFile(String titulo, String interprete, String ano, String genero, String album){
        this.titulo = titulo;
        this.interprete = interprete;
        this.ano = ano;
        this.genero = genero;
        this.album = album;
        this.numDownloads = 0;
        //this.userUploader ="";
    }



    public MusicFile(MusicFile mf){
        this.titulo = mf.getTitulo();
        this.interprete = mf.getInterprete();
        this.ano = mf.getAno();
        this.genero = mf.getGenero();
        this.album = mf.getAlbum();
        this.numDownloads = mf.getNumDownloads();
    }



    public String getTitulo(){return this.titulo;}

    public String getInterprete(){return this.interprete;}

    public String getAno(){return this.ano;}

    public String getGenero(){return this.genero;}

    public String getAlbum(){return this.album;}

    /*public String getUploader(){return this.userUploader;}*/

    public int getNumDownloads(){return this.numDownloads;}


    public void setUserUploader(String user){
        this.userUploader = user;
    }



    public void incrementNumDownloads(){ //atualizar total de downloads do ficheiro sempre que é descarregado
		this.numDownloads++;
	}



    public MusicFile clone(){
        return new MusicFile(this);
    }

    public boolean hasPermission(String user){  //verificar se o user dado como parametro corresponde
        return this.userUploader.equals(user);    // ao user que partilhou o ficheiro
    }

    public String toString(){//Imprime a informação de uma música
        StringBuilder sb = new StringBuilder();

        sb.append("Título: ");
        sb.append(titulo);
        sb.append(" | ");
        sb.append("Intérprete: ");
        sb.append(interprete);
        sb.append(" | ");
        sb.append("Ano: ");
        sb.append(ano);
        sb.append(" | ");
        sb.append("Género: ");
        sb.append(genero);
        sb.append(" | ");
        sb.append("Álbum: ");
        sb.append(album);
        sb.append(" | ");
        sb.append("Número de downloads: ");
        sb.append(numDownloads);
        sb.append("\n");

        return sb.toString();
    }


}
