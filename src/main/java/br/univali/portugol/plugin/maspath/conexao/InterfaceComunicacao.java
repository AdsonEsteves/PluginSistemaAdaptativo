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
    
    public static InterfaceComunicacao getInstance()
    {
        if(interCom == null)
        {
            interCom = new InterfaceComunicacao();
        }        
        return interCom;
    }
    
    public String criarAluno(String aluno)
    {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos", "GET", null);
        return response.toString();
    }
    
    public String fazerLogin(String dados) throws Exception
    {
        String response = ConexaoHTTP.fazerRequest("/interface/login", "GET", new ObjectMapper().readTree(dados));
        this.porta = response.toString();
        return response.toString();
    }
    
    public String deslogar()
    {
        String response = ConexaoHTTP.fazerRequest("/interface/logout/"+porta, "POST", null);
        return response.toString();
    }

    public String requisitaConteudos()
    {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos/"+porta, "GET", null);
        return response.toString();
    }
    
    public String requisitaTrilha()
    {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaTrilha/"+porta, "GET", null);
        return response.toString();
    }

    public static void main(String[] args) throws InterruptedException, JsonProcessingException, Exception {
        Student student = new Student("Rafaelf", "edwdwd", "trgrtgrt", "homem", 24, "ensino médio", new ArrayList<String>(){{
            add("Mangá");
            add("Memes");
            add("Programação");
        }});
        Content content = new Content("nome", "descricao", 5, "Tópico", "Complexidade", true, 5, new ArrayList<String>(){{
            add("Tabuleiro");
            add("Jogos");
            add("Entrada e Saida");
        }}, "link", "link2");
        // String dados = "{"+
        //     "'nome':'Andre',"+
        //     "'cliques':["+
        //     "{'componente':'Executar', 'timestamp':'1603315704', 'IP':'177.132.153.244', 'modulo':'Exemplo'},"+
        //     "{'componente':'Debug', 'timestamp':'1603315704', 'IP':'177.132.153.244', 'modulo':'OGPor'},"+
        //     "{'componente':'Debug', 'timestamp':'1603315704', 'IP':'177.132.153.244', 'modulo':'Exemplo'},"+
        //     "{'componente':'Executar', 'timestamp':'1603315704', 'IP':'177.132.153.244', 'modulo':'Conteudo'}"+
        //     "]"+
        //     "'conteudo':["+
        //     "{'nome':'VETOR CARRO', 'timestamp':'1603315704', 'IP':'177.132.153.244', 'topico':'VETORES'}"+
        //     "]}";

        // String dados = "{"+
        //     "'nome':'Andre',"+
        //     "'conteudo':"+
        //     "{'nome':'VETOR CARRO', 'topico':'VETORES'}"+
        //     "}";
        // String dados = "{"+
        //     "'conteudoRelacionado':'JogoDaVelha',"+
        //     "'valorRelacao': -25,"+
        //     "'conteudo':"+
        //     content.toString()+
        //     "}";
        String dados = "{"+
            "\"usuario\":\"euGJ14ZALG\","+
            "\"senha\": \"8pm1VRR3g3\""+
            "}";
        InterfaceComunicacao intercom = InterfaceComunicacao.getInstance();
        intercom.porta = intercom.fazerLogin(dados);
        System.out.println("PORTA: "+intercom.porta);
        ObjectMapper mapper = new ObjectMapper();
        String trilhaJson = intercom.requisitaConteudos();
        System.out.println(trilhaJson);
        JsonNode trilha = mapper.readTree(trilhaJson).get(0);
        for (JsonNode jsonNode : trilha) {
            Content conteudo = mapper.treeToValue(jsonNode, Content.class);
            System.out.println(conteudo.toString());
        }
        
        Thread.sleep(2000);
        System.out.println(intercom.deslogar());
    }
}
