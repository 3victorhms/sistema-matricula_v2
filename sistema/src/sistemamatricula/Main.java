package sistemamatricula;

import java.util.Scanner;

public class Main {
    static Sistema sistema = new Sistema(30, 50);
    static Scanner leia = new Scanner(System.in);
    static int larguraTabela = 15;

    public static void main(String[] args) {
        sistema.init();
        boolean continuar = true;
        while (continuar) {
            continuar = menu();
        }

    }

    // scanner e print só em main
    static boolean menu() {
        System.out.println("---------- MENU ----------");
        System.out.println("1 - Adicionar disciplina");
        System.out.println("2 - Excluir disciplina");
        System.out.println("3 - Buscar disciplina");
        System.out.println("4 - Listar disciplinas");
        System.out.println("5 - Listar matrículas");
        System.out.println("6 - Adicionar aluno");
        System.out.println("7 - Alterar aluno");
        System.out.println("8 - Buscar aluno");
        System.out.println("9 - Listar alunos");
        System.out.println("0 - Sair");

        System.out.println("< ESCOLHA UMA OPÇÃO PARA CONTINUAR >");
        int opcao = lerOpcao(0, 9);

        if (opcao == 0) {
            System.out.println("Saindo...");
            return false;
        } else if (opcao == 1) {
            if (adicionarDisciplina())
                System.out.println("Disciplina adicionada com sucesso.");
            else
                System.out.println("Erro ao adicionar disciplina.");
            esperarEnter();
        } else if (opcao == 2) {
            listarDisciplinas(sistema.getDisciplinas());
            System.out.println("Insira o código da disciplina a ser removida: ('-1' para voltar):");
            long codDisciplina = leia.nextLong();
            leia.nextLine();

            if (escape(codDisciplina))
                return true;

            if (sistema.excluir(codDisciplina))
                System.out.println("Disciplina removida com sucesso.");
            else
                System.out.println("Erro ao remover disciplina.");
            esperarEnter();
        } else if (opcao == 3) {
            System.out.println("Insira o código da disciplina a ser buscada: ('-1' para voltar)");
            long codDisciplina = leia.nextLong();
            leia.nextLine();

            if (escape(codDisciplina))
                return true;

            Disciplina d = sistema.buscarDisciplina(codDisciplina);
            if (d != null)
                System.out.println("Disciplina: " + d.getNome() + " (" + d.getAno() + ")" + " (" + d.getNumVagas()
                        + " vagas)" + " Professor: " + d.getNomeDoProfessor());
            else
                System.out.println("Disciplina não encontrada.");
            esperarEnter();
        } else if (opcao == 4) {
            listarDisciplinas(sistema.getDisciplinas());
            esperarEnter();
        } else if (opcao == 5) {
            listarMatriculas(sistema.getAlunos(), sistema.getDisciplinas());
            esperarEnter();
        } else if (opcao == 6) {
            adicionarAluno();
            esperarEnter();
        } else if (opcao == 7) {
            listarAlunos(sistema.getAlunos());
            esperarEnter();
            alterarAluno();
            esperarEnter();
        } else if (opcao == 8) {
            System.out.println("Insira uma matrícula: ('-1' para voltar)");
            long codAluno = leia.nextLong();
            leia.nextLine();

            if (escape(codAluno))
                return true;

            Aluno a = sistema.buscarAluno(codAluno);
            if (a != null) {
                System.out.println("Aluno encontrado:");
                System.out.println("Matrícula: " + a.getNumMatricula());
                System.out.println("Nome: " + a.getNome());
                System.out.println("Nome da mãe: " + a.getNomeDaMae());
                System.out.println("Endereço: " + a.getEndereco());
                System.out.println("Média: " + a.getMedia());

                // disciplinas matriculadas
                NotaDisciplina[] disciplinas = a.getDiscMatriculadas();
                if (disciplinas != null && a.getNumDiscMatriculadas() > 0) {
                    System.out.print("Disciplinas: ");
                    boolean primeira = true;
                    for (NotaDisciplina disciplina : disciplinas) {
                        if (disciplina != null) {
                            if (!primeira) {
                                System.out.print(", ");
                            }
                            System.out.print(disciplina.getDisciplina().getNome());
                            primeira = false;
                        }
                    }
                    System.out.println();
                } else
                    System.out.println("Disciplinas: Nenhuma matriculada.");
            } else
                System.out.println("Aluno não encontrado.");

            esperarEnter();

        } else { // if opcao == 9;
            System.out.println("Listar por: ('-1' para voltar)");
            System.out.println("1 - Ordem de cadastro. ");
            System.out.println("2 - Ordem Crescente de nota.");
            System.out.println("3 - Alterar largura da tabela.");
            int opcaoListar = lerOpcao(1, 3);

            if (escape(opcaoListar))
                return true;

            if (opcaoListar == 1)
                listarAlunos(sistema.getAlunos());
            else if (opcaoListar == 2)
                listarAlunos(sistema.ordenarPorMedia(sistema.getAlunos()));
            else {
                System.out.println("Tamanho atual: " + larguraTabela);
                System.out.println("Insira a nova largura da tabela: ('-1' para voltar): ");
                int nova = leia.nextInt();
                leia.nextLine();

                if (escape(nova))
                    return true;

                while (nova < 10 || nova > 30) {
                    System.out.println("Tamanho inválido. Insira um tamanho entre 10 e 30: ");
                    nova = leia.nextInt();
                }
                ajustarLarguraTabela(nova);
            }
            esperarEnter();

        }
        return true;
    }

