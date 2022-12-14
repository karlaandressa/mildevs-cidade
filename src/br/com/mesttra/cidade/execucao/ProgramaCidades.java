package br.com.mesttra.cidade.execucao;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import br.com.mesttra.cidade.dao.CidadeDAO;
import br.com.mesttra.cidade.pojo.CidadePOJO;

public class ProgramaCidades {

    private static void insercao(CidadeDAO cidadeDao, Scanner sc) throws SQLException {
        System.out.println("-- Inserção de Cidade --");
        System.out.println("Digite o DDD: ");
        int ddd=sc.nextInt();
        sc.nextLine();
        
        System.out.println("Digite o nome da Cidade: ");
        String nome=sc.nextLine();
        
        System.out.println("Digite a quantidade de habitantes: ");
        int numHabitantes=sc.nextInt();
        
        System.out.println("Digite a renda per capita: ");
        double renda=sc.nextDouble();
        
        System.out.println("Digite 0 caso seja uma capital e 1 caso não seja: ");
        Boolean capital = sc.nextInt() == 0;
        sc.nextLine();

        System.out.println("Digite a sigla do estado: ");
        String estado=sc.nextLine();
                
        System.out.println("Digite o nome do Prefeito(a): ");
        String nomePrefeito=sc.nextLine();
        
        CidadePOJO cidadeInserida = new CidadePOJO(ddd, nome, numHabitantes, renda, false, estado, nomePrefeito);
        if(cidadeDao.insereCidade(cidadeInserida)){
            System.out.println("Cidade inserida com sucesso!");
        }
    }
    private static void remocao(CidadeDAO cidadeDao, Scanner sc) {
        System.out.println("-- Remoção de Cidade --");
        
        System.out.println("Digite o DDD: ");
        int ddd = sc.nextInt();
        
        if(cidadeDao.removeCidade(ddd)){
            System.out.println("Cidade removida com sucesso!");
        };

    }   
   
    public static void main(String[] args) throws SQLException {
        
        CidadeDAO CidadeDao = new CidadeDAO();
       /*  CidadePOJO manaus = new CidadePOJO(92, "Manaus", 50000, 1000.00, false, "AM", "Davizinho");
        CidadeDao.insereCidade(manaus);
        CidadeDao.removeCidade(92);
        System.out.println("FIM"); */
        Scanner sc = new Scanner(System.in);

        int opcao = 0;
        do {
            System.out.println("-- GESTÃO DE CIDADES --");
            System.out.println("1 - Para inserir uma cidade");
            System.out.println("2 - Para remover uma cidade pelo DDD");
            System.out.println("3 - Listar cidades");
            System.out.println("4 - Para consultar uma cidade pelo DDD");
            System.out.println("5 - Listar cidades que começam com um texto digitado");
            System.out.println("6 - Listar cidades por estado");
            System.out.println("7 - Exibir a quantidade de cidades por Estado");
            System.out.println("8 - Listar cidades que sejam capitais ou não");
            System.out.println("-1 - Para encerrar o programa");
            System.out.println("DIGITE A OPÇÃO ESCOLHIDA: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    //inserçao
                    try{   // ou a trativa podia ser feita em cada variavel 
                    insercao(CidadeDao, sc);
                    } catch(InputMismatchException e){
                        System.err.println("Erro ao ler os dados da cidade, refaça a operação"/*+e.getMessage()*/);
                        sc.nextLine();
                    }
                    break;
                case 2:
                try{   // ou a trativa podia ser feita em cada variavel 
                    remocao(CidadeDao, sc);
                    } catch(InputMismatchException e){
                        System.err.println("Erro ao ler DDD, digite um numero inteiro.");
                        sc.nextLine();
                    }
                    break;
                case 3:
                    listagem(CidadeDao);
                    break;
                case 4:
                    consultaCidade(CidadeDao, sc);
                    break;
                case 5:
                    listaCidadesQueComecamCom(CidadeDao, sc);
                    break;
                case 6:
                    listarCidadesPorEstado(CidadeDao, sc);
                    break;
                case 7:
                    contaCidadesPorEstados(CidadeDao, sc);
                    break;
                case 8:
                    listarCapitaisOuInterior(CidadeDao, sc);
                    break;
                default:
                    break;
            }
        } while (opcao != -1);
    
        System.out.println("Obrigado por utilizar nosso sistema!");
        
    }



private static void listarCapitaisOuInterior(CidadeDAO CidadeDao, Scanner sc) {
    System.out.println("-- Listar cidades que sejam Capitais ou não");
    System.out.println("Digite 0 para listar capitais e 1 para listar cidades do interior");
    boolean capital = sc.nextInt() == 0;
    List<CidadePOJO> cidadesNoBd = CidadeDao.listaCidadesFiltradasPorCapital(capital);
    for(CidadePOJO cidade : cidadesNoBd){
        System.out.println(cidade);
    }
}

private static void contaCidadesPorEstados(CidadeDAO CidadeDao, Scanner sc) {
    System.out.println("--Contagem de Cidades por Estado--");
    System.out.println("Digita a sigla do estado desejado: ");
    String estado = sc.nextLine();
    int quantidadeCidades = CidadeDao.contaCidadesPorEstado(estado);
    System.out.println("Existem " + quantidadeCidades + " no Estado buscado");
}

private static void listaCidadesQueComecamCom(CidadeDAO CidadeDao, Scanner sc) {
    sc.nextLine();
    System.out.println(" -- Listagem de Cidades que iniciam por um texto --");
    System.out.println("Digite um texto para a pesquisa: ");
    String textoLido=sc.nextLine();
    List<CidadePOJO> cidadesNoBd = CidadeDao.listaCidadesQueComecamCom(textoLido);
    for(CidadePOJO cidade : cidadesNoBd){
        System.out.println(cidade);
    }
}

private static void consultaCidade(CidadeDAO cidadeDao, Scanner sc){
    System.out.println(" -- Consultar uma cidade pelo DDD --");
    System.out.println("Digite o DDD desejado: ");
    int dddBusca = sc.nextInt();
    CidadePOJO cidade = cidadeDao.consultaCidade(dddBusca);
    if(cidade != null){
        System.out.println(cidade);
        return;
    }
    System.out.println("A Cidade não foi encontrada");
}

private static void listarCidadesPorEstado(CidadeDAO CidadeDao, Scanner sc){
    sc.nextLine();
    System.out.println("-- Listar Cidades por Estado --");
    System.out.println("Digite a sigla do Estado desejado: ");
    String sigla = sc.nextLine();
    List<CidadePOJO> cidadesNoBd = CidadeDao.listaCidadePorEstado(sigla);

    if(cidadesNoBd.size() == 0){
        System.out.println("Nenhuma cidade encntrada para o filtro");
        return;
    }
    for(CidadePOJO cidade : cidadesNoBd){
        System.out.println(cidade);
    }
}

private static void listagem(CidadeDAO CidadeDao) {
    List<CidadePOJO> cidadesNoBd = CidadeDao.listaCidades();
    for(CidadePOJO cidade : cidadesNoBd){
        System.out.println(cidade);
    }
}

}
