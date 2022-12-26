package br.univali.portugol.plugin.maspath.testes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Student2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class experimento2 {

    public static void main(String[] args) throws Exception {

        List<String> fileLines = new ArrayList<>();
        fileLines.add(
                "nome,senha,genero,idade,nivelEducacional,lo1,lo2,lo3,lo4,lo5,lo6,lo7,lo8,lo9,lo10,lo11,lo12,lo13,lo14,lo15");
        for (int a = 0; a < 1000; a++) {
            fileLines.add(cria_aluno());
        }
        // imprime_dados();

        try {
            givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(new File(saveFolder + "students.csv"), fileLines);
        } catch (Exception e) {
            System.out.println("ERRO DE PRINT" + e);
        }
    }

    static String saveFolder = "C:\\Users\\shina\\Desktop\\SACIPTest\\";

    static List<Integer> redo = new ArrayList<>();

    static InterfaceComunicacao intercom = InterfaceComunicacao.getInstance();

    static Student2 student;

    static String[] tags = {
            "carros", "musica", "desenhos", "jogos",
            "matematica", "linguas", "biologia", "animais",
            "comedia", "cultura", "memes", "geografia",
            "filmes", "superherois", "historia", "esportes",
            "internacional", "tecnologia", "comidas", "livros"
    };
    static String[][] topicos = {
            { "t1", "t2", "t3" },
            { "t4", "t5", "t6" },
            { "t7", "t8", "t9" },
            { "t10", "t11", "t12" },
            { "t13", "t14", "t15" }
    };
    static String[] topicos1d = {
            "t1", "t2", "t3",
            "t4", "t5", "t6",
            "t7", "t8", "t9",
            "t10", "t11", "t12",
            "t13", "t14", "t15",
    };

    static int niveis = 5;

    static String[] complexidade = { "Matemática", "Cognitiva", "Algoritmo", "Codigo" };

    static String[] generos = { "Masculino", "Feminino", "Transsexual", "Outro" };

    static String[] niveisEdu = { "Fundamental", "EnsinoMedio", "Graduacao" };

    private static void desloga() throws Exception {
        intercom.deslogar();
    }

    private static void faz_login() throws Exception {
        intercom.fazerLogin("{" + "\"usuario\":\"" + student.getName() + "\"," + "\"senha\": \"" + student.getPassword()
                + "\"" + "}");
    }

    private static void imprime_dados() {
        System.out.println(intercom.imprimir_dados());
    }

    private static void registra_na_trilha(String nome) throws Exception {
        String resposta = intercom.atualizaTrilha("{\"nomeConteudo\":\"" + nome + "\"}");
        System.out.println(resposta);
    }

    private static void requisita_recomendacao(int nivel) throws Exception {
        for (int j = 1; j <= 3; j++) {
            if (!pede_recomendacao((nivel * 3) + j)) {
                redo.add(j);
            }
        }
        if (!redo.isEmpty()) {
            for (Integer r : redo) {
                pede_recomendacao((nivel * 3) + r);
            }
            redo.clear();
        }
    }

    private static boolean pede_recomendacao(int topico_index) throws Exception {
        String conteudos_string_json = intercom.requisitaConteudos();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode conteudos = mapper.readTree(conteudos_string_json).get(0);
        int t = (int) (Math.random() * 3) + 1;
        int nivel = (topico_index / 3) + 1;
        for (JsonNode conteudo : conteudos) {
            if (conteudo.get("topic").asText().equals(topicos1d[topico_index - 1])) {
                registra_na_trilha(conteudo.get("name").asText());
                return true;
            }
        }
        System.out.println("Sem conteudo do topico: " + topicos1d[topico_index]);
        return false;
        // System.exit(0);
    }

    public static String cria_aluno() {
        Random generator = new Random();
        ObjectMapper mapper = new ObjectMapper();
        String name = randomString();
        String password = randomString();
        String genero = generos[generator.nextInt(generos.length)];
        String nivelEdu = niveisEdu[generator.nextInt(niveisEdu.length)];
        int idade = generator.nextInt(30) + 12;
        String[] links = {
                "https://cdn.discordapp.com/attachments/571157550956019741/800619655366574091/12243585_1694508097447198_1004266710788666891_n.jpg",
                "https://cdn.discordapp.com/attachments/571157550956019741/800619703089365002/21077295_1119616784841346_734019202998452151_n.jpg",
                "https://cdn.discordapp.com/attachments/571157550956019741/800619727889629264/1521285067403.jpg" };
        String avatar = links[generator.nextInt(3)];
        List<String> preferences = Arrays.asList(returnRandomTags());
        List<String> trilha = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            ObjectNode json = new ObjectMapper().createObjectNode();
            json.put("topico", "t" + i);
            json.set("tags", mapper.valueToTree(preferences));
            String resposta = intercom.requisitaConteudos2(json);
            trilha.add(resposta);
            System.out.println("\n" + resposta);
        }

        student = new Student2(name, password, avatar, genero, idade, nivelEdu, preferences,
                trilha.get(0), trilha.get(1), trilha.get(2), trilha.get(3), trilha.get(4), trilha.get(5), trilha.get(6),
                trilha.get(7), trilha.get(8), trilha.get(9), trilha.get(10), trilha.get(11), trilha.get(12),
                trilha.get(13),
                trilha.get(14));

        // "nome, senha, genero, idade, nivelEducacional, lo1, lo2, lo3, lo4, lo5, lo6,
        // lo7, lo8, lo9, lo10, lo11, lo12, lo13, lo14, lo15"

        return name + "," + password + "," + genero + "," + idade + "," + nivelEdu + "," + trilha.get(0) + ","
                + trilha.get(1) + "," + trilha.get(2) + "," + trilha.get(3) + "," + trilha.get(4) + "," + trilha.get(5)
                + "," + trilha.get(6) + "," + trilha.get(7) + "," + trilha.get(8) + "," + trilha.get(9) + ","
                + trilha.get(10) + "," + trilha.get(11) + "," + trilha.get(12) + "," + trilha.get(13) + ","
                + trilha.get(14);

        // SendStudentToDB(student);

    }

    public static void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(File fileName, List<String> lines)
            throws IOException {
        if (!fileName.exists()) {
            fileName.getParentFile().mkdirs();
        }
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (String string : lines) {
            printWriter.println(string);
        }
        printWriter.close();
    }

    public static void SendStudentToDB(Student2 student) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SACIP");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
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

    private static String[] returnRandomTags() {
        List<String> selectedTags = new ArrayList<>();

        int randtags = (int) (Math.random() * 2) + 3;
        System.out.println(randtags);
        for (int i = 0; i < randtags; i++) {
            String chosenTag = tags[new Random().nextInt(tags.length)];
            if (selectedTags.contains(chosenTag)) {
                i--;
            } else {
                selectedTags.add(chosenTag);
            }
        }
        return selectedTags.toArray(new String[selectedTags.size()]);
    }

}