    static boolean adicionarAluno() {
        System.out.println("Insira o nome do aluno: ('voltar' para voltar)");
        String nome = leia.nextLine();

        if (escape(nome))
            return false;

        while (nome.isEmpty()) {
            System.out.println("Insira um nome válido: ");
            nome = leia.nextLine();
        }

        while (sistema.alunoExiste(nome)) {
            System.out.println("O nome do aluno já está cadastrado. Insira um nome diferente: ");
            nome = leia.nextLine();
        }

        System.out.println("Insira o nome da mãe do aluno: ('voltar' para voltar)");
        String nomeMae = leia.nextLine();
        if (escape(nomeMae))
            return false;
        while (nomeMae.isEmpty()) {
            System.out.println("Insira um nome válido: ");
            nomeMae = leia.nextLine();
        }

        System.out.println("Insira o endereço do aluno: ('voltar' para voltar)");
        String endereco = leia.nextLine();
        if (escape(endereco))
            return false;
        while (endereco.isEmpty()) {
            System.out.println("Insira um endereço válido: ('voltar' para voltar)");
            endereco = leia.nextLine();
        }

        Aluno aluno = new Aluno(sistema.gerarProxMatricula(), nome, nomeMae, endereco);
        sistema.inserir(aluno);
        return true;
    }

    static boolean adicionarDisciplina() {
        System.out.println("Insira o nome da disciplina: ('voltar' para voltar)");
        String nome = leia.nextLine();

        if (escape(nome))
            return false;

        while (nome.isEmpty()) {
            System.out.println("Insira um nome válido: ");
            nome = leia.nextLine();
        }

        System.out.println("Insira o ano da disciplina: ('-1' para voltar)");
        int ano = leia.nextInt();
        leia.nextLine();

        if (escape(ano))
            return false;

        while (ano < 0) {
            System.out.println("Insira um ano válido: ");
            ano = leia.nextInt();
            leia.nextLine();
        }

        System.out.println("Insira o número de vagas da disciplina: ('-1' para voltar)");
        int numVagas = leia.nextInt();
        leia.nextLine();

        if (escape(numVagas))
            return false;

        while (numVagas < 0) {
            System.out.println("Insira um número de vagas válido: ");
            numVagas = leia.nextInt();
            leia.nextLine();
        }

        System.out.println("Insira o nome do professor: ");
        String nomeDoProfessor = leia.nextLine();

        if (escape(nomeDoProfessor))
            return false;

        while (nomeDoProfessor.isEmpty()) {
            System.out.println("Insira um nome válido: ");
            nomeDoProfessor = leia.nextLine();
        }

        Disciplina disciplina = new Disciplina(sistema.gerarProxCodigo(), nome, ano, numVagas, nomeDoProfessor);
        return sistema.inserir(disciplina);
    }

