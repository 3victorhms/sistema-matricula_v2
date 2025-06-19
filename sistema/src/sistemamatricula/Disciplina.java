package sistemamatricula;

public class Disciplina {
    private long codigo;
    private String nome;
    private int ano;
    private int numVagas;
    private String nomeDoProfessor;

    public Disciplina(long codigo, String nome, int ano, int numVagas, String nomeDoProfessor) {
        this.codigo = codigo;
        this.nome = nome;
        this.ano = ano;
        this.numVagas = numVagas;
        this.nomeDoProfessor = nomeDoProfessor;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getAno() {
        return ano;
    }

    public int getNumVagas() {
        return numVagas;
    }

    public void setNumVagas(int numVagas) {
        if (numVagas >= 0)
            this.numVagas = numVagas;
    }

    public String getNomeDoProfessor() {
        return nomeDoProfessor;
    }

}
