package application.model;

public enum OpcoesMenu {

    CREATE(0),
    READ(1),
    UPDATE(2),
    DELETE(3);


    private final int valor;

    OpcoesMenu(int valorOpcao){
        valor = valorOpcao;
    }
    public int getValor(){
        return valor;
    }

}
