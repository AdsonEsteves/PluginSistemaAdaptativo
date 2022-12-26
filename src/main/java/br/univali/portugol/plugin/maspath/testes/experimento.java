package br.univali.portugol.plugin.maspath.testes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ValueNode;

import br.univali.portugol.plugin.maspath.conexao.InterfaceComunicacao;
import br.univali.portugol.plugin.maspath.dataentities.Student;

public class experimento {

    public static void main(String[] args) throws Exception {

        for (int a = 0; a < 100; a++) {
            cria_aluno();
            faz_login();
            // for (int i = 0; i < topicos1d.length / 2; i++) {
            // requisita_recomendacao(i);
            // }
            for (int i = 0; i < topicos.length; i++) {
                List<String> doneTopics = new ArrayList<>();
                do {
                    String pede_recomendacao = pede_recomendacao("");
                    if (!doneTopics.contains(pede_recomendacao)) {
                        doneTopics.add(pede_recomendacao);
                    }
                    System.out.println("USER NUMBER: " + a);
                    System.out.println(doneTopics);
                } while (!doneTopics.containsAll(Arrays.asList(topicos[i])));
            }
            desloga();
        }
        imprime_dados();
    }

    static List<Integer> redo = new ArrayList<>();

    static InterfaceComunicacao intercom = InterfaceComunicacao.getInstance();

    static Student student;

    static String[] tags = {
            "carros", "musica", "desenhos", "jogos",
            "matematica", "linguas", "biologia", "animais",
            "comedia", "cultura", "memes", "geografia"
    };

    static String[] types = {
            "E-Book", "Podcast", "Imagem", "Infografico", "Mapas Conceituais",
            "Quiz", "TesteDescritivo", "TesteMisturado", "TesteProgramacao",
            "Estudo de Caso", "ForumAvaliativo", "Simuladores", "LabsVirtuais", "Jogos", "Hyperbook"
    };

    static String[] min_types = {
            "Textos", "Slides", "Video", "Atividade"
    };

    static String[] min_types2 = {
            "Forum", "Chat"
    };

    static String[][] topicos = {
            { "t1-1", "t1-2", "t2-1", "t2-2", "t2-3", "t2-4", "t2-5", "t2-6", "t3-1", "t3-2", "t3-3" },
            { "t4-1", "t4-2", "t4-3", "t4-4", "t5-1", "t5-2", "t5-3" },
            { "t6-1", "t6-2", "t6-3", "t6-4", "t7-1", "t7-2", "t7-3", "t7-4" },
            { "t8-1", "t8-2", "t8-3", "t9-1", "t9-2" },
            { "t10-1", "t10-2", "t10-3", "t10-4", "t10-5" }
    };
    static String[] topicos1d = {
            "t1-1", "t1-2", "t2-1", "t2-2", "t2-3", "t2-4", "t2-5", "t2-6", "t3-1", "t3-2", "t3-3",
            "t4-1", "t4-2", "t4-3", "t4-4", "t5-1", "t5-2", "t5-3", "t6-1", "t6-2", "t6-3", "t6-4",
            "t7-1", "t7-2", "t7-3", "t7-4", "t8-1", "t8-2", "t8-3", "t9-1", "t9-2", "t10-1", "t10-2", "t10-3",
            "t10-4", "t10-5"
    };

    static int niveis = 5;

    static String[] complexidade = { "Matemática", "Cognitiva", "Algoritmo", "Codigo" };

    static String[] generos = { "Masculino", "Feminino", "Transsexual", "Outro" };

