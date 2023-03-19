package dio.web.api.handler;

public class BusinessException extends RuntimeException{
    public BusinessException(String mensagem) {
        super(mensagem);
    }

    //Object ... params -> varax pra tornar a mensagem mais dinamica
    public BusinessException(String mensagem, Object ... params) {
        super(String.format(mensagem,params));
    }
}
