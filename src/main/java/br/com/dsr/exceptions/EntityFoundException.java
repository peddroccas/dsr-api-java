package br.com.dsr.exceptions;

public class EntityFoundException extends RuntimeException {
    public EntityFoundException(String entity) {
        super(entity + "já existe");
    }
}
