package antd_access;

import antd_access.model.req.user.UserReq;
import antd_access.model.resp.HandlerResp;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEntityControllerTest {

    @LocalServerPort
    private int port ;

    @Autowired
    private TestRestTemplate restTemplate ;

    public ResponseEntity<HandlerResp> register(String username , String password) {
        UserReq userReq = new UserReq(username,password);
        String url = "http://localhost:"+port +"/api/user/register" ;
        return restTemplate.postForEntity(url,userReq, HandlerResp.class) ;
    }

    @Test
    public void usernameIsV1(){
        ResponseEntity<HandlerResp> resp =  register("v1","Abcd1234");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }

    @Test
    public void usernameIsSpec(){
        ResponseEntity<HandlerResp> resp =  register("viki$","Abcd1234");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }

    @Test
    public void passwordLenght(){
        ResponseEntity<HandlerResp> resp =  register("viki","12345");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordNumber(){
        ResponseEntity<HandlerResp> resp =  register("viki","12345678");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordLowerLetter(){
        ResponseEntity<HandlerResp> resp =  register("viki","abcdefgh");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordUpperLetter(){
        ResponseEntity<HandlerResp> resp =  register("viki","ABCDEFGH");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordUpperAndLowerLetter(){
        ResponseEntity<HandlerResp> resp =  register("viki","ABCDefgh");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordUpperAndNumberLetter(){
        ResponseEntity<HandlerResp> resp =  register("viki","ABCD1234");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordLowerAndNumberLetter(){
        ResponseEntity<HandlerResp> resp =  register("viki","abcd1234");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void passwordCorrectUserNameNotUnique(){
        ResponseEntity<HandlerResp> resp =  register("viki","Abcd1234");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(1) ;
    }
    @Test
    public void correct(){
        ResponseEntity<HandlerResp> resp =  register("viki"+System.currentTimeMillis(),"Abcd1234");
        log.info(String.valueOf(resp));
        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK) ;
        Assertions.assertThat(resp.getBody().getCode()).isEqualTo(0) ;
    }
}
