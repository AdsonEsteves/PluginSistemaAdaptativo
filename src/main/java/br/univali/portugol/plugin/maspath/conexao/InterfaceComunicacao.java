package br.univali.portugol.plugin.maspath.conexao;

import java.util.ArrayList;


import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.portugol.plugin.maspath.dataentities.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InterfaceComunicacao {

    public InterfaceComunicacao() {
        super();
    }    
    
    public String criarAluno(String aluno)
    {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos", "GET", null);
        return response.toString();
    }
    
    public String fazerLogin(String dados) throws Exception
    {
        String response = ConexaoHTTP.fazerRequest("/interface/login", "GET", new ObjectMapper().readTree(dados));
        return response.toString();
    }
    
    public String deslogar(String porta)
    {
        String response = ConexaoHTTP.fazerRequest("/interface/logout/"+porta, "POST", null);
        return response.toString();
    }

    public String requisitaConteudos(String porta)
    {
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos/"+porta, "GET", null);
        return response.toString();
    }
    

    public static void main(String[] args) throws InterruptedException, JsonProcessingException, Exception {
        Student student = new Student("Rafaelf", "edwdwd", "trgrtgrt", "homem", 24, "ensino médio", new ArrayList<String>(){{
            add("Mangá");
            add("Memes");
            add("Programação");
        }});
        Content content = new Content("Xadrez", "Descricao", 1, "Matrizes", 1, "graduação", true, "Analise", new ArrayList<String>(){{
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
            "'usuario':'l4yhU9z2bx',"+
            "'senha': 'wMCPoOVLAw',"+
            "}";
        InterfaceComunicacao intercom = new InterfaceComunicacao();
        String porta = intercom.fazerLogin(dados);
        System.out.println("PORTA: "+porta);
        System.out.println(intercom.requisitaConteudos(porta));
        Thread.sleep(2000);
        System.out.println(intercom.deslogar(porta));
    }
}
