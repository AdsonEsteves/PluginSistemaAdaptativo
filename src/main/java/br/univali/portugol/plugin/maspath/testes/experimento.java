package br.univali.portugol.plugin.maspath.testes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Student;

public class experimento {

    public static void main(String[] args) throws Exception {

        for (int a = 0; a < 2; a++) {
            cria_aluno();
            faz_login();
            for (int i = 0; i < topicos1d.length; i++) {
                pede_recomendacao(i);
            }            
            desloga();            
        }
        imprime_dados();
    }

    static InterfaceComunicacao intercom = InterfaceComunicacao.getInstance();

    static Student student;

    static String[] tags = {
        "carros", "musica", "animes", "desenhos", "animacao", "jogos", "geografia",
        "matematica", "linguas", "biologia", "animais", "pets", "imagens", "memes",
        "mitologia", "marvel", "dc", "monstros", "youtube", "comedia", "cultura",
        "filmes", "superherois", "historia", "esportes", "ciencia", "brinquedos",
        "internacional", "tecnologia", "comidas", "livros"
        }
    ;
    static String[][] topicos = {
        {"t1", "t2", "t3"},
        {"t4", "t5", "t6"},
        {"t7", "t8", "t9"},
        {"t10", "t11", "t12"},
        {"t13", "t14", "t15"}                            
    };
    static String[] topicos1d = {
        "t1", "t2", "t3",
        "t4", "t5", "t6",
        "t7", "t8", "t9",
        "t10", "t11", "t12",
        "t13", "t14", "t15",                         
    };

    static int niveis = 5;

    static String[] complexidade = {"Matemática", "Cognitiva", "Algoritmo", "Codigo"};

    static String[] generos = {"Masculino", "Feminino", "Transsexual", "Outro"};

    static String[] niveisEdu = {"Fundamental", "EnsinoMedio", "Graduacao"};
    

    
    private static void desloga() throws Exception {
        intercom.deslogar();
    }

    private static void faz_login() throws Exception {
        intercom.fazerLogin("{" + "\"usuario\":\"" + student.getName() + "\"," + "\"senha\": \"" + student.getPassword() + "\"" + "}");
    }

    private static void imprime_dados() {
        System.out.println(intercom.imprimir_dados());
    }

    private static void registra_na_trilha(String nome) throws Exception {
        String resposta = intercom.atualizaTrilha("{\"nomeConteudo\":\""+nome+"\"}");
        System.out.println(resposta);
    }

    private static void pede_recomendacao(int topico_index) throws Exception {
        String conteudos_string_json = intercom.requisitaConteudos();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode conteudos = mapper.readTree(conteudos_string_json).get(0);

        for (JsonNode conteudo : conteudos) {
            if(conteudo.get("topic").asText().equals(topicos1d[topico_index]))
            {
                registra_na_trilha(conteudo.get("name").asText());
                return;
            }
        }
        System.out.println("Sem conteudo do topico: "+topicos1d[topico_index]);        
        System.exit(0);
    }

    public static void cria_aluno() {
        Random generator = new Random();

        String name = randomString();
        String password = randomString();
        String genero = generos[generator.nextInt(generos.length)];
        String nivelEdu = niveisEdu[generator.nextInt(niveisEdu.length)];
        int idade = generator.nextInt(30)+12;
        String[] links = {"https://cdn.discordapp.com/attachments/571157550956019741/800619655366574091/12243585_1694508097447198_1004266710788666891_n.jpg",
                        "https://cdn.discordapp.com/attachments/571157550956019741/800619703089365002/21077295_1119616784841346_734019202998452151_n.jpg",
                        "https://cdn.discordapp.com/attachments/571157550956019741/800619727889629264/1521285067403.jpg"};
        String avatar = links[generator.nextInt(3)];

        student = new Student(name, password, avatar, genero, idade, nivelEdu, Arrays.asList(returnRandomTags()), new ArrayList<String>());

        intercom.criarAluno(student);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
        
        return generatedString;
    }

    private static String[] returnRandomTags()
    {
        List<String> selectedTags = new ArrayList<>();

        int randtags = (int)(Math.random() * 2) + 3; 

        for (int i = 0; i < randtags; i++) {
            String chosenTag = tags[new Random().nextInt(tags.length)];
            if(selectedTags.contains(chosenTag))
            {
                i--;
            }
            else{
                selectedTags.add(chosenTag);
            }
        }
        return selectedTags.toArray(new String[selectedTags.size()]);
    }

}
