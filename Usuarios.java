public class Usuarios {
    //Características do user
    ClienteSocket endereco;
    public String nome;

    public Usuarios(){

    }
    public Usuarios(ClienteSocket endereco, String nome){
        this.endereco = endereco;
        this.nome = nome;
    }
    public void setEndereco(ClienteSocket endereco) {
        this.endereco = endereco;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public ClienteSocket getEndereco(){
        return this.endereco;
    }
    public String getNome(){
        return this.nome;
    }
    
}
