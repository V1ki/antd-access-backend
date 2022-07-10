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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserEntityControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<HandlerResp<String>> register(String username, String password) {
        UserReq userReq = new UserReq(username, password);
        HttpEntity<UserReq> request = new HttpEntity<>(userReq);

        String url = "http://localhost:" + port + "/api/user/register";
        return restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                });

//        return restTemplate.postForEntity(url,userReq, new ParameterizedTypeReference<HandlerResp<String>>() {}) ;
    }

    @Test
    public void usernameIsV1() {
        Assertions.assertThat(register("v1", "Abcd1234"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void usernameIsSpec() {
        Assertions.assertThat(register("viki$", "Abcd1234"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordLenght() {
        Assertions.assertThat(register("viki", "12345"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordNumber() {

        Assertions.assertThat(register("viki", "12345678"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordLowerLetter() {

        Assertions.assertThat(register("viki", "abcdefgh"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordUpperLetter() {

        Assertions.assertThat(register("viki", "ABCDEFGH"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordUpperAndLowerLetter() {

        Assertions.assertThat(register("viki", "ABCDefgh"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordUpperAndNumberLetter() {

        Assertions.assertThat(register("viki", "ABCD1234"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordLowerAndNumberLetter() {
        Assertions.assertThat(register("viki", "abcd1234"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void passwordCorrectUserNameNotUnique() {

        Assertions.assertThat(register("admin", "Abcd1234"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(1);
    }

    @Test
    public void correct() {
        Assertions.assertThat(register("viki" + System.currentTimeMillis(), "Abcd1234"))
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK)
                .extracting(HttpEntity::getBody)
                .isNotNull()
                .extracting(HandlerResp::getCode)
                .isEqualTo(0);
    }
}
