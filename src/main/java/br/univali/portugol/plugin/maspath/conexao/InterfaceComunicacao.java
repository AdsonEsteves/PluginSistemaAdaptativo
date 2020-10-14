package br.univali.portugol.plugin.maspath.conexao;

import java.util.ArrayList;

import org.json.JSONObject;

import br.univali.portugol.plugin.maspath.dataentities.Student;

public class InterfaceComunicacao {

    public InterfaceComunicacao() {
        super();
    }    
    public String criarAluno(Student aluno)
    {
        String response = ConexaoHTTP.fazerRequest("/interface/contas", "POST", new JSONObject(aluno.toString()));
        return response.toString();
    }

    public static void main(String[] args) {
        Student student = new Student("MANBAKI", "bruh", "MMMMMM", "mulher", 16, "ensino médio", new ArrayList<>());
        InterfaceComunicacao intercom = new InterfaceComunicacao();
        System.out.println(intercom.criarAluno(student));        
    }
}