    static int[] niveisEdu = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

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
        // System.out.println(resposta);
    }

    private static void requisita_recomendacao(int nivel) throws Exception {
        for (int j = 1; j <= 2; j++) {
            if (!pede_recomendacao((nivel * 2) + j)) {
                redo.add(j);
            }
        }
        if (!redo.isEmpty()) {
            for (Integer r : redo) {
                pede_recomendacao((nivel * 2) + r);
            }
            redo.clear();
        }
    }

    private static boolean pede_recomendacao(int topico_index) throws Exception {
        String conteudos_string_json = intercom.requisitaConteudos();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode conteudos = mapper.readTree(conteudos_string_json).get(0);
        int t = (int) (Math.random() * 2) + 1;
        int nivel = (topico_index / 2) + 1;
        Random rand = new Random();
        List<JsonNode> conteudosSelecionados = new ArrayList<>();
        List<JsonNode> conteudosInteresse = new ArrayList<>();
        for (JsonNode conteudo : conteudos) {
            if (conteudo.get("topic").asText().equals(topicos1d[topico_index - 1])) {
                conteudosSelecionados.add(conteudo);
                if (student.getPreferencias().contains(conteudo.get("type").asText())) {
                    conteudosInteresse.add(conteudo);
                }
            }
        }

        if (conteudosSelecionados.isEmpty()) {
            System.out.println("Sem conteudo do topico: " + topicos1d[topico_index]);
            return false;
        }
        JsonNode content;

        if (conteudosInteresse.isEmpty()) {
            content = conteudosSelecionados.get(rand.nextInt(conteudosSelecionados.size()));
        } else {
            System.out.println("Tem conteudo de interesse " + topicos1d[topico_index]);
            content = conteudosInteresse.get(rand.nextInt(conteudosInteresse.size()));
        }
        registra_na_trilha(content.get("name").asText());
        return true;
        // System.exit(0);
    }

    private static String pede_recomendacao(String topico_index) throws Exception {
        String conteudos_string_json = intercom.requisitaConteudos();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode conteudos = mapper.readTree(conteudos_string_json).get(0);
        int t = (int) (Math.random() * 2) + 1;
        Random rand = new Random();
        List<JsonNode> conteudosInteresse = new ArrayList<>();
        List<JsonNode> conteudosSelecionados = new ArrayList<>();
        for (JsonNode conteudo : conteudos) {
            // if (conteudo.get("topic").asText().equals(topico_index)) {
            conteudosSelecionados.add(conteudo);
            // }
            if (student.getPreferencias().contains(conteudo.get("type").asText())) {
                conteudosInteresse.add(conteudo);
            }
        }

        if (conteudosSelecionados.isEmpty()) {
            System.out.println("Sem conteudo do topico: " + topico_index);
            // return false;
        }

        JsonNode content;

        if (conteudosInteresse.isEmpty()) {
            content = conteudosSelecionados.get(rand.nextInt(conteudosSelecionados.size()));
        } else {
            System.out.println("Tem conteudo de interesse " + topico_index);
            content = conteudosInteresse.get(rand.nextInt(conteudosInteresse.size()));
        }
        registra_na_trilha(content.get("name").asText());
        return content.get("topic").asText();
        // System.exit(0);
    }

    public static void cria_aluno() {
        Random generator = new Random();

        String name = randomString();
        String password = randomString();
        String genero = generos[generator.nextInt(generos.length)];
        int nivelEdu = niveisEdu[generator.nextInt(niveisEdu.length)];
        int idade = generator.nextInt(30) + 12;
        String[] links = {
                "https://cdn.discordapp.com/attachments/571157550956019741/800619655366574091/12243585_1694508097447198_1004266710788666891_n.jpg",
                "https://cdn.discordapp.com/attachments/571157550956019741/800619703089365002/21077295_1119616784841346_734019202998452151_n.jpg",
                "https://cdn.discordapp.com/attachments/571157550956019741/800619727889629264/1521285067403.jpg" };
        String avatar = links[generator.nextInt(3)];

        student = new Student(name, password, avatar, genero, idade, nivelEdu, Arrays.asList(returnRandomTags()),
                new ArrayList<String>(), generator.nextDouble() * 10, generator.nextDouble());

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
        selectedTags.add(min_types[new Random().nextInt(min_types.length)]);
        selectedTags.add(min_types2[new Random().nextInt(min_types2.length)]);

        int m = new Random().nextInt(3);

        for (int i = 0; i < m; i++) {
            selectedTags.add(types[new Random().nextInt(types.length)]);
        }

        return selectedTags.toArray(new String[selectedTags.size()]);
    }

}
