package sistemamatricula;

public class Aluno {
    private long numMatricula;
    private String nome;
    private String nomeDaMae;
    private String endereco;
    private NotaDisciplina[] discMatriculadas;
    private int numDiscMatriculadas;
    private double media;

    public Aluno(long numMatricula, String nome, String nomeDaMae, String endereco) {
        this.numMatricula = numMatricula;
        this.nome = nome;
        this.nomeDaMae = nomeDaMae;
        this.endereco = endereco;
        this.discMatriculadas = new NotaDisciplina[10];
        this.numDiscMatriculadas = 0;
    }

    public long getNumMatricula() {
        return numMatricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty())
            this.nome = nome;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        if (nomeDaMae != null && !nomeDaMae.isEmpty())
            this.nomeDaMae = nomeDaMae;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco != null && !endereco.isEmpty())
            this.endereco = endereco;
    }

    public NotaDisciplina[] getDiscMatriculadas() {
        return discMatriculadas;
    }

    public void setNumDiscMatriculadas(int numDiscMatriculadas) {
        this.numDiscMatriculadas = numDiscMatriculadas;
    }

    public int getNumDiscMatriculadas() {
        return numDiscMatriculadas;
    }

    public void setMedia(double media) {
        if (media >= 0 && media <= 10)
            this.media = media;
    }

    public double getMedia() {
        return media;
    }
}