    static boolean alterarAluno() {
        System.out.println("Insira a matrícula do aluno a ser alterado: ('-1' para voltar)");
        long matricula = leia.nextLong();
        leia.nextLine();

        if (escape(matricula))
            return false;

        Aluno a = sistema.buscarAluno(matricula);

        if (a != null) {

            System.out.println("----- MENU DE ALTERAÇÃO -----");
            System.out.println("1 - Alterar nome");
            System.out.println("2 - Alterar nome da mãe");
            System.out.println("3 - Alterar endereco");
            System.out.println("4 - Alterar nota");
            System.out.println("5 - Matricular aluno");
            System.out.println("0 - Voltar");

            System.out.println("< ESCOLHA UMA OPÇÃO PARA CONTINUAR >");
            int opcao = lerOpcao(0, 5);

            if (opcao == 1) {
                System.out.println("Insira o novo nome: ('voltar' para voltar)");
                String nome = leia.nextLine();
                if (escape(nome))
                    return false;
                if (!nome.isEmpty() && !sistema.alunoExiste(nome)) {
                    if (sistema.alterarNomeAluno(matricula, nome))
                        System.out.println("Nome alterado com sucesso.");
                    else
                        System.out.println("Erro ao alterar nome.");
                }
            } else if (opcao == 2) {
                System.out.println("Insira o novo nome da mãe: ('voltar' para voltar)");
                String nomeMae = leia.nextLine();
                if (escape(nomeMae))
                    return false;
                if (!nomeMae.isEmpty()) {
                    if (sistema.alterarNomeDaMaeAluno(matricula, nomeMae))
                        System.out.println("Nome da mãe alterado com sucesso.");

                    else
                        System.out.println("Erro ao alterar nome da mãe.");

                }
            } else if (opcao == 3) {
                System.out.println("Insira o novo endereço: ('voltar' para voltar)");
                String endereco = leia.nextLine();
                if (escape(endereco))
                    return false;
                if (!endereco.isEmpty()) {
                    if (sistema.alterarEnderecoAluno(matricula, endereco))
                        System.out.println("Endereço alterado com sucesso.");
                    else
                        System.out.println("Erro ao alterar endereço.");
                }
            } else if (opcao == 4) {
                if (a.getNumDiscMatriculadas() == 0) {
                    System.out.println("O aluno não está matriculado em nenhuma disciplina.");
                    return false;
                }
                listarDisciplinasMatriculadas(a.getDiscMatriculadas(), a.getNumDiscMatriculadas());
                System.out.println("Insira o código da disciplina: ('-1' para voltar)");
                int codigo = leia.nextInt();
                leia.nextLine();

                if (escape(codigo))
                    return false;

                if (sistema.alunoEstaMatriculado(a, codigo)) {

                    System.out.println("Insira a nova nota: ('-1' para voltar)");
                    double nota = leia.nextDouble();
                    if (escape(nota))
                        return false;
                    while (nota < 0 || nota > 10) {
                        System.out.println("Insira uma nota válida: ");
                        nota = leia.nextDouble();
                    }
                    if (sistema.alterarNotaDisciplina(matricula, codigo, nota))
                        System.out.println("Nota alterada com sucesso.");
                    else
                        System.out.println("Erro ao alterar nota.");
                    return true;
                } else
                    System.out.println("O aluno não está matriculado na disciplina selecionada.");
            } else if (opcao == 5) {
                matricularAluno(matricula);
            } else {
                esperarEnter();
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
        return false;
    }

    static boolean matricularAluno(long matricula) {

        if (escape(matricula))
            return false;

        if (sistema.alunoExiste(matricula)) {
            listarDisciplinas(sistema.getDisciplinas());
            System.out.println("Insira o código da disciplina: ");
            int codigo = leia.nextInt();
            leia.nextLine();

            if (sistema.matricularAluno(matricula, codigo))
                System.out.println("Aluno matriculado com sucesso.");
            else
                System.out.println("Erro ao matricular aluno.");
        } else
            System.out.println("Aluno não encontrado.");
        return false;
    }

    static void listarMatriculas(Aluno[] alunos, Disciplina[] disciplinas) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina != null) {
                System.out.println("Disciplina: " + disciplina.getNome());

                System.out.print("+------------+");
                int i = 0;
                while (i < larguraTabela + 2) {
                    System.out.print("-");
                    i++;
                }
                System.out.println("+--------+");

                System.out.printf("| %-10s | %-" + larguraTabela + "s | %-6s |\n", "Matrícula", "Nome", "Nota");

                System.out.print("+------------+");
                i = 0;
                while (i < larguraTabela + 2) {
                    System.out.print("-");
                    i++;
                }
                System.out.println("+--------+");

                boolean temAlunos = false;

                for (Aluno aluno : alunos) {
                    if (aluno != null) {
                        for (NotaDisciplina nd : aluno.getDiscMatriculadas()) {
                            if (nd != null && nd.getDisciplina().getCodigo() == disciplina.getCodigo()) {
                                System.out.printf("| %-10d | %-" + larguraTabela + "s | %-6.1f |\n",
                                        aluno.getNumMatricula(),
                                        ajustarTamanhoString(aluno.getNome(), larguraTabela),
                                        nd.getNota());
                                temAlunos = true;
                            }
                        }
                    }
                }

                if (!temAlunos) {
                    System.out.print("| ");
                    System.out.print("Nenhum aluno matriculado.");
                    int espacos = 10 + 3 + larguraTabela + 3 + 6;
                    while (espacos > 30) {
                        System.out.print(" ");
                        espacos--;
                    }
                    System.out.println(" |");
                }

                System.out.print("+------------+");
                i = 0;
                while (i < larguraTabela + 2) {
                    System.out.print("-");
                    i++;
                }
                System.out.println("+--------+\n");
            }
        }
    }

    static void listarAlunos(Aluno[] alunos) {
        System.out.printf(
                "%-10s | %-" + larguraTabela + "s | %-" + larguraTabela + "s | %-" + larguraTabela
                        + "s | %-21s | %-5s\n",
                "Matrícula", "Nome", "Nome da mãe", "Endereço", "Qntd de disciplinas", "Média");

        int total = 10 + 3 + larguraTabela * 3 + 3 + 21 + 3 + 5;
        // largura da coluna matricula + reticencias + (larguraTabela * (larguraNome,
        // larguraNomeMae, larguraEndereco)) + reticencias + larguraQntdDisciplinas +
        // larguraMedia
        // larguraTabela é as strings que terão sua largura da coluna variada, as outras
        // são predefinidas

        int i = 0;
        while (i < total) {
            System.out.print("-");
            i++;
        }
        System.out.println();

        for (Aluno a : alunos) {
            if (a != null) {
                System.out.printf(
                        "%-10d | %-" + larguraTabela + "s | %-" + larguraTabela + "s | %-" + larguraTabela
                                + "s | %-21d | %-5.2f\n",
                        a.getNumMatricula(),
                        ajustarTamanhoString(a.getNome(), larguraTabela),
                        ajustarTamanhoString(a.getNomeDaMae(), larguraTabela),
                        ajustarTamanhoString(a.getEndereco(), larguraTabela),
                        a.getNumDiscMatriculadas(),
                        a.getMedia());
            }
        }
    }

    static void listarDisciplinas(Disciplina[] disciplinas) {
        System.out.printf("%-10s | %-" + larguraTabela + "s | %-5s | %-10s | %-" + larguraTabela + "s\n",
                "Código", "Nome", "Ano", "Vagas", "Professor");

        int total = 10 + 3 + larguraTabela + 3 + 5 + 3 + 10 + 3 + larguraTabela;
        // largura da coluna código + reticencias + (larguraTabela + reticencias +
        // larguraVagas + larguraTabela
        // larguraTabela é as strings que terão sua largura da coluna variada, as outras
        // são predefinidas

        int i = 0;
        while (i < total) {
            System.out.print("-");
            i++;
        }
        System.out.println();

        for (Disciplina d : disciplinas) {
            if (d != null) {
                System.out.printf("%-10d | %-" + larguraTabela + "s | %-5d | %-10d | %-" + larguraTabela + "s\n",
                        d.getCodigo(),
                        ajustarTamanhoString(d.getNome(), larguraTabela),
                        d.getAno(),
                        d.getNumVagas(),
                        ajustarTamanhoString(d.getNomeDoProfessor(), larguraTabela));
            }
        }
    }

    static void listarDisciplinasMatriculadas(NotaDisciplina[] disciplinas, int numDisciplinas) {
        System.out.printf("%-10s | %-" + larguraTabela + "s | %-5s\n", "Código", "Nome", "Nota");

        for (int i = 0; i < numDisciplinas; i++) {
            if (disciplinas[i] != null) {
                NotaDisciplina d = disciplinas[i];
                System.out.printf("%-10d | %-" + larguraTabela + "s | %-5.1f\n",
                        d.getDisciplina().getCodigo(),
                        ajustarTamanhoString(d.getDisciplina().getNome(), larguraTabela),
                        d.getNota());
            }
        }
    }

    static void ajustarLarguraTabela(int nova) {

        larguraTabela = nova;
        System.out.println("Largura atualizada com sucesso.");
    }

    static String ajustarTamanhoString(String texto, int limite) {
        String resultado = "";
        int i = 0;

        while (i < texto.length() && i < limite - 3) {
            resultado += texto.charAt(i);
            i++;
        }

        if (texto.length() > limite - 3) {
            resultado += "...";
        }

        while (resultado.length() < limite) {
            resultado += " ";
        }

        return resultado;
    }

    static boolean escape(String entrada) {
        return entrada.equalsIgnoreCase("voltar");
    }

    static boolean escape(long entrada) {
        return entrada == -1;
    }

    static boolean escape(int entrada) {
        return entrada == -1;
    }

    static boolean escape(double entrada) {
        return entrada == -1;
    }

    static int lerOpcao(int min, int max) {
        int opcao = leia.nextInt();
        leia.nextLine();
        while (opcao < min || opcao > max) {
            System.out.println("Opção inválida. Insira uma opção válida: ");
            opcao = leia.nextInt();
            leia.nextLine();
        }
        return opcao;
    }

    static void esperarEnter() {
        System.out.println("< PRESSIONE ENTER PARA CONTINUAR >");
        leia.nextLine();
    }

}