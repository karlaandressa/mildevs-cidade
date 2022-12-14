package br.com.mesttra.cidade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.mesttra.cidade.connectionfactory.ConnectionFactory;
import br.com.mesttra.cidade.pojo.CidadePOJO;

public class CidadeDAO {         //TODOS OS METODOS DE INTERAÇAO COM O BANCO FICAM NO DAO
    
    private Connection conn;

    public CidadeDAO(){
        this.conn = ConnectionFactory.getConnection();
    }
    public boolean insereCidade(CidadePOJO cidade) throws SQLException{
        String sql = "insert into cidades.cidade(ddd, nome, num_habitantes, rendapercapita, capital, estado, nome_prefeito)"
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try{
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setInt(1, cidade.getDdd());
        stmt.setString(2, cidade.getNome());
        stmt.setInt(3, cidade.getNumHabitantes());
        stmt.setDouble(4, cidade.getRendaPerCapita());
        stmt.setBoolean(5, cidade.isCapital());
        stmt.setString(6, cidade.getEstado());
        stmt.setString(7, cidade.getNomePrefeito());
        stmt.execute();
        stmt.close();

        } catch(SQLException e){
            System.err.println("Erro ao realizar inserção");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean removeCidade(int ddd){

        String sql = "DELETE FROM cidades.cidade WHERE ddd = ?";

        PreparedStatement stmt; //ele apaga depois q cria a linha 67.
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, ddd);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Erro ao realizar remoção");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<CidadePOJO> listaCidades(){
        String sql = "SELECT * FROM cidades.cidade";
        List<CidadePOJO> listaRetornada = new ArrayList<>();
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            populaLista(listaRetornada, rs);

        } catch(SQLException e){
            System.err.println("Erro ao listar cidade");
            System.err.println(e.getMessage());
        }
        return listaRetornada; 
    }
    

//2. Crie no seu DAO um método que é capaz de retornar uma cidade com base no seu número de ddd.
    public CidadePOJO consultaCidade(int ddd){
        String sql = "SELECT * FROM cidades.cidade where ddd = ?"; // ? -> determinada variavel

        PreparedStatement stmt;
        try{
            stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, ddd);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                CidadePOJO cidadeRetornada = new CidadePOJO();
                cidadeRetornada.setDdd(rs.getInt("ddd"));
                cidadeRetornada.setNome(rs.getString("nome"));
                cidadeRetornada.setNumHabitantes(rs.getInt("num_habitantes"));
                cidadeRetornada.setRendaPerCapita(rs.getDouble("rendapercapita"));
                cidadeRetornada.setCapital(rs.getBoolean("capital"));
                cidadeRetornada.setEstado(rs.getString("estado"));
                cidadeRetornada.setNomePrefeito(rs.getString("nome_prefeito"));
            }
        }catch(SQLException e){
            System.err.println("Erro ao consultar cidade");
            System.err.println(e.getMessage());
        }
        
        return null;
    }

//3. Crie um método que é capaz de pesquisar cidades cujos nomes se iniciam por um texto passado como parâmetro.
    public List<CidadePOJO> listaCidadesQueComecamCom(String inicio){
        List<CidadePOJO> cidades = new ArrayList<CidadePOJO>();
        String sql = "SELECT * FROM cidades.cidade where nome like ?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, inicio + '%');
            ResultSet rs = stmt.executeQuery();
            populaLista(cidades, rs);

        } catch (Exception e) {
            System.err.println("Erro ao listar cidades que começam com: " + inicio);
            System.err.println(e.getMessage());
        }
        return cidades;
    }

//4. Crie um método que é capaz de listar cidades filtradas pela sigla de estado.
    public List<CidadePOJO> listaCidadePorEstado(String estado){
        List<CidadePOJO> cidades = new ArrayList<CidadePOJO>();
        String sql = "SELECT * FROM cidades.cidade where estado = ?";

        PreparedStatement stmt;
        try{
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            populaLista(cidades, rs);
        } catch (SQLException e) {
            System.err.println("Erro ao listar cidades do Estado: " + estado);
            System.err.println(e.getMessage());
        }
        return cidades;
    }

//5. Crie um método que recebe a sigla de um estado e retorna a quantidade de cidades daquele estado.
    public int contaCidadesPorEstado(String estado){
        String sql = "SELECT count(*) as contagem from cidades.cidade where estado = ?";

        PreparedStatement stmt;
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            rs.next();
        } catch (Exception e) {
            System.err.println("Erro ao contar Cidades do Estado: " + estado);
            System.err.println(e.getMessage());
        }
        return 0;
    }

//6. Provavelmente neste momento você já repetiu bastante o while (rs.next) e a construção da cidade, que tal criar 
//um método privado que seja genérico e utilizá-lo em seus outros métodos de listagem?
    private void populaLista(List<CidadePOJO> listaRetornada, ResultSet rs) throws SQLException {
        while (rs.next()){
            CidadePOJO cidadeRetornada = new CidadePOJO();

            cidadeRetornada.setDdd(rs.getInt("ddd"));
            cidadeRetornada.setNome(rs.getString("nome"));
            cidadeRetornada.setNumHabitantes(rs.getInt("num_habitantes"));
            cidadeRetornada.setRendaPerCapita(rs.getDouble("rendapercapita"));
            cidadeRetornada.setCapital(rs.getBoolean("capital"));
            cidadeRetornada.setEstado(rs.getString("estado"));
            cidadeRetornada.setNomePrefeito(rs.getString("nome_prefeito"));

            listaRetornada.add(cidadeRetornada);
        }
    }

//7. Crie um método que filtra cidades pela coluna capital, onde o valor do filtro é passado como parâmetro.
    public List<CidadePOJO> listaCidadesFiltradasPorCapital(boolean ehCapital){
        List<CidadePOJO> cidades = new ArrayList<CidadePOJO>();
        String sql = "SELECT * FROM cidades.cidade WHERE capital = ?";

        PreparedStatement stmt;
        try {
            stmt = this.conn.prepareStatement(sql);
            stmt.setBoolean(1, ehCapital);
            ResultSet rs = stmt.executeQuery();
            populaLista(cidades, rs);
        } catch (Exception e) {
            System.err.println("Erro ao listar cidades filtradas pela coluna capital");
            System.err.println(e.getMessage());
        }
        return cidades;
    }
//8. Modifique seu menu (Programa Principal) para ser capaz de utilizar as novas funcionalidades.
}
