package sistemamatricula;

public class Sistema {
    private Disciplina[] disciplinas;
    private int proxCodigo = 1;

    private Aluno[] alunos;
    private int proxMatricula = 1;

    void init() {
        // Inicializar
        this.disciplinas = new Disciplina[30];
        this.alunos = new Aluno[10];

        // Inserir 5 disciplinas
        Disciplina d = new Disciplina(gerarProxCodigo(), "Matematica", 2025, 10, "João Batista");
        this.inserir(d);

        d = new Disciplina(gerarProxCodigo(), "POO", 2025, 10, "Luciano");
        this.inserir(d);

        d = new Disciplina(gerarProxCodigo(), "História", 2025, 10, "Júlia Junqueira");
        this.inserir(d);

        d = new Disciplina(gerarProxCodigo(), "Fisica", 2025, 10, "João Paulo");
        this.inserir(d);

        d = new Disciplina(gerarProxCodigo(), "Quimica", 2025, 10, "Bárbara");
        this.inserir(d);

        // Inserir 3 alunos
        Aluno a = new Aluno(gerarProxMatricula(), "Victor Hugo", "Lucimara", "Rua 1, 12345-678");
        this.inserir(a);

        a = new Aluno(gerarProxMatricula(), "Gabriel Miranda", "Maria", "Rua 2, 87654-321");
        this.inserir(a);

        a = new Aluno(gerarProxMatricula(), "Carlos Eduardo", "Maria Isabel", "Rua 3, 23456-789");
        this.inserir(a);

    }

    boolean inserir(Aluno aluno) {
        if (aluno != null) {
            this.alunos[getProxMatricula()] = aluno;
            return true;
        } else {
            return false;
        }
    }

    boolean inserir(Disciplina disciplina) {
        if (disciplina != null && disciplina.getNumVagas() > 0) {
            this.disciplinas[getProxCodigo()] = disciplina;
            return true;
        } else {
            return false;
        }
    }

    boolean excluir(long codDisciplina) {
        boolean excluido = false;

        for (int i = 0; i < this.disciplinas.length; i++) {
            if (this.disciplinas[i] != null && this.disciplinas[i].getCodigo() == codDisciplina) {
                this.disciplinas[i] = null;
                excluido = true;
            }
        }

        if (excluido) {
            for (Aluno aluno : this.alunos) {
                if (aluno != null) {
                    NotaDisciplina[] nd = aluno.getDiscMatriculadas();
                    for (int j = 0; j < nd.length; j++) {
                        if (nd[j] != null && nd[j].getDisciplina() != null && nd[j].getDisciplina().getCodigo() == codDisciplina) {
                            nd[j] = null;
                            aluno.setNumDiscMatriculadas(aluno.getNumDiscMatriculadas() - 1);
                        }
                    }
                    aluno.setMedia(calcularMedia(aluno.getDiscMatriculadas()));
                }
            }
        }
        return excluido;
    }

    Aluno buscarAluno(long matricula) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null)
                if (aluno.getNumMatricula() == matricula)
                    return aluno;
        }
        return null;
    }

    Disciplina buscarDisciplina(long codDisciplina) {
        for (Disciplina disciplina : this.disciplinas) {
            if (disciplina != null) {
                if (disciplina.getCodigo() == codDisciplina) {
                    return disciplina;
                }
            }
        }
        return null;
    }

    boolean alunoExiste(String nome) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null) {
                if (aluno.getNome().equalsIgnoreCase(nome)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean alunoExiste(long matAluno) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null) {
                if (aluno.getNumMatricula() == matAluno)
                    return true;
            }
        }
        return false;
    }

    public Disciplina[] getDisciplinas() {
        return disciplinas;
    }

    public Aluno[] getAlunos() {
        return alunos;
    }

    public int getProxCodigo() {
        return proxCodigo;
    }

    public int getProxMatricula() {
        return proxMatricula;
    }

    public int gerarProxCodigo() {
        return proxCodigo++;
    }

    public int gerarProxMatricula() {
        return proxMatricula++;
    }

    double calcularMedia(NotaDisciplina[] notas) {
        double soma = 0;
        double contador = 0;
        for (NotaDisciplina nota : notas) {
            if (nota != null && nota.getDisciplina() != null) {
                soma += nota.getNota();
                contador++;
            }
        }
        if (contador == 0) return 0;
        return soma / contador;
    }

    Aluno[] ordenarPorMedia(Aluno[] alunos) {
        Aluno[] copia = new Aluno[alunos.length];
        for (int i = 0; i < alunos.length; i++) {
            copia[i] = alunos[i];
        }

        for (int i = 0; i < copia.length; i++) {
            for (int j = i + 1; j < copia.length; j++) {
                if (copia[i] != null && copia[j] != null) {
                    if (copia[i].getMedia() < copia[j].getMedia()) {
                        Aluno aux = copia[i];
                        copia[i] = copia[j];
                        copia[j] = aux;
                    }
                }
            }
        }
        return copia;
    }

    boolean alunoEstaMatriculado(Aluno a, long codDisciplina) {
        if (a != null) {
            for (NotaDisciplina nd : a.getDiscMatriculadas()) {
                if (nd != null && nd.getDisciplina() != null && nd.getDisciplina().getCodigo() == codDisciplina) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean alterarNomeAluno(long matricula, String nome) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null && aluno.getNumMatricula() == matricula) {
                aluno.setNome(nome);
                return true;
            }
        }
        return false;
    }

    boolean alterarNomeDaMaeAluno(long matricula, String nomeDaMae) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null && aluno.getNumMatricula() == matricula) {
                aluno.setNomeDaMae(nomeDaMae);
                return true;
            }
        }
        return false;
    }

    boolean alterarEnderecoAluno(long matricula, String endereco) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null && aluno.getNumMatricula() == matricula) {
                aluno.setEndereco(endereco);
                return true;
            }
        }
        return false;
    }

    boolean alterarNotaDisciplina(long matricula, long codDisciplina, double nota) {
        for (Aluno aluno : this.alunos) {
            if (aluno != null && aluno.getNumMatricula() == matricula) {
                for (NotaDisciplina nd : aluno.getDiscMatriculadas()) {
                    if (nd != null && nd.getDisciplina() != null && nd.getDisciplina().getCodigo() == codDisciplina) {
                        nd.setNota(nota);
                        aluno.setMedia(calcularMedia(aluno.getDiscMatriculadas()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean matricularAluno(long matricula, long codDisciplina) {
        Aluno aluno = buscarAluno(matricula);
        Disciplina disciplina = buscarDisciplina(codDisciplina);
        if (aluno != null) {
            if (disciplina != null) {
                if (!alunoEstaMatriculado(aluno, codDisciplina) && disciplina.getNumVagas() > 0) {
                    aluno.getDiscMatriculadas()[aluno.getNumDiscMatriculadas()] = new NotaDisciplina(disciplina, 0);
                    aluno.setNumDiscMatriculadas(aluno.getNumDiscMatriculadas() + 1);
                    aluno.setMedia(calcularMedia(aluno.getDiscMatriculadas()));
                    disciplina.setNumVagas(disciplina.getNumVagas() - 1);
                    return true;
                }
            }
        }
        return false;
    }

}
