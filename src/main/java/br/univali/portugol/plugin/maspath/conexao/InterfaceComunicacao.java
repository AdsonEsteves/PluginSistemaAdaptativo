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
        JSONObject response = ConexaoHTTP.fazerRequest("/interface/contas", "POST", new JSONObject(aluno.toString()));
        System.out.println(response.toString());
        return "SUCESSO?";
    }

    public static void main(String[] args) {
        Student student = new Student("MAYONESE", "COOL", "MMMMMM", "homem", 21, "ensino médio", new ArrayList<>());
        InterfaceComunicacao intercom = new InterfaceComunicacao();
        System.out.println(intercom.criarAluno(student));        
    }
}
