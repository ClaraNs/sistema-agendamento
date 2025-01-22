package com.serenaterapias.agendamento.validation;

public class ValidadorCPF {
    public static boolean isValid(String cpf){

        if(cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")){
            return false;
        }

        return true;
        //verificação da soma do cpf?
    }
}
