package dio.web.api.handler;

import javax.annotation.Resource;
import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Resource
    private MessageSource messageSource; //-> capaz de pegar mensagem de origem das exceçoes executadas nas requisições
    private HttpHeaders headers(){ //-> cabeçalho da resposta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //-> o conteudo sera um json
        return headers;
    }

    //private ResponseError responseError -> Implementação da nossa classe 'ResponseError'
    private ResponseError responseError(String message,HttpStatus statusCode){
        ResponseError responseError = new ResponseError();
        responseError.setStatus("error"); //-> colocamos um status fixo (podemos customizar colocando 'info' etc..)
        responseError.setError(message);
        responseError.setStatusCode(statusCode.value());
        return responseError;
    }
    @ExceptionHandler(Exception.class) //-> ao captar qualqeur tipo de exceção na nossa aplicação
    // ela faz uma verificação se essa exceção é do tipo BusinessException, como estamos dizendo
    //ali na linha 38 (no return)
    //execção que criamos que é unica que é relacionada ao negocio da aplicação
    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
        if (e.getClass().isAssignableFrom(UndeclaredThrowableException.class)) {
            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;
            return handleBusinessException((BusinessException) exception.getUndeclaredThrowable(), request);
        } else {
            //Se não for uma exceção de 'BusinessException', o spring vai retornar uma exeção generica
            String message = messageSource.getMessage("error.server", new Object[]{e.getMessage()}, null);
            ResponseError error = responseError(message,HttpStatus.INTERNAL_SERVER_ERROR);
            return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    //se o 'ExceptionHandler' acima captar a exceção de BusinessException
    //ela vai chamar o 'ExceptionHandler' abaixo e vai montar toda estrutura que esta abaixo
    //a mensagem sera passada de acordo com o controller (BusinessException e)
    //e o statusCode eu vou ilustar como 'Conflict' (setado na mao aqui mesmo)
    @ExceptionHandler({BusinessException.class})
    private ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        ResponseError error = responseError(e.getMessage(),HttpStatus.CONFLICT);
        return handleExceptionInternal(e, error, headers(), HttpStatus.CONFLICT, request);
    }
}

