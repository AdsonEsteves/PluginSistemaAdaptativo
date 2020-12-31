package br.univali.portugol.plugin.maspath.conexao;

import java.util.ArrayList;

import org.json.JSONObject;

import br.univali.portugol.plugin.maspath.dataentities.Content;
import br.univali.portugol.plugin.maspath.dataentities.Student;

public class InterfaceComunicacao {

    public InterfaceComunicacao() {
        super();
    }    
    public String criarAluno(String aluno)
    {
        // String response = ConexaoHTTP.fazerRequest("/interface/conteudos", "POST", new JSONObject(aluno));
        String response = ConexaoHTTP.fazerRequest("/tutor/requisitaConteudos", "GET", null);
        return response.toString();
    }
    

    public static void main(String[] args) {
        Student student = new Student("Rafaelf", "edwdwd", "trgrtgrt", "homem", 24, "ensino médio", new ArrayList<String>(){{
            add("Mangá");
            add("Memes");
            add("Programação");
        }});
        Content content = new Content("Xadrez", 1, "Matrizes", 1, "graduação", true, "Analise", new ArrayList<String>(){{
            add("Tabuleiro");
            add("Jogos");
            add("Entrada e Saida");
        }}, "link");
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
        String dados = "{"+
            "'conteudoRelacionado':'JogoDaVelha',"+
            "'valorRelacao': -25,"+
            "'conteudo':"+
            content.toString()+
            "}";
        InterfaceComunicacao intercom = new InterfaceComunicacao();
        System.out.println(intercom.criarAluno(dados));        
    }
}
