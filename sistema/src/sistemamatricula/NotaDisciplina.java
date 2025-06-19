package sistemamatricula;

public class NotaDisciplina {
    private double nota;
    private Disciplina disciplina;

    NotaDisciplina(Disciplina disciplina, double nota) {
        if (nota >= 0 && nota <= 10)
            this.nota = nota;
        if (disciplina != null)
            this.disciplina = disciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if (nota >= 0 && nota <= 10)
            this.nota = nota;
    }
}
