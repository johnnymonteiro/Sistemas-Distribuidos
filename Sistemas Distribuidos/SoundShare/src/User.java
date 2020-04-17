

import java.util.*;


class User {

    private String username;
    private String password;
    private boolean ligacao;
    private List<MusicFile> musicas; //lista de musicas descarregadas




    public User(String username, String pass) {
        this.username = username;
        this.password = pass;
        this.musicas = new ArrayList<>();
        this.ligacao = false;

    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getLigacao() {
        return this.ligacao;
    }

    public void setLigacao(boolean ligacao){
        this.ligacao = ligacao;
    }



    /*public int getTotalMusicas() {
    	return musicas.size(); 
    }*/
    

    public void addMusica(MusicFile mf){
		this.musicas.add(mf);
	}

    public List<MusicFile> getMusicas(){
        List<MusicFile> res = new ArrayList<>();
        for(MusicFile mf: this.musicas){
            res.add(mf);
        }

        return res;

    }


    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (this == null)
            return false;

        if (this.getClass() != o.getClass())
            return false;

        User u = (User) o;

        return (this.username.equals(u.getUsername()) && this.password.equals(u.getPassword()));
    }


	


}
