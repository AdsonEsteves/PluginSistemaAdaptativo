package br.univali.portugol.plugin.maspath.conexao;

import java.util.ArrayList;

import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.portugol.plugin.maspath.dataentities.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InterfaceComunicacao {

    String porta = null;
    public static InterfaceComunicacao interCom;

    public InterfaceComunicacao() {
        super();
    }

    public static InterfaceComunicacao getInstance() {
        if (interCom == null) {
            interCom = new InterfaceComunicacao();
        }
        return interCom;
    }

    public String criarAluno(Student aluno) {
        String response = ConexaoHTTP.fazerRequest("/interface/contas", "POST", new ObjectMapper().valueToTree(aluno));
        return response.toString();
    }

    public Student fazerLogin(String dados) throws Exception {
        String response = ConexaoHTTP.fazerRequest("/interface/login", "GET", new ObjectMapper().readTree(dados));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode login = mapper.readTree(response);
        this.porta = login.get("porta").asText();
        return mapper.treeToValue(login.get("estudante"), Student.class);
    }

    public String deslogar() {
        String response = ConexaoHTTP.fazerRequest("/interface/logout/" + porta, "POST", null);
        return response.toString();
    }

    public String requisitaConteudos() {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos/" + porta, "GET", null);
        return response.toString();
    }

    public String requisitaConteudos2(JsonNode dadosDeConteudos) {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos/", "POST", dadosDeConteudos);
        return response.toString();
    }

    public String requisitaTrilha() {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaTrilha/" + porta, "GET", null);
        return response.toString();
    }

    public String atualizaTrilha(String jsonNomeConteudo) throws Exception {
        String response = ConexaoHTTP.fazerRequest("/tutor/atualizarTrilha/" + porta, "GET",
                new ObjectMapper().readTree(jsonNomeConteudo));
        return response.toString();
    }

    public String requisitaInfo() {
        String response = ConexaoHTTP.fazerRequest("/tutor/getInfo", "GET", null);
        return response.toString();
    }

    public String requisitaBuscaConteudos(JsonNode dadosDeConteudos) {
        String response = ConexaoHTTP.fazerRequest("/tutor/buscarConteudo", "POST", dadosDeConteudos);
        return response.toString();
    }

    public String imprimir_dados() {
        String response = ConexaoHTTP.fazerRequest("/interface/imprimirDados", "GET", null);
        return response.toString();
    }

    public static void main(String[] args) throws InterruptedException, JsonProcessingException, Exception {
        Student student = new Student("Raul", "123456",
                "https://cdn.discordapp.com/attachments/571157550956019741/800619727889629264/1521285067403.jpg",
                "Masculino", 30, 6, new ArrayList<String>() {
                    {
                        add("tecnologia");
                        add("pets");
                    }
                }, new ArrayList<>(), 10.0, 0.0);
        // Content content = new Content("nome", "descricao", 5, "Tópico",
        // "Complexidade", true, 5, new ArrayList<String>(){{
        // add("Tabuleiro");
        // add("Jogos");
        // add("Entrada e Saida");
        // }}, "link", "link2");
        // String dados = "{"+
        // "'nome':'Andre',"+
        // "'cliques':["+
        // "{'componente':'Executar', 'timestamp':'1603315704', 'IP':'177.132.153.244',
        // 'modulo':'Exemplo'},"+
        // "{'componente':'Debug', 'timestamp':'1603315704', 'IP':'177.132.153.244',
        // 'modulo':'OGPor'},"+
        // "{'componente':'Debug', 'timestamp':'1603315704', 'IP':'177.132.153.244',
        // 'modulo':'Exemplo'},"+
        // "{'componente':'Executar', 'timestamp':'1603315704', 'IP':'177.132.153.244',
        // 'modulo':'Conteudo'}"+
        // "]"+
        // "'conteudo':["+
        // "{'nome':'VETOR CARRO', 'timestamp':'1603315704', 'IP':'177.132.153.244',
        // 'topico':'VETORES'}"+
        // "]}";

        // String dados = "{"+
        // "'nome':'Andre',"+
        // "'conteudo':"+
        // "{'nome':'VETOR CARRO', 'topico':'VETORES'}"+
        // "}";
        // String dados = "{"+
        // "'conteudoRelacionado':'JogoDaVelha',"+
        // "'valorRelacao': -25,"+
        // "'conteudo':"+
        // content.toString()+
        // "}";
        String dados = "{" +
                "\"usuario\":\"5heJTV1BJo\"," +
                "\"senha\": \"AoKMP8ulNg\"" +
                "}";
        InterfaceComunicacao intercom = InterfaceComunicacao.getInstance();
        String estudante = intercom.criarAluno(student);

        // Thread.sleep(2000);
        System.out.println(estudante);
    }
}
