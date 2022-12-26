package br.univali.portugol.plugin.maspath.dataentities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
public class Student2 {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    // Dados pessoais
    private String name;
    private String password;
    private String avatar;
    private String genero;
    private int idade;
    private String nivelEducacional;
    @ElementCollection(targetClass = String.class)
    private List<String> preferencias;

    // trilha
    private String lo1 = null;
    private String lo2 = null;
    private String lo3 = null;
    private String lo4 = null;
    private String lo5 = null;
    private String lo6 = null;
    private String lo7 = null;
    private String lo8 = null;
    private String lo9 = null;
    private String lo10 = null;
    private String lo11 = null;
    private String lo12 = null;
    private String lo13 = null;
    private String lo14 = null;
    private String lo15 = null;

    public int pontos = 0;

    public Student2() {
        super();
    }

    public Student2(String name, String password, String avatar, String genero, int idade, String nivelEducacional,
            List<String> preferencias) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.genero = genero;
        this.idade = idade;
        this.nivelEducacional = nivelEducacional;
        this.preferencias = preferencias;
    }

    public Student2(String name, String password, String avatar, String genero, int idade, String nivelEducacional,
            List<String> preferencias, String lo1, String lo2, String lo3, String lo4, String lo5, String lo6,
            String lo7, String lo8, String lo9, String lo10, String lo11, String lo12, String lo13, String lo14,
            String lo15) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.genero = genero;
        this.idade = idade;
        this.nivelEducacional = nivelEducacional;
        this.preferencias = preferencias;
        this.lo1 = lo1;
        this.lo2 = lo2;
        this.lo3 = lo3;
        this.lo4 = lo4;
        this.lo5 = lo5;
        this.lo6 = lo6;
        this.lo7 = lo7;
        this.lo8 = lo8;
        this.lo9 = lo9;
        this.lo10 = lo10;
        this.lo11 = lo11;
        this.lo12 = lo12;
        this.lo13 = lo13;
        this.lo14 = lo14;
        this.lo15 = lo15;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNivelEducacional() {
        return this.nivelEducacional;
    }

    public void setNivelEducacional(String nivelEducacional) {
        this.nivelEducacional = nivelEducacional;
    }

    public List<String> getPreferencias() {
        return this.preferencias;
    }

    public String buildPreferenciasAsString() {

        StringBuilder builder = new StringBuilder();

        for (String preferencia : preferencias) {
            builder.append("'" + preferencia + "',");
        }

        if (!preferencias.isEmpty())
            builder.deleteCharAt(builder.lastIndexOf(","));

        return builder.toString();
    }

    public void setPreferencias(List<String> preferencias) {
        this.preferencias = preferencias;
    }

    // public List<String> getTrilha() {
    // return this.trilha;
    // }

    public void addNovoPassoTrilha(String content) {
        // this.trilha.add(content);
    }

    public String getLo1() {
        return lo1;
    }

    public void setLo1(String lo1) {
        this.lo1 = lo1;
    }

    public String getLo2() {
        return lo2;
    }

    public void setLo2(String lo2) {
        this.lo2 = lo2;
    }

    public String getLo3() {
        return lo3;
    }

    public void setLo3(String lo3) {
        this.lo3 = lo3;
    }

    public String getLo4() {
        return lo4;
    }

    public void setLo4(String lo4) {
        this.lo4 = lo4;
    }

    public String getLo5() {
        return lo5;
    }

    public void setLo5(String lo5) {
        this.lo5 = lo5;
    }

    public String getLo6() {
        return lo6;
    }

    public void setLo6(String lo6) {
        this.lo6 = lo6;
    }

    public String getLo7() {
        return lo7;
    }

    public void setLo7(String lo7) {
        this.lo7 = lo7;
    }

    public String getLo8() {
        return lo8;
    }

    public void setLo8(String lo8) {
        this.lo8 = lo8;
    }

    public String getLo9() {
        return lo9;
    }

    public void setLo9(String lo9) {
        this.lo9 = lo9;
    }

    public String getLo10() {
        return lo10;
    }

    public void setLo10(String lo10) {
        this.lo10 = lo10;
    }

    public String getLo11() {
        return lo11;
    }

    public void setLo11(String lo11) {
        this.lo11 = lo11;
    }

    public String getLo12() {
        return lo12;
    }

    public void setLo12(String lo12) {
        this.lo12 = lo12;
    }

    public String getLo13() {
        return lo13;
    }

    public void setLo13(String lo13) {
        this.lo13 = lo13;
    }

    public String getLo14() {
        return lo14;
    }

    public void setLo14(String lo14) {
        this.lo14 = lo14;
    }

    public String getLo15() {
        return lo15;
    }

    public void setLo15(String lo15) {
        this.lo15 = lo15;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {
        return "{" +
                " name:'" + name + "'" +
                ", password:'" + password + "'" +
                ", avatar:'" + avatar + "'" +
                ", genero:'" + genero + "'" +
                ", idade:" + idade + "" +
                ", nivelEducacional:'" + nivelEducacional + "'" +
                ", preferencias:[" + buildPreferenciasAsString() + "]" +
                "}";
    }

}